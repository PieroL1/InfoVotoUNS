package com.aristidevs.infovotouns.View.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.security.MessageDigest

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // Firestore
    private val db = FirebaseFirestore.getInstance()

    // Moderno: usa ActivityResultLauncher
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Configura Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        // Click en el botón de Google
        binding.btnGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }

// ---- LOGIN MANUAL: ALUMNO + CONTRASEÑA ----
        binding.btnLogin.setOnClickListener {
            val codigoAlumno = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (codigoAlumno.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            db.collection("usuarios").document(codigoAlumno).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        // <<< Cambios clave aquí
                        val storedPasswordHash = doc.getString("contraseña")?.trim()
                        val enteredPasswordHash = sha256(password).trim()
                        Log.d("LoginDebug", "Ingresado: '$enteredPasswordHash' | Guardado: '$storedPasswordHash'")



                        if (storedPasswordHash == enteredPasswordHash) {
                            Toast.makeText(requireContext(), "Bienvenido/a!", Toast.LENGTH_SHORT).show()
                            navegarAHOME()
                        } else {
                            Toast.makeText(requireContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }

                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Error de conexión", Toast.LENGTH_SHORT).show()
                }
        }


        // Si ya hay usuario logueado con Google, salta a Home
        auth.currentUser?.let {
            navegarAHOME()
        }
    }

    // Función para hashear con SHA-256
    private fun sha256(text: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(text.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun handleSignInResult(completedTask: com.google.android.gms.tasks.Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                firebaseAuthWithGoogle(account.idToken!!)
            }
        } catch (e: ApiException) {
            Toast.makeText(requireContext(), "Error al iniciar con Google", Toast.LENGTH_SHORT).show()
            Log.w("LoginFragment", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Bienvenido/a!", Toast.LENGTH_SHORT).show()
                    navegarAHOME()
                } else {
                    Toast.makeText(requireContext(), "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navegarAHOME() {
        findNavController().navigate(R.id.HomeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

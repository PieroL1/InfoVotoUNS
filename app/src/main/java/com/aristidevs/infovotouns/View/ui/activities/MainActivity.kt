package com.aristidevs.infovotouns.View.ui.activities

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatDelegate
import com.aristidevs.infovotouns.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore

import com.aristidevs.infovotouns.model.Partido
import com.aristidevs.infovotouns.model.Propuesta
import com.aristidevs.infovotouns.model.Candidato
import com.aristidevs.infovotouns.model.Evento
import com.aristidevs.infovotouns.model.Noticia
import com.aristidevs.infovotouns.model.Usuario
import com.aristidevs.infovotouns.network.NetworkReceiver
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: MaterialToolbar
    private var mostrarMenuOption: Boolean = true  // <-- Bandera
    private var networkReceiver: NetworkReceiver? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)


        // ---- Noticias ----
        Firebase.firestore.collection("noticias")
            .addSnapshotListener { snaps, _ ->
                snaps?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        Snackbar.make(findViewById(android.R.id.content),
                            "📰 Nueva noticia: ${change.document["titulo"] ?: "Sin título"}",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
            }

        // ---- Candidatos ----
        Firebase.firestore.collection("candidatos")
            .addSnapshotListener { snaps, _ ->
                snaps?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        Snackbar.make(findViewById(android.R.id.content),
                            "🧑‍💼 Nuevo candidato: ${change.document["nombre"] ?: "Sin nombre"}",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
            }

        // ---- Eventos ----
        Firebase.firestore.collection("eventos")
            .addSnapshotListener { snaps, _ ->
                snaps?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        Snackbar.make(findViewById(android.R.id.content),
                            "📅 Nuevo evento: ${change.document["descripcion"] ?: "Sin descripción"}",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
            }

        // ---- Partidos ----
        Firebase.firestore.collection("partidos")
            .addSnapshotListener { snaps, _ ->
                snaps?.documentChanges?.forEach { change ->
                    if (change.type == DocumentChange.Type.ADDED) {
                        Snackbar.make(findViewById(android.R.id.content),
                            "🏳️ Nuevo partido: ${change.document["nombre"] ?: "Sin nombre"}",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
            }


        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNav = findViewById(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val fragmentsSinMenu = setOf(R.id.loginFragment)
            bottomNav.visibility = if (destination.id in fragmentsSinMenu)
                android.view.View.GONE
            else
                android.view.View.VISIBLE

            // Solo muestro menú si no estoy en login
            mostrarMenuOption = destination.id !in fragmentsSinMenu
            invalidateOptionsMenu() // Actualizo menú de arriba
        }

        // === PEDIR PERMISO DE NOTIFICACIÓN (Android 13+) ===
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                // Puedes mostrar un Toast si lo deseas
                // if (!isGranted) Toast.makeText(this, "No podrás recibir notificaciones", Toast.LENGTH_SHORT).show()
            }
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        // === OBTENER EL TOKEN FCM (opcional, solo para debug) ===
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FCM_TOKEN", "Token: ${task.result}")
            } else {
                Log.e("FCM_TOKEN", "Error al obtener el token", task.exception)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (mostrarMenuOption) {
            menuInflater.inflate(R.menu.menu_option, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_calendar -> {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            val navController = navHostFragment.navController
            navController.navigate(R.id.calendarFragment)
            true
        }

        R.id.action_logout -> {
            logout()
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut()

        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                .navController

        // Evito doble navegación
        if (navController.currentDestination?.id != R.id.loginFragment) {
            navController.popBackStack(R.id.loginFragment, false)
            navController.navigate(R.id.loginFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        networkReceiver = NetworkReceiver(this)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkReceiver)
    }



}



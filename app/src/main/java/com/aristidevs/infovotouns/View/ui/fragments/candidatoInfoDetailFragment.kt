package com.aristidevs.infovotouns.View.ui.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.databinding.FragmentCandidatoInfoDetailBinding
import com.aristidevs.infovotouns.model.Candidato
import com.bumptech.glide.Glide


class candidatoInfoDetailFragment : DialogFragment() {
    private lateinit var binding: FragmentCandidatoInfoDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Hacer que sea fullscreen
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCandidatoInfoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar Toolbar con botón cerrar
        binding.toolbarCandidatoDetalle.navigationIcon =
            ContextCompat.getDrawable(view.context, R.drawable.ic_close)
        binding.toolbarCandidatoDetalle.setTitleTextColor(Color.WHITE)
        binding.toolbarCandidatoDetalle.setNavigationOnClickListener { dismiss() }

        // Recibir Candidato
        val candidato = arguments?.getSerializable("candidato") as? Candidato
        candidato?.let {
            binding.toolbarCandidatoDetalle.title = "Datos del Candidato"

            // Cargar imagen con Glide
            Glide.with(requireContext())
                .load(it.fotoUrl)
                .placeholder(R.drawable.placeholder_user)
                .error(R.drawable.placeholder_user)
                .circleCrop()
                .into(binding.ivFotoCandidatoInfo)

            // Asignar datos
            binding.tvNombreCandidatoInfo.text = it.nombre
            binding.tvPartidoCandidatoInfo.text = it.partido
            binding.tvNacimientoCandidato.text = it.fechaNacimiento ?: "No especificado"
            binding.tvProfesionCandidato.text = it.profesion ?: "No especificado"
            binding.tvFormacionCandidato.text = it.formacionAcademica ?: "No especificado"
            binding.tvTrayectoriaCandidato.text = it.trayectoria ?: "No especificado"
        }
    }
}
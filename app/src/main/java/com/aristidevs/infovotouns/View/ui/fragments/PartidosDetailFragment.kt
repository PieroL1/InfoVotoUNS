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
import com.aristidevs.infovotouns.databinding.FragmentPartidosBinding
import com.aristidevs.infovotouns.databinding.FragmentPartidosDetailBinding
import com.aristidevs.infovotouns.model.Partido
import com.bumptech.glide.Glide


class PartidosDetailFragment : DialogFragment() {

    private lateinit var binding: FragmentPartidosDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPartidosDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar Toolbar con botón de cerrar
        binding.toolbarDetallePartido.navigationIcon =
            ContextCompat.getDrawable(view.context, R.drawable.ic_close)
        binding.toolbarDetallePartido.setTitleTextColor(Color.WHITE)
        binding.toolbarDetallePartido.setNavigationOnClickListener { dismiss() }

        // Recibir Partido desde argumentos
        val partido = arguments?.getSerializable("partido") as? Partido
        partido?.let {
            binding.toolbarDetallePartido.title = it.nombre

            Glide.with(requireContext())
                .load(it.logo)
                .placeholder(R.drawable.placeholder_logo) // imagen por defecto
                .error(R.drawable.placeholder_logo)       // si falla la carga
                .into(binding.ivPartyLogo)

            binding.tvPartyNameDetail.text = it.nombre

            // Ideología
            binding.tvPartyIdeologia.text = it.ideologia

            // Descripción corta (opcional puedes mapearla desde Firestore)
            binding.tvPartyShortDescDetail.text = it.ideologia

            // Historia
            binding.tvPartyHistoryDesc.text = it.historia

            // Objetivos (si los tienes en el modelo, si no puedes poner un texto fijo)
            binding.tvPartyGoalsDesc.text = "Promover la educación, salud y desarrollo sostenible..."
        }
    }
}
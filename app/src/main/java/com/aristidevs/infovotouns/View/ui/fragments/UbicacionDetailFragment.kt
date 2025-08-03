package com.aristidevs.infovotouns.View.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.databinding.FragmentUbicacionDetailBinding
import com.aristidevs.infovotouns.model.Ubicacion
import com.bumptech.glide.Glide


class UbicacionDetailFragment : DialogFragment() {

    private var _binding: FragmentUbicacionDetailBinding? = null
    private val binding get() = _binding!!
    private val args: UbicacionDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUbicacionDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ubicacion = args.ubicacion

        binding.apply {
            toolbarUbicacionDetalle.title = ubicacion.nombre
            toolbarUbicacionDetalle.setNavigationIcon(R.drawable.ic_close)
            toolbarUbicacionDetalle.setNavigationOnClickListener {
                dismiss()
            }

            tvLocationName.text = ubicacion.nombre
            tvLocationAddress.text = ubicacion.direccion
            tvLocationDetails.text = "${ubicacion.horario}\n${ubicacion.descripcion}"

            Glide.with(requireContext())
                .load(ubicacion.foto)
                .into(ivLocationMap)
        }
    }
    override fun getTheme(): Int = R.style.FullScreenDialogStyle

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

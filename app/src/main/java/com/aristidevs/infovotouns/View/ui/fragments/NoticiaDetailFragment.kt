package com.aristidevs.infovotouns.View.ui.fragments


import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.databinding.FragmentNewDetailsBinding
import com.aristidevs.infovotouns.databinding.FragmentNoticiasBinding

import com.aristidevs.infovotouns.model.Noticia
import com.bumptech.glide.Glide

class NoticiaDetailFragment : DialogFragment() {

    private lateinit var binding: FragmentNewDetailsBinding
    private var noticia: Noticia? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarNoticiaDetalle.setNavigationOnClickListener {
            dismiss()
        }

        noticia = arguments?.getSerializable("noticia") as? Noticia

        noticia?.let { nt ->
            binding.tvNoticiaTitle.text = nt.titulo
            binding.tvNoticiaAutor.text = "Autor: ${nt.autor}"
            binding.tvNoticiaFecha.text = "Fecha: ${nt.fecha}"
            binding.tvNoticiaCategoria.text = "Categoría: ${nt.categoria}"
            binding.tvNoticiaContenido.text = nt.contenido

            Glide.with(this)
                .load(nt.imagenUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(binding.imgNoticia)
        }
    }

    companion object {
        fun newInstance(noticia: Noticia): NoticiaDetailFragment {
            val fragment = NoticiaDetailFragment()
            val args = Bundle().apply {
                putSerializable("noticia", noticia)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
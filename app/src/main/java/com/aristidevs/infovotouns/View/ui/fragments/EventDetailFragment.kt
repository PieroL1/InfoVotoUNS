package com.aristidevs.infovotouns.View.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.databinding.FragmentEventDetailBinding
import com.aristidevs.infovotouns.model.Evento
import com.bumptech.glide.Glide

class EventDetailFragment : DialogFragment() {

    private lateinit var binding: FragmentEventDetailBinding
    private var evento: Evento? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cerrar el fragmento desde la Toolbar
        binding.toolbarEventoDetalle.setNavigationOnClickListener {
            dismiss()
        }

        // Obtener el evento desde los argumentos
        evento = arguments?.getSerializable("evento") as? Evento

        evento?.let { ev ->
            binding.tvEventTitle.text = ev.titulo
            binding.tvEventDescription.text = ev.descripcion
            binding.tvEventDate.text = "Fecha: ${ev.fecha}"
            binding.tvEventLocation.text = "Categoría: ${ev.categoria}"
            binding.tvEventTime.text = "Partidos: ${ev.partidos.joinToString(", ")}"

          //Glide.with(this).load(ev.imagenUrl).into(binding.imgEvento)
        }
    }

    companion object {
        fun newInstance(evento: Evento): EventDetailFragment {
            val fragment = EventDetailFragment()
            val args = Bundle().apply {
                putSerializable("evento", evento)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
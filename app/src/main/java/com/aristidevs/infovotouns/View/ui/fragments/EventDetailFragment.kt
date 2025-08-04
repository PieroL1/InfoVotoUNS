package com.aristidevs.infovotouns.View.ui.fragments
import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.databinding.FragmentEventDetailBinding
import com.aristidevs.infovotouns.model.Evento
import com.bumptech.glide.Glide
import androidx.navigation.fragment.navArgs
import androidx.navigation.fragment.findNavController
import java.time.LocalDateTime
import java.time.ZoneId

class EventDetailFragment : DialogFragment() {

    private val args: EventDetailFragmentArgs by navArgs()
    private var _binding: FragmentEventDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogNoPadding)
    }


    override fun onCreateView(
        i: LayoutInflater, c: ViewGroup?, s: Bundle?
    ): View {
        _binding = FragmentEventDetailBinding.inflate(i, c, false)
        return binding.root
    }

    override fun onViewCreated(v: View, s: Bundle?) {
        super.onViewCreated(v, s)

        val evento = args.evento                     // 🎯 Safe-Args

        val localDateTime = LocalDateTime.parse(evento.fecha)
        val startMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        binding.toolbarEventoDetalle.setNavigationOnClickListener { findNavController().navigateUp() }


        binding.btnAgregarCalendario.setOnClickListener {
            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, evento.titulo)
                putExtra(CalendarContract.Events.DESCRIPTION, evento.descripcion)

                // Si tienes ubicación
                // putExtra(CalendarContract.Events.EVENT_LOCATION, "Universidad Nacional del Santa")

                // Convierte la fecha
                val localDateTime = LocalDateTime.parse(evento.fecha)
                val startMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
            }
            startActivity(intent)
        }






        binding.tvEventTitle.text        = evento.titulo
        binding.tvEventDescription.text  = evento.descripcion
        binding.tvEventDate.text         = "Fecha: ${evento.fecha}"
        binding.tvEventLocation.text     = "Categoría: ${evento.categoria}"
        binding.tvEventTime.text         =
            "Partidos: ${evento.partidos.joinToString(", ")}"

        // Glide.with(this).load(evento.imagenUrl).into(binding.imgEvento)
    }

    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}

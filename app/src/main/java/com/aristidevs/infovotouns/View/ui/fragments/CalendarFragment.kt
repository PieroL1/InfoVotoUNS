package com.aristidevs.infovotouns.View.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristidevs.infovotouns.databinding.FragmentCalendarBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.ZoneId
import android.graphics.Color
import android.text.style.ForegroundColorSpan
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater.from
import android.widget.TextView
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.model.Evento
import com.prolificinteractive.materialcalendarview.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()

    // Mapearemos fecha → lista de eventos
    private val mapaEventos = mutableMapOf<LocalDate, MutableList<Evento>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Cargar TODOS los eventos una sola vez
        db.collection("eventos").get().addOnSuccessListener { snaps ->
            mapaEventos.clear()

            snaps.forEach { doc ->                 // ← creamos un Evento completo
                val fechaStr = doc.getString("fecha") ?: return@forEach
                val fecha = LocalDate.parse(fechaStr.substring(0, 10))

                val ev = Evento(
                    id         = doc.id,
                    titulo     = doc.getString("titulo") ?: "(sin título)",
                    descripcion= doc.getString("descripcion") ?: "",
                    categoria  = doc.getString("categoria") ?: "",
                    fecha      = fechaStr,
                    partidos   = doc.get("partidos") as? List<String> ?: emptyList(),
                    imagenUrl  = doc.getString("imagenUrl") ?: ""
                )

                // evita duplicados
                val lista = mapaEventos.getOrPut(fecha) { mutableListOf() }
                if (ev.id !in lista.map { it.id }) lista.add(ev)
            }

            marcarDiasConEventos()
        }




        // 2. Al seleccionar un día → mostrar lista
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            val local = LocalDate.of(date.year, date.month, date.day)
            val lista = mapaEventos[local] ?: emptyList()
            binding.rvEventosDia.adapter = EventosAdapter(lista)
        }

        binding.rvEventosDia.layoutManager = LinearLayoutManager(requireContext())
    }

    // ---- Decorador para sombrear los días ----
    private fun marcarDiasConEventos() {
        val fechas = mapaEventos.keys.map {
            CalendarDay.from(it.year, it.monthValue, it.dayOfMonth)
        }.toSet()
        binding.calendarView.addDecorator(object : DayViewDecorator {
            override fun shouldDecorate(day: CalendarDay) = day in fechas
            override fun decorate(view: DayViewFacade) {
                view.addSpan(ForegroundColorSpan(Color.RED)) // pinta el número
                view.setSelectionDrawable(resources.getDrawable(R.drawable.bg_circle_event))
            }
        })
    }

    // ---- Adaptador mini ----
    private inner class EventosAdapter(
        private val data : List<Evento>
    ) : RecyclerView.Adapter<EventosAdapter.Holder>() {

        inner class Holder(v: View) : RecyclerView.ViewHolder(v) {
            val tv: TextView = v.findViewById(android.R.id.text1)
        }

        override fun onCreateViewHolder(p: ViewGroup, vType: Int): Holder {
            val v = from(p.context)
                .inflate(android.R.layout.simple_list_item_1, p, false)
            return Holder(v)
        }

        override fun onBindViewHolder(h: Holder, pos: Int) {
            val e = data[pos]
            h.tv.text = "• ${e.titulo}"
            h.itemView.setOnClickListener {
                val act =
                    CalendarFragmentDirections.actionCalendarFragmentToEventDetailFragment(e)
                findNavController().navigate(act)
            }
        }


        override fun getItemCount() = data.size
    }


    override fun onDestroyView() { super.onDestroyView(); _binding = null }
}

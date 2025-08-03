package com.aristidevs.infovotouns.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.model.Evento
import com.bumptech.glide.Glide


class EventoAdapter(
    private var eventos: List<Evento>,
    private val listener: EventoListener
) : RecyclerView.Adapter<EventoAdapter.EventoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_evento, parent, false)
        return EventoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        val evento = eventos[position]
        holder.bind(evento)
    }

    override fun getItemCount(): Int = eventos.size

    fun updateData(newEventos: List<Evento>) {
        eventos = newEventos
        notifyDataSetChanged()
    }

    inner class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitulo: TextView = itemView.findViewById(R.id.tvTituloEvento)
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFechaEvento)
        private val ivImagen: ImageView = itemView.findViewById(R.id.ivEvento)

        fun bind(evento: Evento) {
            tvTitulo.text = evento.titulo
            tvFecha.text = evento.fecha

            Glide.with(itemView.context)
                .load(evento.imagenUrl)
                .placeholder(R.drawable.placeholder_image) // Imagen por defecto
                .error(R.drawable.ic_event)
                .into(ivImagen)

            itemView.setOnClickListener {
                listener.onEventoClicked(evento)
            }
        }
    }
}

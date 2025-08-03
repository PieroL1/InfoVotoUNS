package com.aristidevs.infovotouns.View.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.model.Partido
import com.bumptech.glide.Glide

class PartidoAdapter(val partidoListener: PartidoListener) :
    RecyclerView.Adapter<PartidoAdapter.ViewHolder>() {

    var listadoPartidos = ArrayList<Partido>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_partido, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val partido = listadoPartidos[position]

        holder.tvNombrePartido.text = partido.nombre
        holder.tvIdeologiaPartido.text = partido.ideologia

        Glide.with(holder.itemView.context)
            .load(partido.logo)
            .into(holder.ivLogoPartido)

        holder.itemView.setOnClickListener {
            partidoListener.onPartidoClicked(partido, position)
        }
    }

    override fun getItemCount() = listadoPartidos.size

    fun updateData(data: List<Partido>) {
        listadoPartidos.clear()
        listadoPartidos.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombrePartido: TextView = itemView.findViewById(R.id.tvNombrePartido)
        val tvIdeologiaPartido: TextView = itemView.findViewById(R.id.tvIdeologiaPartido)
        val ivLogoPartido: ImageView = itemView.findViewById(R.id.ivLogoPartido)
    }
}
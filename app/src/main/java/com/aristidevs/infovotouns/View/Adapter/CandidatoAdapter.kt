package com.aristidevs.infovotouns.View.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.model.Candidato
import com.bumptech.glide.Glide

class CandidatoAdapter(private val candidatoListener: CandidatoListener) :
    RecyclerView.Adapter<CandidatoAdapter.ViewHolder>() {

    var listadoCandidatos = ArrayList<Candidato>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_candidato, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val candidato = listadoCandidatos[position]

        holder.tvNombreCandidato.text = candidato.nombre
        holder.tvPartidoCandidato.text = candidato.partido

        // Imagen del candidato (si tienes URL en Firestore)
        Glide.with(holder.itemView.context)
            .load(candidato.fotoUrl)
            .placeholder(R.drawable.ic_person) // imagen por defecto
            .into(holder.ivFotoCandidato)

        // Botón Info Personal
        holder.btnInfo.setOnClickListener {
            candidatoListener.onInfoClicked(candidato, position)
        }

        // Botón Propuestas
        holder.btnPropuestas.setOnClickListener {
            candidatoListener.onPropuestasClicked(candidato, position)
        }
    }

    override fun getItemCount() = listadoCandidatos.size

    fun updateData(data: List<Candidato>) {
        listadoCandidatos.clear()
        listadoCandidatos.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombreCandidato: TextView = itemView.findViewById(R.id.tvNombreCandidato)
        val tvPartidoCandidato: TextView = itemView.findViewById(R.id.tvPartidoCandidato)
        val ivFotoCandidato: ImageView = itemView.findViewById(R.id.ivFotoCandidato)
        val btnInfo: Button = itemView.findViewById(R.id.btnInfoPersonal)
        val btnPropuestas: Button = itemView.findViewById(R.id.btnPropuestas)
    }
}

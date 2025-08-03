package com.aristidevs.infovotouns.View.Adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.model.Noticia

import com.bumptech.glide.Glide

class NoticiaAdapter(
    private var noticias: List<Noticia>,
    private val listener: NoticiaListener
) : RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = noticias[position]
        holder.bind(noticia)
    }

    override fun getItemCount(): Int = noticias.size

    fun updateData(newNoticias: List<Noticia>) {
        noticias = newNoticias
        notifyDataSetChanged()
    }

    inner class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitulo: TextView = itemView.findViewById(R.id.tvTituloNoticia)
        private val tvFecha: TextView = itemView.findViewById(R.id.tvFechaNoticia)
        private val ivImagen: ImageView = itemView.findViewById(R.id.ivNoticia)

        fun bind(noticia: Noticia) {
            tvTitulo.text = noticia.titulo
            tvFecha.text = noticia.fecha

            Glide.with(itemView.context)
                .load(noticia.imagenUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.ic_news)
                .into(ivImagen)

            itemView.setOnClickListener {
                listener.onNoticiaClicked(noticia)
            }
        }
    }
}

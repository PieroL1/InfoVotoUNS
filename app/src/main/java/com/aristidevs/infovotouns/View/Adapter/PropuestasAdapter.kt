package com.aristidevs.infovotouns.View.Adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.model.Propuesta

class PropuestasAdapter(
    private var propuestas: List<Propuesta>
) : RecyclerView.Adapter<PropuestasAdapter.PropuestaViewHolder>() {

    class PropuestaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoria: TextView = view.findViewById(R.id.tvCategoriaPropuesta)
        val tvTitulo: TextView = view.findViewById(R.id.tvTituloPropuesta)
        val tvDescripcion: TextView = view.findViewById(R.id.tvDescripcionPropuesta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropuestaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_propuesta, parent, false)
        return PropuestaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PropuestaViewHolder, position: Int) {
        val propuesta = propuestas[position]

        holder.tvCategoria.text = propuesta.categoria ?: "Sin categoría"
        holder.tvTitulo.text = propuesta.titulo ?: ""
        holder.tvDescripcion.text = propuesta.descripcion ?: ""
    }

    override fun getItemCount(): Int = propuestas.size

    fun updateData(newPropuestas: List<Propuesta>) {
        propuestas = newPropuestas
        notifyDataSetChanged()
    }
}

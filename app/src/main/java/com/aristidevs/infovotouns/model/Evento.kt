// app/src/main/java/com/aristidevs/infovotouns/model/Evento.kt
package com.aristidevs.infovotouns.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Evento(
    val id: String = "",
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: String = "",
    val fecha: String = "",
    val partidos: List<String> = emptyList(),
    val imagenUrl: String = ""
) : Parcelable

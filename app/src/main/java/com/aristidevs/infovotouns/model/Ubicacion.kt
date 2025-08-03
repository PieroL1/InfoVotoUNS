package com.aristidevs.infovotouns.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
@Parcelize
data class Ubicacion(
    val nombre: String = "",
    val direccion: String = "",
    val foto: String = "",             // URL o nombre del recurso
    val horario: String = "",
    val descripcion: String = "",
    val latitud: Double = 0.0,
    val longitud: Double = 0.0
): Parcelable
package com.aristidevs.infovotouns.model
import java.io.Serializable
data class Evento(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: String = "",
    val fecha: String = "",
    val partidos: List<String> = emptyList(),
    val imagenUrl: String = ""
): Serializable
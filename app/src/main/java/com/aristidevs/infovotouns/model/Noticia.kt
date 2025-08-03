package com.aristidevs.infovotouns.model

import java.io.Serializable


data class Noticia(
    val titulo: String = "",
    val contenido: String = "",
    val categoria: String = "",
    val autor: String = "",
    val fecha: String = "",
    val imagenUrl: String = ""
): Serializable
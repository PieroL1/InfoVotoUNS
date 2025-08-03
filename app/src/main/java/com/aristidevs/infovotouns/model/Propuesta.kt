package com.aristidevs.infovotouns.model

import java.io.Serializable

data class Propuesta(
    var id: String? = null,
    var titulo: String? = null,       // "Implementar internet en escuelas rurales"
    var descripcion: String? = null,  // "Acceso a Internet de alta velocidad..."
    var categoria: String? = null     // "Educación"
): Serializable

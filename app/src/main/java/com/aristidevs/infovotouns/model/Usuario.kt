package com.aristidevs.infovotouns.model

import java.io.Serializable


data class Usuario(
    val codEstudinate: String = "",
    val nombre: String = "",
    val contraseña: String = "",
    val rol: String = "",
    val carrera: String = ""
) : Serializable
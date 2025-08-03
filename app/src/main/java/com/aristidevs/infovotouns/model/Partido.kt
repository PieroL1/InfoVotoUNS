package com.aristidevs.infovotouns.model

import java.io.Serializable


data class Partido(
    var nombre: String? = null,
    var logo: String? = null,
    var historia: String? = null,
    var ideologia: String? = null
): Serializable
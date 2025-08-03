package com.aristidevs.infovotouns.View.Adapter

import com.aristidevs.infovotouns.model.Partido

interface PartidoListener {
    fun onPartidoClicked(partido: Partido, position: Int)
}
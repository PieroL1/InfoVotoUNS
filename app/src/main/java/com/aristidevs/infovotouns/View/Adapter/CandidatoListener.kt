package com.aristidevs.infovotouns.View.Adapter

import com.aristidevs.infovotouns.model.Candidato

interface CandidatoListener {
    fun onInfoClicked(candidato: Candidato, position: Int)
    fun onPropuestasClicked(candidato: Candidato, position: Int)
}
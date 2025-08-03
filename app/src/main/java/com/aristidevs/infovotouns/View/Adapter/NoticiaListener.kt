package com.aristidevs.infovotouns.View.Adapter

import com.aristidevs.infovotouns.model.Noticia

interface NoticiaListener {
    fun onNoticiaClicked(noticia: Noticia)
}
package com.aristidevs.infovotouns.model

import android.os.Parcelable
import java.io.Serializable

data class Candidato(
    var id: String? = null,                // ID del documento en Firestore (no se guarda en Firestore, solo en la app)
    var nombre: String? = null,            // "Juan Pérez"
    var partido: String? = null,           // "Partido Esperanza"
    var fechaNacimiento: String? = null,   // "01/01/1980"
    var profesion: String? = null,         // "Abogado"
    var formacionAcademica: String? = null,// "Maestría en Derecho Constitucional, Universidad X"
    var trayectoria: String? = null,       // "Experiencia como fiscal y profesor universitario"
    var fotoUrl: String? = null,
    // URL de la imagen del candidato
): Serializable

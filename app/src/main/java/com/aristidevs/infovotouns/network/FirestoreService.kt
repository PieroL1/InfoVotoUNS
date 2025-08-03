package com.aristidevs.infovotouns.network
import com.aristidevs.infovotouns.model.Candidato
import com.aristidevs.infovotouns.model.Evento
import com.aristidevs.infovotouns.model.Noticia
import com.aristidevs.infovotouns.model.Partido
import com.aristidevs.infovotouns.model.Propuesta
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
const val PARTIDOS_COLLECTION_NAME = "partidos"
const val CANDIDATOS_COLLECTION_NAME = "candidatos"
const val EVENTOS_COLLECTION_NAME = "eventos"
const val NOTICIAS_COLLECTION_NAME = "noticias"
class FirestoreService {

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val settings = FirebaseFirestoreSettings.Builder().setPersistenceEnabled(true).build()

    init {
        firebaseFirestore.firestoreSettings = settings
    }

    fun getPartidos(callback: Callback<List<Partido>>) {
        firebaseFirestore.collection(PARTIDOS_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                val list = result.toObjects(Partido::class.java)
                callback.onSuccess(list)
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }
    // NUEVO → Obtener todos los candidatos
    fun getCandidatos(callback: Callback<List<Candidato>>) {
        firebaseFirestore.collection(CANDIDATOS_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.map { doc ->
                    val candidato = doc.toObject(Candidato::class.java)
                    candidato.id = doc.id
                    candidato
                }
                callback.onSuccess(lista)
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }

    // NUEVO → Obtener propuestas de un candidato
    fun getPropuestasByCandidato(idCandidato: String, callback: Callback<List<Propuesta>>) {
        firebaseFirestore.collection(CANDIDATOS_COLLECTION_NAME)
            .document(idCandidato)
            .collection("propuestas")
            .get()
            .addOnSuccessListener { result ->
                val lista = result.map { doc ->
                    val propuesta = doc.toObject(Propuesta::class.java)
                    propuesta.id = doc.id
                    propuesta
                }
                callback.onSuccess(lista)
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }

    fun getEventos(callback: Callback<List<Evento>>) {
        firebaseFirestore.collection(EVENTOS_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.toObjects(Evento::class.java)
                callback.onSuccess(lista)
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }
    fun getNoticias(callback: Callback<List<Noticia>>) {
        firebaseFirestore.collection(NOTICIAS_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                val lista = result.toObjects(Noticia::class.java)
                callback.onSuccess(lista)
            }
            .addOnFailureListener { exception ->
                callback.onFailed(exception)
            }
    }
}
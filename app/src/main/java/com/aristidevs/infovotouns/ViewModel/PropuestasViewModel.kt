package com.aristidevs.infovotouns.ViewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristidevs.infovotouns.model.Propuesta
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class PropuestasViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    private val _propuestas = MutableLiveData<List<Propuesta>>()
    val propuestas: LiveData<List<Propuesta>> get() = _propuestas

    fun cargarPropuestas(candidatoId: String) {
        listener = db.collection("candidatos")
            .document(candidatoId)
            .collection("propuestas")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("PropuestasViewModel", "Error escuchando propuestas", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    _propuestas.value = snapshot.toObjects(Propuesta::class.java)
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}

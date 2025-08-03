package com.aristidevs.infovotouns.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristidevs.infovotouns.model.Evento
import com.aristidevs.infovotouns.network.FirestoreService
import com.aristidevs.infovotouns.network.Callback
class EventoViewModel : ViewModel() {

    private val firestoreService = FirestoreService()
    val listadoEventos: MutableLiveData<List<Evento>> = MutableLiveData()
    val isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        isLoading.value = true
        getEventosFromFirebase()
    }

    private fun getEventosFromFirebase() {
        firestoreService.getEventos(object : Callback<List<Evento>> {
            override fun onSuccess(result: List<Evento>?) {
                result?.let {
                    listadoEventos.postValue(it)
                }
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                Log.e("EventoViewModel", "Error al obtener eventos", exception)
                processFinished()
            }
        })
    }

    private fun processFinished() {
        isLoading.value = false
    }
}
package com.aristidevs.infovotouns.ViewModel



import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristidevs.infovotouns.model.Partido
import com.aristidevs.infovotouns.network.Callback
import com.aristidevs.infovotouns.network.FirestoreService
import java.lang.Exception

class PartidoViewModel : ViewModel() {

    val firestoreService = FirestoreService()
    var listadoPartidos: MutableLiveData<List<Partido>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        isLoading.value = true
        getPartidosFromFirebase()
    }

    private fun getPartidosFromFirebase() {
        firestoreService.getPartidos(object : Callback<List<Partido>> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onSuccess(result: List<Partido>?) {
                Log.d("PartidoViewModel", "Partidos recibidos: ${result?.size}")
                result?.forEach {
                    Log.d("PartidoViewModel", "Nombre: ${it.nombre} - Ideología: ${it.ideologia} - Logo: ${it.logo}")
                }
                listadoPartidos.postValue(result)
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                processFinished()
            }
        })
    }

    fun processFinished() {
        isLoading.value = false
    }
}

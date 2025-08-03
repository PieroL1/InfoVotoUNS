package com.aristidevs.infovotouns.ViewModel


import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristidevs.infovotouns.model.Candidato
import com.aristidevs.infovotouns.network.Callback
import com.aristidevs.infovotouns.network.FirestoreService
import java.lang.Exception

class CandidatoViewModel : ViewModel() {

    private val firestoreService = FirestoreService()
    var listadoCandidatos: MutableLiveData<List<Candidato>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        isLoading.value = true
        getCandidatosFromFirebase()
    }

    private fun getCandidatosFromFirebase() {
        firestoreService.getCandidatos(object : Callback<List<Candidato>> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onSuccess(result: List<Candidato>?) {
                Log.d("CandidatoViewModel", "Candidatos recibidos: ${result?.size}")
                result?.forEach {
                    Log.d("CandidatoViewModel", "Nombre: ${it.nombre} - Partido: ${it.partido}")
                }
                listadoCandidatos.postValue(result)
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                Log.e("CandidatoViewModel", "Error al obtener candidatos", exception)
                processFinished()
            }
        })
    }

    private fun processFinished() {
        isLoading.value = false
    }
}
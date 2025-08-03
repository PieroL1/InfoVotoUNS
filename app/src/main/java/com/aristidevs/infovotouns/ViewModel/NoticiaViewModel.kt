package com.aristidevs.infovotouns.ViewModel


import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aristidevs.infovotouns.model.Noticia
import com.aristidevs.infovotouns.network.Callback
import com.aristidevs.infovotouns.network.FirestoreService
import java.lang.Exception

class NoticiaViewModel : ViewModel() {

    private val firestoreService = FirestoreService()
    var listadoNoticias: MutableLiveData<List<Noticia>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh() {
        isLoading.value = true
        getNoticiasFromFirebase()
    }

    private fun getNoticiasFromFirebase() {
        firestoreService.getNoticias(object : Callback<List<Noticia>> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onSuccess(result: List<Noticia>?) {
                Log.d("NoticiaViewModel", "Noticias recibidas: ${result?.size}")
                result?.forEach {
                    Log.d("NoticiaViewModel", "Título: ${it.titulo} - Autor: ${it.autor} - Fecha: ${it.fecha}")
                }
                listadoNoticias.postValue(result)
                processFinished()
            }

            override fun onFailed(exception: Exception) {
                Log.e("NoticiaViewModel", "Error al obtener noticias", exception)
                processFinished()
            }
        })
    }

    private fun processFinished() {
        isLoading.value = false
    }
}

package com.aristidevs.infovotouns.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.google.android.material.snackbar.Snackbar
import android.widget.Toast
import android.app.Activity

class NetworkReceiver(val activity: Activity) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
        if (noConnectivity) {
            // Snackbar: se muestra en la vista principal de la activity
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                "Sin conexión a Internet",
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            // Opcional: mostrar conexión restaurada
            Snackbar.make(
                activity.findViewById(android.R.id.content),
                "¡Conexión restablecida!",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }
}

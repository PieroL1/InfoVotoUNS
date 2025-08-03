package com.aristidevs.infovotouns.View.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.aristidevs.infovotouns.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupWithNavController
import com.aristidevs.infovotouns.model.Partido
import com.aristidevs.infovotouns.model.Propuesta
import com.aristidevs.infovotouns.model.Candidato
import com.aristidevs.infovotouns.model.Evento
import com.aristidevs.infovotouns.model.Noticia
import com.google.firebase.firestore.FirebaseFirestore
import com.aristidevs.infovotouns.model.Usuario
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


import org.json.JSONArray
import org.json.JSONObject
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)



    }
}
package com.aristidevs.infovotouns.View.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.databinding.FragmentUbicationBinding
import com.aristidevs.infovotouns.model.Ubicacion
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import androidx.navigation.fragment.findNavController

class ubicationFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentUbicationBinding? = null
    private val binding get() = _binding!!

    private lateinit var googleMap: GoogleMap

    // Datos de ejemplo desde el modelo Ubicacion
    private val ubicacion = Ubicacion(
        nombre = "Centro Preuniversitario UNS",
        direccion = "Av. Universitaria, Nuevo Chimbote",
        foto = "https://lh3.googleusercontent.com/gps-cs-s/AC9h4nqodU9-raxPk8HEbVZKw8VbB1M1Y2ad-oDFEqnyH4hBzLzrc2QKdFk8yk6Orpdsdtk1IBqz2VGPuvYJ0JZePlBSxKq4CsSJbPwQdECTq9rYOlz_5SVPjLLG2bsUWkHhfeFtJ_IK=w408-h306-k-no",
        horario = "8:00 AM - 4:00 PM",
        descripcion = "Local de votación para estudiantes de la UNS",
        latitud = -9.119189,
        longitud = -78.515364
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUbicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val posicion = LatLng(ubicacion.latitud, ubicacion.longitud)

        googleMap.addMarker(
            MarkerOptions()
                .position(posicion)
                .title(ubicacion.nombre)
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion, 16f))

        googleMap.setOnMarkerClickListener {
            abrirDetalleUbicacion()
            true
        }
    }

    private fun abrirDetalleUbicacion() {
        val action = ubicationFragmentDirections
            .actionUbicationFragmentToUbicacionDetailFragment(ubicacion)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

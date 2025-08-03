package com.aristidevs.infovotouns.View.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.View.Adapter.PropuestasAdapter
import com.aristidevs.infovotouns.ViewModel.PropuestasViewModel
import com.aristidevs.infovotouns.databinding.FragmentProposalsDetailBinding

class ProposalsDetailFragment : DialogFragment() {

    private lateinit var binding: FragmentProposalsDetailBinding
    private lateinit var propuestasAdapter: PropuestasAdapter
    private lateinit var viewModel: PropuestasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProposalsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cerrar diálogo
        binding.toolbarPropuestasDetalle.setNavigationOnClickListener { dismiss() }

        // Configurar RecyclerView
        propuestasAdapter = PropuestasAdapter(emptyList())
        binding.rvListaPropuestas.layoutManager = LinearLayoutManager(requireContext())
        binding.rvListaPropuestas.adapter = propuestasAdapter

        // ViewModel
        viewModel = ViewModelProvider(this).get(PropuestasViewModel::class.java)

        // Obtener ID
        val candidatoId = arguments?.getString("candidatoId")
        if (!candidatoId.isNullOrEmpty()) {
            viewModel.cargarPropuestas(candidatoId)
        }

        // Observar cambios
        viewModel.propuestas.observe(viewLifecycleOwner) { lista ->
            propuestasAdapter.updateData(lista)
        }
    }
}

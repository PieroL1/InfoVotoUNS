package com.aristidevs.infovotouns.View.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.View.Adapter.EventoAdapter
import com.aristidevs.infovotouns.View.Adapter.EventoListener
import com.aristidevs.infovotouns.databinding.FragmentEventosBinding
import com.aristidevs.infovotouns.ViewModel.EventoViewModel
import com.aristidevs.infovotouns.model.Evento
class EventosFragment : Fragment(), EventoListener {

    private lateinit var adapter: EventoAdapter
    private lateinit var viewModel: EventoViewModel
    private lateinit var binding: FragmentEventosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel sin Hilt
        viewModel = ViewModelProvider(this).get(EventoViewModel::class.java)
        viewModel.refresh()

        // Adapter
        adapter = EventoAdapter(emptyList(), this)

        binding.rvEventos.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@EventosFragment.adapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.listadoEventos.observe(viewLifecycleOwner) { eventos ->
            adapter.updateData(eventos)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBarEventos.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onEventoClicked(evento: Evento) {
        val dialog = EventDetailFragment()
        dialog.arguments = bundleOf("evento" to evento)
        dialog.show(parentFragmentManager, "EventDetailDialog")
    }

}
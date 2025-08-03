package com.aristidevs.infovotouns.View.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristidevs.infovotouns.R
import com.aristidevs.infovotouns.View.Adapter.PartidoAdapter
import com.aristidevs.infovotouns.View.Adapter.PartidoListener
import com.aristidevs.infovotouns.ViewModel.PartidoViewModel
import com.aristidevs.infovotouns.databinding.FragmentPartidosBinding
import com.aristidevs.infovotouns.model.Partido


class PartidosFragment : Fragment(), PartidoListener {

    private lateinit var partidoAdapter: PartidoAdapter
    private lateinit var viewModel: PartidoViewModel
    private lateinit var binding: FragmentPartidosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPartidosBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        viewModel = ViewModelProvider(this).get(PartidoViewModel::class.java)
        viewModel.refresh()

        // Adapter
        partidoAdapter = PartidoAdapter(this)

        binding.recyclerPartidos.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = partidoAdapter
        }

        observeViewModel()
    }
    @SuppressLint("FragmentLiveDataObserve")
    private fun observeViewModel() {
        viewModel.listadoPartidos.observe(
            this,
            Observer<List<Partido>> { partidos ->
                partidoAdapter.updateData(partidos)
            }
        )

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { loading ->
            binding.progressBarPartidos.visibility = if (loading) View.VISIBLE else View.GONE
        })

    }


    override fun onPartidoClicked(partido: Partido, position: Int) {
        val dialog = PartidosDetailFragment()
        dialog.arguments = bundleOf("partido" to partido)
        dialog.show(parentFragmentManager, "PartidoDetailDialog")
    }
}
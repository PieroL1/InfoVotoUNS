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
import com.aristidevs.infovotouns.View.Adapter.CandidatoAdapter
import com.aristidevs.infovotouns.View.Adapter.CandidatoListener
import com.aristidevs.infovotouns.ViewModel.CandidatoViewModel
import com.aristidevs.infovotouns.databinding.FragmentCandidatosBinding
import com.aristidevs.infovotouns.model.Candidato

class candidatosFragment : Fragment(), CandidatoListener {

    private lateinit var candidatoAdapter: CandidatoAdapter
    private lateinit var viewModel: CandidatoViewModel
    private lateinit var binding: FragmentCandidatosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCandidatosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ViewModel
        viewModel = ViewModelProvider(this).get(CandidatoViewModel::class.java)
        viewModel.refresh()

        // Adapter
        candidatoAdapter = CandidatoAdapter(this)

        binding.rvCandidatos.apply {
            layoutManager = LinearLayoutManager(view.context)
            adapter = candidatoAdapter
        }

        observeViewModel()
    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observeViewModel() {
        viewModel.listadoCandidatos.observe(
            viewLifecycleOwner,
            Observer<List<Candidato>> { candidatos ->
                candidatoAdapter.updateData(candidatos)
            }
        )

        viewModel.isLoading.observe(viewLifecycleOwner, Observer { loading ->
            binding.progressBarCandidatos.visibility =
                if (loading) View.VISIBLE else View.GONE
        })
    }

    // Click en botón Info Personal
    override fun onInfoClicked(candidato: Candidato, position: Int) {
        val dialog = candidatoInfoDetailFragment()
        dialog.arguments = bundleOf("candidato" to candidato)
        dialog.show(parentFragmentManager, "CandidatoInfoDialog")
    }

    // Click en botón Propuestas
    override fun onPropuestasClicked(candidato: Candidato, position: Int) {
        val dialog = ProposalsDetailFragment()
        dialog.arguments = bundleOf("candidatoId" to candidato.id)
        dialog.show(parentFragmentManager, "PropuestasDialog")
    }
}
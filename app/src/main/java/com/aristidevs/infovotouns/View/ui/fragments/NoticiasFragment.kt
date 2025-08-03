package com.aristidevs.infovotouns.View.ui.fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aristidevs.infovotouns.View.Adapter.NoticiaAdapter
import com.aristidevs.infovotouns.View.Adapter.NoticiaListener
import com.aristidevs.infovotouns.ViewModel.NoticiaViewModel
import com.aristidevs.infovotouns.databinding.FragmentNoticiasBinding
import com.aristidevs.infovotouns.model.Noticia

class NoticiasFragment : Fragment(), NoticiaListener {

    private lateinit var adapter: NoticiaAdapter
    private lateinit var viewModel: NoticiaViewModel
    private lateinit var binding: FragmentNoticiasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoticiasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[NoticiaViewModel::class.java]
        viewModel.refresh()

        adapter = NoticiaAdapter(emptyList(), this)

        binding.rvNoticias.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@NoticiasFragment.adapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.listadoNoticias.observe(viewLifecycleOwner) { noticias ->
            adapter.updateData(noticias)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBarNoticias.visibility = if (loading) View.VISIBLE else View.GONE
        }
    }

    override fun onNoticiaClicked(noticia: Noticia) {
        val dialog = NoticiaDetailFragment()
        dialog.arguments = bundleOf("noticia" to noticia)
        dialog.show(parentFragmentManager, "NoticiaDetailDialog")
    }
}
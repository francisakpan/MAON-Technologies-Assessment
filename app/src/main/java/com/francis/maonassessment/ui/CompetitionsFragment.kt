package com.francis.maonassessment.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.francis.maonassessment.R
import com.francis.maonassessment.data.model.table.CompetitionEntity
import com.francis.maonassessment.data.type.Plan
import com.francis.maonassessment.databinding.FragmentCompetitionsBinding
import com.francis.maonassessment.ui.adapter.CompetitionAdapter
import com.francis.maonassessment.util.State
import com.francis.maonassessment.util.gone
import com.francis.maonassessment.util.showSnackBar
import com.francis.maonassessment.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class CompetitionsFragment : Fragment() {
    private lateinit var binding: FragmentCompetitionsBinding

    // Instantiate the viewModel
    private val viewModel: CompetitionFragmentViewModel by viewModels()

    private val adapter by lazy { CompetitionAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCompetitionsBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.competitionsLayout.adapter = adapter

        viewModel.competitions.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                adapter.submitData(it)
            }
        }

        viewModel.navEvent.observe(viewLifecycleOwner) { event ->
            val content = event.getContentIfNotHandled() as CompetitionEntity?
            if (content != null) {
                //When a competition is clicked, Navigate to the teams screen
                val action = CompetitionsFragmentDirections
                    .actionCompetitionsFragmentToTeamsFragment(id = content.id, name = content.name ?: "")
                findNavController().navigate(action)
            }
        }

        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> {
                    binding.placeholder.stopShimmer()
                    binding.placeholder.gone()
                    showSnackBar(requireActivity(), state.exception.message) {
                        viewModel.fetchCompetitions()
                    }
                }
                State.Loading -> {
                    binding.placeholder.startShimmer()
                    binding.placeholder.visible()
                }
                State.Success -> {
                    binding.placeholder.gone()
                    binding.placeholder.stopShimmer()
                }
                else -> {}
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.free -> viewModel.filter(Plan.FREE)
            R.id.basic -> viewModel.filter(Plan.BASIC)
            R.id.standard -> viewModel.filter(Plan.STANDARD)
            else -> viewModel.filter(Plan.ADVANCED)
        }
        return super.onOptionsItemSelected(item)
    }
}
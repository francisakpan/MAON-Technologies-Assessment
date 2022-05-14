package com.francis.maonassessment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.francis.maonassessment.data.model.table.TeamEntity
import com.francis.maonassessment.databinding.FragmentTeamBinding
import com.francis.maonassessment.ui.adapter.TeamsAdapter
import com.francis.maonassessment.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamsFragment : Fragment() {

    private lateinit var binding: FragmentTeamBinding

    // Instantiate the viewModel
    private val viewModel: TeamsViewModel by viewModels()
    private val args by navArgs<TeamsFragmentArgs>()

    private val adapter by lazy {
        TeamsAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamBinding.inflate(layoutInflater)
        setupToolsBar(args.name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Fetch Teams
        viewModel.fetchTeamsInCompetition(args.id)

        binding.teamsLayout.adapter = adapter

        viewModel.getTeamsInCompetition(args.id).observe(viewLifecycleOwner) { teams ->
            lifecycleScope.launch {
                adapter.submitList(teams.toMutableList())
            }
        }

        viewModel.navEvent.observe(viewLifecycleOwner) { event ->
            val content = event.getContentIfNotHandled()
            if (content != null) {
                val action =
                    TeamsFragmentDirections.actionTeamsFragmentToTeamDetailsFragment(content as TeamEntity?)
                findNavController().navigate(action)
            }
        }

        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> {
                    binding.placeholder.stopShimmer()
                    binding.placeholder.gone()
                    showSnackBar(requireActivity(), state.exception.message) {
                        viewModel.fetchTeamsInCompetition(args.id)
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
}
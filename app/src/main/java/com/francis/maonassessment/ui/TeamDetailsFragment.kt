package com.francis.maonassessment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.francis.maonassessment.R
import com.francis.maonassessment.databinding.FragmentTeamDetailsBinding
import com.francis.maonassessment.ui.adapter.SquadAdapter
import com.francis.maonassessment.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsFragment : Fragment() {

    private lateinit var binding: FragmentTeamDetailsBinding
    private val args by navArgs<TeamDetailsFragmentArgs>()

    private val viewModel: TeamsViewModel by viewModels()

    private val adapter by lazy { SquadAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_team_details, container, false)
        binding.teamData = args.teamData
        setupToolsBar(args.teamData?.name)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.teamData?.teamId?.let { viewModel.fetchTeamSquad(it) }

        binding.playersRv.adapter = adapter

        args.teamData?.teamId?.let {
            viewModel.getTeamSquad(it).observe(viewLifecycleOwner) { playerEntity ->
                adapter.submitList(playerEntity.toMutableList())
            }
        }

        viewModel.result.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Error -> {
                    binding.placeholder.stopShimmer()
                    binding.placeholder.gone()
                    showSnackBar(requireActivity(), state.exception.message) {
                        viewModel.fetchTeamsInCompetition(args.teamData?.teamId ?: return@showSnackBar)
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
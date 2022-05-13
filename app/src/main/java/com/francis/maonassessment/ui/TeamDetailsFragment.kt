package com.francis.maonassessment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.francis.maonassessment.R
import com.francis.maonassessment.databinding.FragmentTeamDetailsBinding

class TeamDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTeamDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeamDetailsBinding.inflate(layoutInflater)
        return binding.root
    }
}
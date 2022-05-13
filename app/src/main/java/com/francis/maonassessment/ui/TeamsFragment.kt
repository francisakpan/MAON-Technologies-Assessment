package com.francis.maonassessment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.francis.maonassessment.databinding.FragmentTeamBinding

class TeamsFragment : Fragment() {

    private lateinit var binding: FragmentTeamBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamBinding.inflate(layoutInflater)
        return binding.root
    }
}
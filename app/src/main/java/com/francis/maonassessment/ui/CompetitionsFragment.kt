package com.francis.maonassessment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.francis.maonassessment.databinding.FragmentCompetitionsBinding


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class CompetitionsFragment : Fragment() {
    private lateinit var binding: FragmentCompetitionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCompetitionsBinding.inflate(layoutInflater)
        return binding.root
    }
}
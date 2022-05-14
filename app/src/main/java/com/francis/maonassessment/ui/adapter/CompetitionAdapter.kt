package com.francis.maonassessment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.francis.maonassessment.R
import com.francis.maonassessment.data.model.table.CompetitionEntity
import com.francis.maonassessment.databinding.LayoutCompetitionBinding
import com.francis.maonassessment.ui.CompetitionFragmentViewModel

class CompetitionAdapter(
    private val viewModel: CompetitionFragmentViewModel
) : PagingDataAdapter<CompetitionEntity, CompetitionAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: LayoutCompetitionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CompetitionEntity, viewModel: CompetitionFragmentViewModel) {
            binding.data = data
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, viewModel) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutCompetitionBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_competition, parent, false)
        return ViewHolder(binding)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CompetitionEntity>() {
            override fun areItemsTheSame(
                oldItem: CompetitionEntity,
                newItem: CompetitionEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CompetitionEntity,
                newItem: CompetitionEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
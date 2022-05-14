package com.francis.maonassessment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.francis.maonassessment.R
import com.francis.maonassessment.data.model.table.TeamEntity
import com.francis.maonassessment.databinding.LayoutTeamsBinding
import com.francis.maonassessment.ui.TeamsViewModel

class TeamsAdapter(private val viewModel: TeamsViewModel) :
    ListAdapter<TeamEntity, TeamsAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: LayoutTeamsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TeamEntity, viewModel: TeamsViewModel) {
            binding.teamData = data
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutTeamsBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_teams, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TeamEntity>() {
            override fun areItemsTheSame(oldItem: TeamEntity, newItem: TeamEntity): Boolean {
                return oldItem.teamId == newItem.teamId
            }

            override fun areContentsTheSame(
                oldItem: TeamEntity,
                newItem: TeamEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
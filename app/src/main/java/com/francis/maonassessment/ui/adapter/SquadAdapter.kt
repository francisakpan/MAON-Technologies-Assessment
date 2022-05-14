package com.francis.maonassessment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.francis.maonassessment.R
import com.francis.maonassessment.data.model.table.PlayerEntity
import com.francis.maonassessment.databinding.LayoutPlayerBinding

class SquadAdapter : ListAdapter<PlayerEntity, SquadAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val binding: LayoutPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PlayerEntity) {
            binding.player = data
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: LayoutPlayerBinding =
            DataBindingUtil.inflate(inflater, R.layout.layout_player, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PlayerEntity>() {
            override fun areItemsTheSame(oldItem: PlayerEntity, newItem: PlayerEntity): Boolean {
                return oldItem.playerId == newItem.playerId
            }

            override fun areContentsTheSame(
                oldItem: PlayerEntity,
                newItem: PlayerEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
package com.francis.maonassessment.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.francis.maonassessment.R
import com.francis.maonassessment.databinding.ItemColorBinding

class ItemColorAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<ItemColorAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(color: String) {
            binding.color = color
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemColorAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: ItemColorBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_color, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemColorAdapter.ViewHolder, position: Int) {
        val color = items[position].let { if (it == "white") "lightgrey" else it }
        holder.bind(color)
    }

    override fun getItemCount() = items.size

}
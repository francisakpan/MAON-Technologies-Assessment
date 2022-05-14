package com.francis.maonassessment.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.francis.maonassessment.R

class ItemColorAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<ItemColorAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: FrameLayout = view.findViewById(R.id.color_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemColorAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemColorAdapter.ViewHolder, position: Int) {

        val color = items[position].let { if (it == "white") "lightgrey" else it }

        val getColor = try {
            Color.parseColor(color)
        } catch (e: Exception) {
            Color.parseColor("white")
        }

        var background = holder.container.background
        background = DrawableCompat.wrap(background)
        DrawableCompat.setTint(background, getColor)
        holder.container.background = background
    }

    override fun getItemCount() = items.size

}
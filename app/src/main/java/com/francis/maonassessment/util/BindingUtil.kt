package com.francis.maonassessment.util

import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.francis.maonassessment.R
import com.francis.maonassessment.data.model.table.CompetitionEntity
import com.francis.maonassessment.data.model.table.PlayerEntity
import com.francis.maonassessment.data.model.table.TeamEntity
import com.francis.maonassessment.ui.adapter.ItemColorAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageUrl")
fun ImageView.loadImage(team: TeamEntity?) {
    // Use Coil image Library to load network images to the imageview
    val imageLoader = ImageLoader.Builder(this.context)
        .components {
            add(SvgDecoder.Factory())
        }.build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(100)
        .placeholder(R.drawable.football_crest_placeholder)
        .data(team?.crestUrl)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

@BindingAdapter("initials")
fun TextView.setInitials(player: PlayerEntity?) {
    val arr = player?.name?.split(" ")?.toTypedArray()
    
    // Pick the first characters of the first and last name as initials
    val initials = arr?.foldIndexed(StringBuilder()) { index, prev, element ->
        prev.append(
            if (index == 0 || index == arr.size - 1) element[0]
            else ""
        )
    }.toString()
    text = initials
}

@BindingAdapter("date")
fun TextView.setStartDate(competition: CompetitionEntity) {
    val startDate = context.getString(
        R.string.start_date,
        competition.currentSeason?.startDate ?: ""
    )
    val spanText = context.getString(R.string.start_date_span)
    val span = SpannableString(startDate)
    span.setSpan(StyleSpan(Typeface.BOLD), 0, spanText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = span
}

@BindingAdapter("url")
fun TextView.setUrl(team: TeamEntity?) {
    team ?: return
    val url = team.website ?: return
    val span = SpannableString(url)
    span.setSpan(URLSpan(url), 0, url.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = span
}

@BindingAdapter("dob")
fun TextView.setDob(player: PlayerEntity?) {
    player ?: return
    text = try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        inputFormat.parse(player.dateOfBirth ?: return)?.let { outputFormat.format(it) }
    } catch (e: Exception) {
        player.dateOfBirth
    }
}

@BindingAdapter("colors")
fun RecyclerView.displayColors(team: TeamEntity?) {
    team ?: return
    try {
        //Since club colors come as a string. Using the delimeter split colors into an array of strings
        val colors = team.clubColors?.split("/")?.map { it.trim().lowercase() } ?: return
        adapter = ItemColorAdapter(ArrayList(colors))
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@BindingAdapter("color")
fun FrameLayout.setColor(color: String?) {
    color ?: return

    val getColor = try {
        Color.parseColor(color)
    } catch (e: Exception) {
        Color.parseColor("white")
    }

    var bg = background
    bg = DrawableCompat.wrap(bg)
    DrawableCompat.setTint(bg, getColor)
    background = bg
}

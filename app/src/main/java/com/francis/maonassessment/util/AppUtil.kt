package com.francis.maonassessment.util

import android.app.Activity
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(activity: Activity, message: String?, action: (View) -> Unit) {
    Snackbar.make(
        activity.findViewById(android.R.id.content),
        message!!, Snackbar.LENGTH_LONG
    ).apply {
        setAction("Reload", action)
        // Changing action button text color
        val textView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)
        show()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun Fragment.setupToolsBar(title: String?) {
    (requireActivity() as AppCompatActivity).supportActionBar?.title = title ?: ""
}
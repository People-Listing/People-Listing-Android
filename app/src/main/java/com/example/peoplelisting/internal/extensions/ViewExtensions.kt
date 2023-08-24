package com.example.peoplelisting.internal.extensions

import android.opengl.Visibility
import android.os.Build
import android.view.View
import androidx.appcompat.widget.TooltipCompat

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setToolTip(text: CharSequence) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        tooltipText = text
    } else {
        TooltipCompat.setTooltipText(this, text)
    }
}
package com.example.peoplelisting.internal.extensions

import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.startIgnoringTouchEvents() {
    window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun AppCompatActivity.stopIgnoringTouchEvents() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}
package com.example.peoplelisting.internal.extensions

import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

fun ComponentActivity.startIgnoringTouchEvents() {
    window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun ComponentActivity.stopIgnoringTouchEvents() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}
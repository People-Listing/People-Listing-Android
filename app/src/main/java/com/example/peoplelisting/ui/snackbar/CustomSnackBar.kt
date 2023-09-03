package com.example.peoplelisting.ui.snackbar

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.peoplelisting.R
import com.example.peoplelisting.databinding.SnackbarLayoutBinding
import com.example.peoplelisting.internal.extensions.hide
import com.example.peoplelisting.internal.extensions.show
import com.example.peoplelisting.internal.utilities.getColor
import com.example.peoplelisting.internal.utilities.getColorStateList
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.SnackbarLayout

class CustomSnackBar(lifecycleOwner: LifecycleOwner): DefaultLifecycleObserver {
    private var currentSnackBar: Snackbar? = null
    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        currentSnackBar?.apply { if(isShown) dismiss() }
        currentSnackBar = null
        super.onDestroy(owner)
    }

    fun showSnackBar(view: View, data: SnackBarData) {
        val snack = Builder()
            .setView(view, data.duration)
            .setMessage(data.message)
            .setColor(data.color)
            .setButtonData(data.snackBarButtonData)
            .build()
        snack.show()
        currentSnackBar = snack
    }

    inner class Builder {
        private lateinit var snackBar: Snackbar
        private lateinit var binding: SnackbarLayoutBinding
        @SuppressLint("InflateParams")
        fun setView(view: View, duration: Int): Builder {
            val context = view.context
            val customLayout = LayoutInflater.from(context).inflate(R.layout.snackbar_layout, null)
            snackBar = Snackbar.make(view,"", duration)
            val snackBarLayout = snackBar.view as SnackbarLayout
            snackBarLayout.setBackgroundColor(Color.TRANSPARENT)
            binding = SnackbarLayoutBinding.bind(customLayout)
            snackBarLayout.addView(customLayout)
            return this


        }

        fun setMessage(message: String): Builder {
            binding.message.text = message
            return this

        }

        fun setColor(@ColorRes color: Int): Builder {
            binding.root.backgroundTintList = getColorStateList(color)
            return this

        }

        fun setButtonData(buttonData: SnackBarButtonData?):  Builder {
            buttonData?.apply {
                binding.button.apply {
                    show()
                    text = buttonData.title
                    setTextColor(getColor(buttonData.titleColor))
                    setOnClickListener{
                        buttonData.listener.invoke()
                        snackBar.dismiss()
                    }
                    backgroundTintList = getColorStateList(buttonData.backgroundColor)
                }
            } ?: kotlin.run {
                binding.closeButton.show()
                binding.closeButton.setOnClickListener {
                    snackBar.dismiss()
                }
                binding.button.hide()
            }
            return this
        }

        fun build(): Snackbar {
            return snackBar
        }

    }
}
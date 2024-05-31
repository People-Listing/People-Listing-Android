package com.example.peoplelisting.ui.snackbar

import android.view.View
import androidx.annotation.ColorRes
import com.example.peoplelisting.R
import com.example.peoplelisting.internal.utilities.getString

class SnackBarData(
    val message: String,
    val duration: Long? = null,
    @ColorRes val color: Int = R.color.redColor,
    val snackBarButtonData: SnackBarButtonData? = null
) {
}

class SnackBarButtonData(
    val title: String = getString(R.string.try_again),
    @ColorRes val titleColor: Int = R.color.redColor,
    val listener: () -> Unit = {},
    @ColorRes val backgroundColor: Int = R.color.white
)
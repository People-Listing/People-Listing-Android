package com.example.peoplelisting.ui.snackbar

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.example.peoplelisting.R

class SnackBarData(
    val message: String,
    val duration: Long? = null,
    @ColorRes val color: Int = R.color.redColor,
    val snackBarButtonData: SnackBarButtonData? = null
) {
}

class SnackBarButtonData(
    @StringRes val title: Int = R.string.try_again,
    @ColorRes val titleColor: Int = R.color.redColor,
    val listener: () -> Unit = {},
    @ColorRes val backgroundColor: Int = R.color.white
)
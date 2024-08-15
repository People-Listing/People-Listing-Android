package com.example.peoplelisting.internal.handlers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.peoplelisting.R
import com.example.peoplelisting.data.network.adapters.NetworkResponse
import com.example.peoplelisting.ui.snackbar.ComposeSnackBar
import com.example.peoplelisting.ui.snackbar.SnackBarButtonData
import com.example.peoplelisting.ui.snackbar.SnackBarData

@Composable
fun AppErrorHandler(
    modifier: Modifier = Modifier,
    failure: NetworkResponse.Failure,
    errorMessage: String,
    duration: Long?,
    tryAgain: (() -> Unit)?
) {
    val buttonData: SnackBarButtonData? = tryAgain?.let {
        SnackBarButtonData(listener = it)
    } ?: run {
        null
    }
    val displayErrorMessage: String = when (failure) {
        is NetworkResponse.Failure.NetworkError -> {
            stringResource(id = R.string.no_internet)
        }

        else -> {
            errorMessage
        }
    }
    ComposeSnackBar(
        modifier = modifier,
        snackBarData = SnackBarData(
            displayErrorMessage,
            duration = duration,
            snackBarButtonData = buttonData
        )
    )
}
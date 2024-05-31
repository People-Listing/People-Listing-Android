package com.example.peoplelisting.ui.snackbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.theme.AppTheme
import com.example.peoplelisting.ui.theme.din
import com.example.peoplelisting.ui.theme.gilroy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ComposeSnackBar(modifier: Modifier = Modifier, snackBarData: SnackBarData) {
    val hostState = remember {
        SnackbarHostState()
    }
    SnackbarHost(hostState = hostState, modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(
                    colorResource(id = snackBarData.color), shape =
                    RoundedCornerShape(15.dp)
                )
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = snackBarData.message, style = TextStyle(
                        fontFamily = din, fontWeight = FontWeight.Normal,
                        fontSize = 13.sp
                    ), color = Color.White, modifier = Modifier.weight(1f)
                )
                snackBarData.snackBarButtonData?.apply {
                    Button(
                        onClick = this.listener, colors = ButtonDefaults.buttonColors().copy(
                            containerColor = colorResource(id = this.backgroundColor)
                        )
                    ) {
                        Text(
                            text = title, color = colorResource(id = titleColor), style = TextStyle(
                                fontFamily =
                                gilroy, fontWeight = FontWeight.ExtraBold, fontSize = 13.sp
                            )
                        )
                    }
                } ?: run {
                    IconButton(
                        onClick = { hostState.currentSnackbarData?.dismiss() },
                        colors = IconButtonDefaults.iconButtonColors()
                            .copy(
                                containerColor = Color.Transparent
                            )
                    ) {
                        Image(painter = painterResource(id = R.drawable.ic_close), contentDescription = null)
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        val job = launch {
            hostState.showSnackbar(message = snackBarData.message, duration = SnackbarDuration.Indefinite)
        }
        snackBarData.duration?.apply {
            delay(this)
            job.cancel()
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ComposeSnackBarPreview() {
    AppTheme {
        ComposeSnackBar(snackBarData = SnackBarData("Error"))
    }
}
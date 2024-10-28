package com.example.peoplelisting.ui.createpeople.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.theme.AppColor
import com.example.peoplelisting.ui.theme.AppTheme
import com.example.peoplelisting.ui.theme.din


@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isEnabled: Boolean,
    alpha: Float,
    isLoading: Boolean
) {
    ElevatedButton(
        enabled = isEnabled,
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(3.dp, AppColor.colorSecondary),
        modifier = modifier
            .heightIn(min = 60.dp)
            .padding(horizontal = 32.dp)
            .fillMaxWidth()
            .alpha(alpha),
        colors = ButtonColors(
            containerColor = AppColor.colorPrimary,
            contentColor = Color.White,
            disabledContainerColor = AppColor.colorPrimary,
            disabledContentColor = Color.White
        )
    ) {
        Row {
            if (isLoading) CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                strokeWidth = 3.dp,
                color = Color.White
            ) else
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.add_person), style = TextStyle(
                        fontFamily = din,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RoundedButtonPreview() {
    AppTheme {
        RoundedButton(modifier = Modifier, {}, true, 1f, false)
    }

}
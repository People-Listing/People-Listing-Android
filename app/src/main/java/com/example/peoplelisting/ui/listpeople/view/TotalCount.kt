package com.example.peoplelisting.ui.listpeople.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.theme.AppColor
import com.example.peoplelisting.ui.theme.AppTheme
import com.example.peoplelisting.ui.theme.din

@Composable
fun TotalCount(modifier: Modifier = Modifier, totalCount: Int) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.total_count))
        append("\u00A0")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontFamily = din)) {
            append(totalCount.toString())
        }
    }

    Text(
        text = annotatedString, style = TextStyle(
            color =
            AppColor
                .colorSecondary, fontFamily = din, fontWeight = FontWeight.Normal, fontSize = 16.sp
        ), textAlign = TextAlign.Start
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TotalCountPreview() {
    AppTheme {
        TotalCount(totalCount = 3)
    }
}
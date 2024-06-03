package com.example.peoplelisting.ui.screens.createpeople.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peoplelisting.ui.theme.AppColor
import com.example.peoplelisting.ui.theme.AppTheme
import com.example.peoplelisting.ui.theme.din

@Composable
fun InfoEntry(
    modifier: Modifier = Modifier, keyboardType: KeyboardType = KeyboardType.Text, info: String, hint: String, title:
    String,
    onInfoChange: (String) ->
    Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.padding(horizontal = 22.dp)
    ) {
        Text(
            text = title, modifier = Modifier.padding(start = 8.dp), style = TextStyle(
                fontFamily = din, fontWeight
                = FontWeight.Medium, fontSize = 18.sp, color = AppColor.colorSecondary
            )
        )
        InfoTextField(info = info, hint = hint, onInfoChanged = onInfoChange, keyboardType = keyboardType)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun InfoEntryPreview() {
    AppTheme {
        InfoEntry(info = "", title = "Firstname", hint = "E.g. Omar") {}
    }
}
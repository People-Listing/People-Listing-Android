package com.example.peoplelisting.ui.screens.createpeople.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peoplelisting.R
import com.example.peoplelisting.ui.theme.AppColor
import com.example.peoplelisting.ui.theme.AppTheme
import com.example.peoplelisting.ui.theme.din

@Composable
fun InfoTextField(modifier: Modifier = Modifier,keyboardType: KeyboardType = KeyboardType.Text, info: String, hint:
String = "", onInfoChanged: (String) -> Unit) {
    OutlinedTextField(
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .border(3.dp, AppColor.colorPrimary, RoundedCornerShape(16.dp)),
        singleLine = true,
        value = info,
        onValueChange = onInfoChanged,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = AppColor.colorSecondary,
            unfocusedContainerColor = AppColor.colorSecondary,
        ),
        textStyle = TextStyle(
            fontFamily = din,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = AppColor.colorPrimary50,
        ),
        placeholder = {
            Text(
                text = hint, style = TextStyle(
                    fontFamily = din,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = AppColor.colorPrimary50,
                )
            )
        }
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun InfoTextFieldPreview() {
    AppTheme {
        InfoTextField(info = "", hint = stringResource(id = R.string.first_name)) {}
    }
}
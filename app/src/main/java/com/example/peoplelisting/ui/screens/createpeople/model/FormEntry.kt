package com.example.peoplelisting.ui.screens.createpeople.model

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

class FormEntry(@StringRes val hint: Int, @StringRes val title: Int, val entryType: EntryType, value: String
= "", val keyboardType: KeyboardType = KeyboardType.Text) {
    var valueState by mutableStateOf(value)
}


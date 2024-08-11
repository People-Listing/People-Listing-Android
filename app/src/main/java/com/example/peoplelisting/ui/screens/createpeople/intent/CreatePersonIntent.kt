package com.example.peoplelisting.ui.screens.createpeople.intent

import com.example.peoplelisting.ui.screens.base.ViewIntent
import com.example.peoplelisting.ui.screens.createpeople.model.EntryType

sealed interface CreatePersonIntent : ViewIntent {

    data class SetEntry(val entryType: EntryType, val value: String) : CreatePersonIntent

    data object CreatePerson : CreatePersonIntent
}
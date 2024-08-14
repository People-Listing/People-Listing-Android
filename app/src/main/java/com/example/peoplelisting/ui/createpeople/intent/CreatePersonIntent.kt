package com.example.peoplelisting.ui.createpeople.intent

import com.example.peoplelisting.ui.base.ViewIntent
import com.example.peoplelisting.ui.createpeople.model.EntryType

sealed interface CreatePersonIntent : ViewIntent {

    data class SetEntry(val entryType: EntryType, val value: String) : CreatePersonIntent

    data object CreatePerson : CreatePersonIntent
}
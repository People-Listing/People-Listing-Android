package com.example.peoplelisting.ui.createpeople.view

import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.network.adapters.NetworkResponse
import com.example.peoplelisting.ui.base.view.UiEffect
import com.example.peoplelisting.ui.base.view.UiEvent
import com.example.peoplelisting.ui.base.view.UiState
import com.example.peoplelisting.ui.createpeople.model.EntryType
import com.example.peoplelisting.ui.createpeople.model.FormEntry

data class CreatePersonUiState(
    val isLoading: Boolean,
    val isButtonEnabled: Boolean,
    val entries: List<FormEntry>
) : UiState {
    val buttonAlpha = if (isButtonEnabled) 1.0f else 0.5f
}

sealed interface CreatePersonUiEvent : UiEvent {
    data class SetEntry(val entryType: EntryType, val value: String) : CreatePersonUiEvent
    data object CreatePerson : CreatePersonUiEvent
}


sealed interface CreatePersonEffect : UiEffect {
    data class ShowError(
        val duration: Long?,
        val failure: NetworkResponse.Failure
    ) : CreatePersonEffect

    data class Done(val person: PersonDto) : CreatePersonEffect
}
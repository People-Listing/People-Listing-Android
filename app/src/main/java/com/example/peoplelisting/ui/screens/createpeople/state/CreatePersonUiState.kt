package com.example.peoplelisting.ui.screens.createpeople.state

import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.ui.screens.base.UiState
import com.example.peoplelisting.ui.screens.createpeople.model.FormEntry

sealed interface CreatePersonUiState : UiState {
    data class Normal(val entries: List<FormEntry>, val isButtonEnabled: Boolean) :
        CreatePersonUiState

    data class Loading(val entries: List<FormEntry>) : CreatePersonUiState

    data class Success(val person: PersonDto) : CreatePersonUiState
}
package com.example.peoplelisting.ui.createpeople.state

import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.ui.base.UiState
import com.example.peoplelisting.ui.createpeople.model.FormEntry

sealed interface CreatePersonUiState : UiState {
    data class Normal(val isButtonEnabled: Boolean) :
        CreatePersonUiState

    data object Loading : CreatePersonUiState

    data class Success(val person: PersonDto) : CreatePersonUiState
}
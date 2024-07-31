package com.example.peoplelisting.ui.screens.listpeople.state

import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.ui.screens.base.UiState

sealed interface PeopleListingUiState : UiState {
    data object FETCHING : PeopleListingUiState
    data class NORMAL(val people: List<PersonDto>, val isRefreshing: Boolean = false) : PeopleListingUiState
}
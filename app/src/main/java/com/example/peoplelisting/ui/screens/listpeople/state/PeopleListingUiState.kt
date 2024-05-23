package com.example.peoplelisting.ui.screens.listpeople.state

import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.ui.screens.base.UiState

sealed interface PeopleListingUiState: UiState {
    data object LOADING: PeopleListingUiState
    data class NORMAL(val people: List<PersonDto>) : PeopleListingUiState

    data class REFRESHING(val people: List<PersonDto>) : PeopleListingUiState
}
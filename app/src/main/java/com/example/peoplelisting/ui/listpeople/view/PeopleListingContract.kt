package com.example.peoplelisting.ui.listpeople.view

import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.network.adapters.NetworkResponse
import com.example.peoplelisting.ui.base.view.UiEffect
import com.example.peoplelisting.ui.base.view.UiEvent
import com.example.peoplelisting.ui.base.view.UiState

data class PeopleListingUiState(
    var isLoading: Boolean = false,
    var people: List<PersonDto> = listOf(),
    var isRefreshing: Boolean = false
) : UiState

sealed interface PeopleListingUiEvent : UiEvent {
    data object LoadData : PeopleListingUiEvent
    data object RefreshData : PeopleListingUiEvent
    data object OnCreateClicked : PeopleListingUiEvent
}

sealed interface PeopleListingEffect : UiEffect {
    data class ShowError(
        val duration: Long?,
        val failure: NetworkResponse.Failure,
        val tryAgain: (() -> Unit)? = null
    ) : PeopleListingEffect

    data object NavigateToCreate : PeopleListingEffect
}
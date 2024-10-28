package com.example.peoplelisting.ui.listpeople.view

import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.network.adapters.NetworkResponse
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.ui.base.view.BaseViewModel
import kotlinx.coroutines.launch


class ListUsersViewModel(private val peopleRepository: PeopleRepository) :
    BaseViewModel<PeopleListingUiState, PeopleListingEffect, PeopleListingUiEvent>() {
    private var isFetched: Boolean = false
    fun setFreshlyCreatedUser(personDto: PersonDto) {
        val current = _uiState.value?.people?.toMutableList()
            ?: mutableListOf()
        current.add(0, personDto)
        setState { copy(people = current) }
    }


    private fun getMyPeople(checkIfFetched: Boolean = false, isRefreshing: Boolean = false) {
        if (checkIfFetched && isFetched) return
        viewModelScope.launch {
            if (isRefreshing) {
                setState { copy(isRefreshing = true, isLoading = false) }
            } else {
                setState { copy(isRefreshing = false, isLoading = true) }
            }
            sendEffect(null)
            when (val response = peopleRepository.getUsers()) {
                is NetworkResponse.Success -> {
                    isFetched = true
                    val persons = response.body
                    val personsDto = mutableListOf<PersonDto>()
                    persons?.forEach {
                        personsDto.add(it.toPersonDto())
                    }
                    setState { copy(isLoading = false, isRefreshing = false, people = personsDto) }
                    sendEffect(null)
                }

                is NetworkResponse.Failure -> {
                    if(isRefreshing)  setState { copy(isRefreshing = false) }
                    val duration =
                        if (_uiState.value?.people == null) SHOW_SNACK_BAR_DURATION else null
                    val tryAgain = if (_uiState.value?.isLoading == true) {
                        {
                            handleEvent(PeopleListingUiEvent.LoadData)
                        }
                    } else null
                    sendEffect(
                        PeopleListingEffect.ShowError(
                            duration,
                            response,
                            tryAgain = tryAgain
                        )
                    )
                }
            }
        }
    }

    override fun handleEvent(event: PeopleListingUiEvent) {
        when (event) {
            PeopleListingUiEvent.LoadData -> getMyPeople(checkIfFetched = true)
            PeopleListingUiEvent.RefreshData -> getMyPeople(
                checkIfFetched = false,
                isRefreshing = true
            )
            PeopleListingUiEvent.OnCreateClicked -> {
                sendEffect(PeopleListingEffect.NavigateToCreate)
            }
        }
    }

    override fun createInitialState() = PeopleListingUiState(isLoading = true)

    companion object {
        const val SHOW_SNACK_BAR_DURATION = 10_000L
    }


}
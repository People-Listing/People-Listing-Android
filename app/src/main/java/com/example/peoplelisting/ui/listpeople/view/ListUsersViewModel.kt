package com.example.peoplelisting.ui.listpeople.view

import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.network.adapters.NetworkResponse
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.ui.base.BaseViewModel
import com.example.peoplelisting.ui.base.ViewIntent
import com.example.peoplelisting.ui.listpeople.intent.PeopleListingViewIntent
import com.example.peoplelisting.ui.listpeople.state.PeopleListingUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException


class ListUsersViewModel(private val peopleRepository: PeopleRepository) :
    BaseViewModel<PeopleListingUiState>() {
    private var isFetched: Boolean = false
    fun setFreshlyCreatedUser(personDto: PersonDto) {
        val current = (_uiState.value as? PeopleListingUiState.NORMAL)?.people?.toMutableList()
            ?: mutableListOf()
        current.add(0, personDto)
        _uiState.value = PeopleListingUiState.NORMAL(current)
    }


    private fun getMyPeople(checkIfFetched: Boolean = false, isRefreshing: Boolean = false) {
        if (checkIfFetched && isFetched) return
        viewModelScope.launch {
            if (isRefreshing) {
                _uiState.value =
                    (_uiState.value as PeopleListingUiState.NORMAL).copy(isRefreshing = true)
            } else {
                _uiState.value = PeopleListingUiState.FETCHING
            }
            _errorState.value = null
            when(val response = peopleRepository.getUsers()) {
                is NetworkResponse.Success -> {
                    isFetched = true
                    val persons = response.body
                    val personsDto = mutableListOf<PersonDto>()
                    persons?.forEach {
                        personsDto.add(it.toPersonDto())
                    }
                    _uiState.value = PeopleListingUiState.NORMAL(personsDto)
                    _errorState.value = null
                }
                is NetworkResponse.Failure -> {
                    if (isRefreshing) {
                        _uiState.value =
                            (_uiState.value as PeopleListingUiState.NORMAL).copy(isRefreshing = false)
                    }
                    _errorState.value = response
                }
            }
        }
    }

    override fun handleIntent(intent: ViewIntent) {
        when (intent) {
            PeopleListingViewIntent.LoadData -> getMyPeople(checkIfFetched = true)
            PeopleListingViewIntent.RefreshData -> getMyPeople(
                checkIfFetched = false,
                isRefreshing = true
            )
        }
    }
}
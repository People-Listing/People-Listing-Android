package com.example.peoplelisting.ui.listuser

import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.network.NetworkResponse
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.ui.screens.base.BaseViewModel
import com.example.peoplelisting.ui.screens.base.ViewIntent
import com.example.peoplelisting.ui.screens.listpeople.intent.PeopleListingViewIntent
import com.example.peoplelisting.ui.screens.listpeople.state.PeopleListingUiState
import kotlinx.coroutines.launch
import java.io.IOException


class ListUsersViewModel(private val peopleRepository: PeopleRepository) :
    BaseViewModel<PeopleListingViewIntent, PeopleListingUiState>() {
    private var isFetched: Boolean = false
    fun setFreshlyCreatedUser(personDto: PersonDto) {
        val current = (_uiState.value as? PeopleListingUiState.NORMAL)?.people?.toMutableList() ?: mutableListOf()
        current.add(0, personDto)
        _uiState.value = PeopleListingUiState.NORMAL(current)
    }


    private fun getMyPeople(checkIfFetched: Boolean = false, isRefreshing: Boolean = false) {
        if (checkIfFetched && isFetched) return
        viewModelScope.launch {
            if (isRefreshing) {
                _uiState.value = (_uiState.value as PeopleListingUiState.NORMAL).copy(isRefreshing = true)
            } else {
                _uiState.value = PeopleListingUiState.FETCHING
            }
            _errorState.value = null
            try {
                val res = peopleRepository.getUsers()
                if (res.isSuccessful) {
                    isFetched = true
                    val persons = res.body()
                    val personsDto = mutableListOf<PersonDto>()
                    persons?.forEach {
                        personsDto.add(it.toPersonDto())
                    }
                    _uiState.value = PeopleListingUiState.NORMAL(personsDto)
                    _errorState.value = null
                } else {
                    if (isRefreshing) {
                        _uiState.value = (_uiState.value as PeopleListingUiState.NORMAL).copy(isRefreshing = false)
                    }
                    _errorState.value = NetworkResponse.UnknownError(null)
                }
            } catch (ex: Exception) {
                val error = when (ex) {
                    is IOException -> NetworkResponse.NetworkError(ex)
                    else -> NetworkResponse.UnknownError(null)
                }
                if (isRefreshing) {
                    _uiState.value =
                        (_uiState.value as PeopleListingUiState.NORMAL).copy(isRefreshing = false) // turn off refreshing
                }
                _errorState.value = error
            }
        }
    }

    override fun handleIntent(intent: ViewIntent) {
        when (intent) {
            PeopleListingViewIntent.LoadData -> getMyPeople(checkIfFetched = true)
            PeopleListingViewIntent.RefreshData -> getMyPeople(checkIfFetched = false, isRefreshing = true)
        }
    }
}
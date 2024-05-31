package com.example.peoplelisting.ui.listuser

import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.internal.utilities.getString
import com.example.peoplelisting.ui.screens.base.BaseViewModel
import com.example.peoplelisting.ui.screens.base.ErrorState
import com.example.peoplelisting.ui.screens.base.ViewIntent
import com.example.peoplelisting.ui.screens.listpeople.intent.PeopleListingViewIntent
import com.example.peoplelisting.ui.screens.listpeople.state.PeopleListingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListUsersViewModel @Inject constructor(private val peopleRepository: PeopleRepository) :
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
                val current = (_uiState.value as PeopleListingUiState.NORMAL).people
                _uiState.value = PeopleListingUiState.REFRESHING(current)
            } else {
                _uiState.value = PeopleListingUiState.LOADING
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
                    if(isRefreshing){
                        val current = (_uiState.value as PeopleListingUiState.REFRESHING).people
                        _uiState.value = PeopleListingUiState.NORMAL(current)
                    }
                    _errorState.value = ErrorState(R.string.get_people_error)
                }
            } catch (ex: Exception) {
                val message = when (ex) {
                    is IOException -> R.string.no_internet
                    else -> R.string.get_people_error
                }
                if(isRefreshing){
                    val current = (_uiState.value as PeopleListingUiState.REFRESHING).people
                    _uiState.value = PeopleListingUiState.NORMAL(current) // turn off refreshing
                }
                _errorState.value = ErrorState(message)
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
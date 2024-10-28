package com.example.peoplelisting.ui.createpeople.view

import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.R
import com.example.peoplelisting.data.network.adapters.NetworkResponse
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.ui.base.view.BaseViewModel
import com.example.peoplelisting.ui.createpeople.model.EntryType
import com.example.peoplelisting.ui.createpeople.model.FormEntry
import kotlinx.coroutines.launch


class CreateUserViewModel(private val peopleRepository: PeopleRepository) :
    BaseViewModel<CreatePersonUiState, CreatePersonEffect, CreatePersonUiEvent>() {


    private fun getEntries() = listOf(
        FormEntry(
            hint = R.string.first_name_hint,
            title = R.string.first_name,
            entryType = EntryType.FirstName
        ),
        FormEntry(
            hint = R.string.last_name_hint,
            title = R.string.last_name,
            entryType = EntryType.LastName
        ),
        FormEntry(
            hint = R.string.age_hint,
            title = R.string.age,
            entryType = EntryType.Age,
            keyboardType = KeyboardType.Number
        ),
        FormEntry(
            hint = R.string.profession_hint,
            title = R.string.profession,
            entryType = EntryType.Profession
        )
    )

    private fun shouldEnableCreate(): Boolean {
        val entries = _uiState.value?.entries
        return entries?.all { it.valueState.isNotEmpty() } ?: false
    }

    private fun setFirstName(firstName: String) {
        _uiState.value?.entries?.firstOrNull { it.entryType == EntryType.FirstName }?.valueState = firstName
        setState {
            copy(
                isButtonEnabled = shouldEnableCreate()
            )
        }
    }

    private fun setLastName(lastName: String) {
        _uiState.value?.entries?.firstOrNull { it.entryType == EntryType.LastName }?.valueState = lastName
        setState {
            copy(
                isButtonEnabled = shouldEnableCreate()
            )
        }
    }

    private fun setAge(age: String) {
        _uiState.value?.entries?.firstOrNull { it.entryType == EntryType.Age }?.valueState = age
        setState {
            copy(
                isButtonEnabled = shouldEnableCreate()
            )
        }
    }

    private fun setProfession(profession: String) {
        _uiState.value?.entries?.firstOrNull { it.entryType == EntryType.Profession }?.valueState =
            profession
        setState {
            copy(
                isButtonEnabled = shouldEnableCreate()
            )
        }
    }

    private fun setEntry(type: EntryType, value: String) {
        when (type) {
            EntryType.FirstName -> setFirstName(value)
            EntryType.LastName -> setLastName(value)
            EntryType.Age -> setAge(value)
            EntryType.Profession -> setProfession(value)
        }
    }


    private fun createUser() {
        val firstName = getEntryValue(EntryType.FirstName) ?: return
        val lastName = getEntryValue(EntryType.LastName) ?: return
        val age = getEntryValue(EntryType.Age)?.toIntOrNull() ?: return
        val profession = getEntryValue(EntryType.Profession) ?: return
        setState {
            copy(
                isLoading = true
            )
        }
        sendEffect(null)
        viewModelScope.launch {
            when (val response =
                peopleRepository.createUser(firstName, lastName, age, profession)) {
                is NetworkResponse.Success -> {
                    setState {
                        copy(isLoading = false)
                    }
                    sendEffect(CreatePersonEffect.Done(response.body!!.toPersonDto()))
                }

                is NetworkResponse.Failure -> {
                    setState {
                        copy(isLoading = false, isButtonEnabled = true)
                    }
                    sendEffect(
                        CreatePersonEffect.ShowError(
                            duration = 10_000L,
                            failure = response
                        )
                    )
                }
            }
        }
    }


    private fun getEntryValue(entryType: EntryType) =
        _uiState.value?.entries?.firstOrNull { it.entryType == entryType }?.valueState

    override fun handleEvent(event: CreatePersonUiEvent) {
        when (event) {
            is CreatePersonUiEvent.CreatePerson -> createUser()
            is CreatePersonUiEvent.SetEntry -> setEntry(event.entryType, event.value)
        }
    }

    override fun createInitialState() = CreatePersonUiState(
        isLoading = false,
        isButtonEnabled = false,
        entries = getEntries()
    )
}
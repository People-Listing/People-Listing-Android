package com.example.peoplelisting.ui.createpeople.view

import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.R
import com.example.peoplelisting.data.network.adapters.NetworkResponse
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.ui.base.BaseViewModel
import com.example.peoplelisting.ui.base.ViewIntent
import com.example.peoplelisting.ui.createpeople.intent.CreatePersonIntent
import com.example.peoplelisting.ui.createpeople.model.EntryType
import com.example.peoplelisting.ui.createpeople.model.FormEntry
import com.example.peoplelisting.ui.createpeople.state.CreatePersonUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException


class CreateUserViewModel(private val peopleRepository: PeopleRepository) :
    BaseViewModel<CreatePersonUiState>() {

    private val _entries = MutableLiveData(getEntries())
    val entries: LiveData<List<FormEntry>>
        get() = _entries

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

    init {
        _uiState.value = CreatePersonUiState.Normal(false)
    }


    private fun shouldEnableCreate(): Boolean {
        val entries = _entries.value
        return entries?.all { it.valueState.isNotEmpty() } ?: false
    }

    private fun setFirstName(firstName: String) {
        _entries.value?.firstOrNull { it.entryType == EntryType.FirstName }?.valueState = firstName
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            isButtonEnabled = shouldEnableCreate()
        )
    }

    private fun setLastName(lastName: String) {
        _entries.value?.firstOrNull { it.entryType == EntryType.LastName }?.valueState = lastName
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            isButtonEnabled = shouldEnableCreate()
        )
    }

    private fun setAge(age: String) {
        _entries.value?.firstOrNull { it.entryType == EntryType.Age }?.valueState = age
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            isButtonEnabled = shouldEnableCreate()
        )
    }

    private fun setProfession(profession: String) {
        _entries.value?.firstOrNull { it.entryType == EntryType.Profession }?.valueState =
            profession
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            isButtonEnabled = shouldEnableCreate()
        )
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
        _uiState.value = CreatePersonUiState.Loading
        _errorState.value = null
        viewModelScope.launch {
            when (val response =
                peopleRepository.createUser(firstName, lastName, age, profession)) {
                is NetworkResponse.Success -> {
                    _uiState.value = CreatePersonUiState.Success(response.body!!.toPersonDto())
                    _errorState.value = null
                }
                is NetworkResponse.Failure -> {
                    _uiState.value = CreatePersonUiState.Normal(true)
                    _errorState.value = response
                }
            }
        }
    }


    private fun getEntryValue(entryType: EntryType) =
        _entries.value?.firstOrNull { it.entryType == entryType }?.valueState

    override fun handleIntent(intent: ViewIntent) {
        when (intent) {
            is CreatePersonIntent.CreatePerson -> createUser()
            is CreatePersonIntent.SetEntry -> setEntry(intent.entryType, intent.value)
        }
    }
}
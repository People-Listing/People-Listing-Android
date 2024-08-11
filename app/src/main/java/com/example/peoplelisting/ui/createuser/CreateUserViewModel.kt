package com.example.peoplelisting.ui.createuser

import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.data.resource.Resource
import com.example.peoplelisting.internal.extensions.setFailure
import com.example.peoplelisting.internal.extensions.setLoading
import com.example.peoplelisting.internal.extensions.setSuccess
import com.example.peoplelisting.internal.utilities.getString
import com.example.peoplelisting.ui.screens.base.BaseViewModel
import com.example.peoplelisting.ui.screens.base.ViewIntent
import com.example.peoplelisting.ui.screens.createpeople.intent.CreatePersonIntent
import com.example.peoplelisting.ui.screens.createpeople.model.EntryType
import com.example.peoplelisting.ui.screens.createpeople.model.FormEntry
import com.example.peoplelisting.ui.screens.createpeople.state.CreatePersonUiState
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
        _uiState.value = CreatePersonUiState.Normal( false)
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
        _entries.value?.firstOrNull { it.entryType == EntryType.Profession }?.valueState = profession
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
        _uiState.value = CreatePersonUiState.Loading
        val firstName =
            _entries.value?.firstOrNull { it.entryType == EntryType.FirstName }?.valueState ?: ""
        val lastName =  _entries.value?.firstOrNull { it.entryType == EntryType.LastName }?.valueState ?: ""
        val age: Int =
            _entries.value?.firstOrNull { it.entryType == EntryType.Age }?.valueState?.toIntOrNull() ?: 0
        val profession =
            _entries.value?.firstOrNull { it.entryType == EntryType.Profession }?.valueState ?: ""
        viewModelScope.launch {
            try {
                val response = peopleRepository.createUser(firstName, lastName, age, profession)
                if (response.isSuccessful) {
                    _uiState.value = CreatePersonUiState.Success(response.body()!!.toPersonDto())
                } else {
                    _uiState.value = CreatePersonUiState.Normal( true)
                }
            } catch (ex: Exception) {
                Timber.tag("api error").i("exception $ex")
                val message = when (ex) {
                    is IOException -> getString(R.string.no_internet)
                    else -> null
                }
                _uiState.value = CreatePersonUiState.Normal( true)
            }
        }
    }

    override fun handleIntent(intent: ViewIntent) {
        when (intent) {
            is CreatePersonIntent.CreatePerson -> createUser()
            is CreatePersonIntent.SetEntry -> setEntry(intent.entryType, intent.value)
        }
    }
}
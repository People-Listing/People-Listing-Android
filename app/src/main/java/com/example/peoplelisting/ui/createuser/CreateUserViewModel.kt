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
    private val _createUserResponse = MutableLiveData<Resource<PersonDto>>()
    val createUserResponse: LiveData<Resource<PersonDto>>
        get() = _createUserResponse

    private val _firstName = MutableLiveData<String>()

    private val _lastName = MutableLiveData<String>()

    private val _age = MutableLiveData<String>()

    private val _profession = MutableLiveData<String>()

    private val _enableCreate = MediatorLiveData<Boolean>()
    val enableCreate: LiveData<Boolean>
        get() = _enableCreate

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
        _uiState.value = CreatePersonUiState.Normal(getEntries(), false)
    }


    private fun shouldEnableCreate(): Boolean {
        val entries = (_uiState.value as CreatePersonUiState.Normal).entries
        return entries.all { it.valueState.isNotEmpty() }
    }

    private fun setFirstName(firstName: String) {
        val entries = (_uiState.value as CreatePersonUiState.Normal).entries
        entries.firstOrNull { it.entryType == EntryType.FirstName }?.valueState = firstName
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            entries = entries,
            isButtonEnabled = shouldEnableCreate()
        )
    }

    private fun setLastName(lastName: String) {
        val entries = (_uiState.value as CreatePersonUiState.Normal).entries
        entries.firstOrNull { it.entryType == EntryType.LastName }?.valueState = lastName
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            entries = entries,
            isButtonEnabled = shouldEnableCreate()
        )
    }

    private fun setAge(age: String) {
        val entries = (_uiState.value as CreatePersonUiState.Normal).entries
        entries.firstOrNull { it.entryType == EntryType.Age }?.valueState = age
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            entries = entries,
            isButtonEnabled = shouldEnableCreate()
        )
    }

    private fun setProfession(profession: String) {
        val entries = (_uiState.value as CreatePersonUiState.Normal).entries
        entries.firstOrNull { it.entryType == EntryType.Profession }?.valueState = profession
        _uiState.value = (_uiState.value as CreatePersonUiState.Normal).copy(
            entries = entries,
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
        val entries = (_uiState.value as CreatePersonUiState.Normal).entries
        _uiState.value = CreatePersonUiState.Loading(entries)
        val firstName =
            entries.firstOrNull { it.entryType == EntryType.FirstName }?.valueState ?: ""
        val lastName = entries.firstOrNull { it.entryType == EntryType.LastName }?.valueState ?: ""
        val age: Int =
            entries.firstOrNull { it.entryType == EntryType.Age }?.valueState?.toIntOrNull() ?: 0
        val profession =
            entries.firstOrNull { it.entryType == EntryType.Profession }?.valueState ?: ""
        viewModelScope.launch {
            try {
                val response = peopleRepository.createUser(firstName, lastName, age, profession)
                if (response.isSuccessful) {
                    _uiState.value = CreatePersonUiState.Success(response.body()!!.toPersonDto())
                } else {
                    val previous = (_uiState.value as CreatePersonUiState.Loading).entries
                    _uiState.value = CreatePersonUiState.Normal(previous, true)
                }
            } catch (ex: Exception) {
                Timber.tag("api error").i("exception $ex")
                val message = when (ex) {
                    is IOException -> getString(R.string.no_internet)
                    else -> null
                }
                val previous = (_uiState.value as CreatePersonUiState.Loading).entries
                _uiState.value = CreatePersonUiState.Normal(previous, true)
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
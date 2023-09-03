package com.example.peoplelisting.ui.createuser

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
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class CreateUserViewModel(private val peopleRepository: PeopleRepository) : ViewModel() {
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

    init {
        _enableCreate.addSource(_firstName){shouldEnableCreate()}
        _enableCreate.addSource(_lastName){shouldEnableCreate()}
        _enableCreate.addSource(_age){shouldEnableCreate()}
        _enableCreate.addSource(_profession){shouldEnableCreate()}
    }


    private fun shouldEnableCreate() {
       _enableCreate.value =  !_firstName.value.isNullOrEmpty()
                && !_lastName.value.isNullOrEmpty()
                && !_age.value.isNullOrEmpty()
                && !_profession.value.isNullOrEmpty()
    }

    fun setFirstName(firstName: String) {
        _firstName.value = firstName
    }

    fun setLastName(lastName: String) {
        _lastName.value = lastName
    }

    fun setAge(age: String) {
        _age.value = age
    }

    fun setProfession(profession: String) {
        _profession.value = profession
    }


    fun createUser() {
        _createUserResponse.setLoading()
        val firstName = _firstName.value ?: ""
        val lastName = _lastName.value ?: ""
        val age: Int = _age.value?.toIntOrNull() ?: 0
        val profession = _profession.value ?: ""
        viewModelScope.launch {
            try {
                val response = peopleRepository.createUser(firstName, lastName, age, profession)
                if (response.isSuccessful) {
                    _createUserResponse.setSuccess(response.body()!!.toPersonDto())
                } else {
                    Timber.tag("api error").i("${response.errorBody()?.string()} ${response.body()}")
                    _createUserResponse.setFailure()
                }
            } catch (ex: Exception) {
                Timber.tag("api error").i("exception $ex")
                val message = when (ex) {
                    is IOException -> getString(R.string.no_internet)
                    else -> null
                }
                _createUserResponse.setFailure(message = message)
            }
        }
    }
}
package com.example.peoplelisting.ui.listuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.data.resource.Resource
import com.example.peoplelisting.internal.extensions.setFailure
import com.example.peoplelisting.internal.extensions.setLoading
import com.example.peoplelisting.internal.extensions.setSuccess
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class ListUsersViewModel(private val peopleRepository: PeopleRepository) : ViewModel() {

    private val _usersResponse = MutableLiveData<Resource<List<PersonDto>>>()
    val usersResponse: LiveData<Resource<List<PersonDto>>>
        get() = _usersResponse


    fun getMyPeople() {
        viewModelScope.launch {
            _usersResponse.setLoading()
            try {
                val res = peopleRepository.getUsers()
                if (res.isSuccessful) {
                    val persons = res.body()
                    val personsDto = mutableListOf<PersonDto>()
                    persons?.forEach {
                        personsDto.add(it.toPersonDto())
                    }
                    _usersResponse.setSuccess(personsDto)
                } else {
                    Timber.tag("api error").i("${res.errorBody()} ${res.body()}")
                    _usersResponse.setFailure()
                }
            } catch (ex: Exception) {
                Timber.tag("api error").i("exception $ex")
                _usersResponse.setFailure()
            }
        }
    }
}
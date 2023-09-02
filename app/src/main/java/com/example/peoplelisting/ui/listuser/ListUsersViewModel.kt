package com.example.peoplelisting.ui.listuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.peoplelisting.R
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.data.repository.PeopleRepository
import com.example.peoplelisting.data.resource.Resource
import com.example.peoplelisting.internal.SingleLiveEvent
import com.example.peoplelisting.internal.extensions.setFailure
import com.example.peoplelisting.internal.extensions.setLoading
import com.example.peoplelisting.internal.extensions.setSuccess
import com.example.peoplelisting.internal.utilities.getString
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class ListUsersViewModel(private val peopleRepository: PeopleRepository) : ViewModel() {

    private val _usersResponse = MutableLiveData<Resource<List<PersonDto>>>()
    val usersResponse: LiveData<Resource<List<PersonDto>>>
        get() = _usersResponse

    private var isFetched: Boolean = false


    fun getMyPeople(checkIfFetched: Boolean = false) {
        if(checkIfFetched && isFetched) return
        viewModelScope.launch {
            _usersResponse.setLoading()
            try {
                val res = peopleRepository.getUsers()
                if (res.isSuccessful) {
                    isFetched = true
                    val persons = res.body()
                    val personsDto = mutableListOf<PersonDto>()
                    persons?.forEach {
                        personsDto.add(it.toPersonDto())
                    }
                    _usersResponse.setSuccess(personsDto)
                } else {
                    Timber.tag("api error").i("${res.errorBody()?.string()} ${res.body()}")
                    _usersResponse.setFailure()
                }
            } catch (ex: Exception) {
                Timber.tag("api error").i("exception $ex")
                val message = when (ex) {
                    is IOException -> getString(R.string.no_internet)
                    else -> null
                }
                _usersResponse.setFailure(message = message)
            }
        }
    }
}
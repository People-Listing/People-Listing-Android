package com.example.peoplelisting.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.peoplelisting.data.model.dto.PersonDto
import com.example.peoplelisting.internal.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    private val _screenTitle =  SingleLiveEvent<String>()
    val screenTitle: LiveData<String>
        get() = _screenTitle
    fun setScreenTitle(title: String) {
        _screenTitle.postValue(title)
    }
}
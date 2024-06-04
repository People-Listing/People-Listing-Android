package com.example.peoplelisting.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.peoplelisting.internal.SingleLiveEvent



class MainViewModel: ViewModel() {
    private val _screenTitle =  SingleLiveEvent<String>()
    val screenTitle: LiveData<String>
        get() = _screenTitle
    fun setScreenTitle(title: String) {
        _screenTitle.postValue(title)
    }
}
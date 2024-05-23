package com.example.peoplelisting.ui.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<VI : ViewIntent, S : UiState> : ViewModel() {
    protected val _uiState = MutableLiveData<S>()
    val uiState: LiveData<S>
        get() = _uiState

    protected val _errorState = MutableLiveData<ErrorState>()
    val errorState: LiveData<ErrorState>
        get() = _errorState

    abstract fun handleIntent(intent: ViewIntent)
}
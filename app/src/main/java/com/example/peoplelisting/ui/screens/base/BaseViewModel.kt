package com.example.peoplelisting.ui.screens.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.peoplelisting.data.network.NetworkResponse

/**
 * Base ViewModel that handles intents and set UI states and Error States
 */
abstract class BaseViewModel<VI : ViewIntent, S : UiState> : ViewModel() {
    protected val _uiState = MutableLiveData<S>()
    val uiState: LiveData<S>
        get() = _uiState

    protected val _errorState = MutableLiveData<NetworkResponse.Failure<*>?>()
    val errorState: LiveData<NetworkResponse.Failure<*>?>
        get() = _errorState

    abstract fun handleIntent(intent: ViewIntent)

    fun setError(errorState: NetworkResponse.Failure<*>?) {
        _errorState.postValue(errorState)
    }
}
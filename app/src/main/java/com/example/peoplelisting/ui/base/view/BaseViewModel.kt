package com.example.peoplelisting.ui.base.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel that handles intents and set UI states and Error States
 */
abstract class BaseViewModel<State : UiState, Effect : UiEffect, Event : UiEvent> : ViewModel() {
    protected val _uiState = MutableLiveData(this.createInitialState())
    val uiState: LiveData<State>
        get() = _uiState

    protected val _effect = MutableSharedFlow<Effect?>()
    val effect: SharedFlow<Effect?>
        get() = _effect

    internal abstract fun handleEvent(event: Event)
    protected fun sendEffect(effect: Effect?) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected abstract fun createInitialState(): State

    protected fun setState(block: State.() -> State) {
        _uiState.postValue(_uiState.value?.block())
    }
}
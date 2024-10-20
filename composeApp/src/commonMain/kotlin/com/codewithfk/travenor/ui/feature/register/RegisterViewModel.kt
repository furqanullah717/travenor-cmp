package com.codewithfk.travenor.ui.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.travenor.data.NetworkService
import com.codewithfk.travenor.data.ResultWrapper
import com.codewithfk.travenor.data.models.request.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(val networkService: NetworkService) : ViewModel() {
    private val _uiState = MutableStateFlow<RegisterState>(RegisterState.Nothing)
    val uiState = _uiState.asStateFlow()

    fun register(name: String, email: String, password: String) {
        _uiState.value = RegisterState.Loading
        viewModelScope.launch {
            val response = networkService.register(
                RegisterRequest(
                    email = email,
                    password = password,
                    name = name
                )
            )
            when (response) {
                is ResultWrapper.Success -> {
                    _uiState.value = RegisterState.Success
                }

                is ResultWrapper.Error -> {
                    _uiState.value = RegisterState.Error(response.e.message ?: "An error occurred")
                }
            }

        }

    }

    fun retry() {
        _uiState.value = RegisterState.Nothing
    }
}

sealed class RegisterState {
    object Nothing : RegisterState()
    object Loading : RegisterState()
    object Success : RegisterState()
    data class Error(val message: String) : RegisterState()
}
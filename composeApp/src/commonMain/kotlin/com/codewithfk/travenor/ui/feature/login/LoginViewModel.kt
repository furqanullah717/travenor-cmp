package com.codewithfk.travenor.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithfk.travenor.cache.TravenorSession
import com.codewithfk.travenor.data.NetworkService
import com.codewithfk.travenor.data.ResultWrapper
import com.codewithfk.travenor.data.models.request.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(val networkService: NetworkService, private val session: TravenorSession) :
    ViewModel() {
    val _uiState = MutableStateFlow<LoginState>(LoginState.Nothing)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.value = LoginState.Loading
        viewModelScope.launch {
            val result = networkService.login(LoginRequest(email, password))
            when (result) {
                is ResultWrapper.Success -> {
                    _uiState.value = LoginState.Success
                    session.saveToken(result.value.token)
                }

                is ResultWrapper.Error -> {
                    _uiState.value = LoginState.Error(result.e.message ?: "An error occurred")
                }
            }
        }
    }

    fun retry() {
        _uiState.value = LoginState.Nothing
    }

}

sealed class LoginState {
    object Nothing : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}
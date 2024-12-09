package com.example.proyectofinalcliente.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalcliente.data.models.AuthResponse
import com.example.proyectofinalcliente.data.models.LoginRequest
import com.example.proyectofinalcliente.data.models.User
import com.example.proyectofinalcliente.data.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun login(email: String, password: String, onSuccess: (AuthResponse) -> Unit, onError: (String) -> Unit) {
        val loginRequest = LoginRequest(email = email, password = password)
        viewModelScope.launch {
            try {
                val response = authRepository.login(loginRequest)
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError("Error en login: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.message}")
            }
        }
    }

    fun register(user: User, onSuccess: (User) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authRepository.register(user)
                if (response.isSuccessful) {
                    response.body()?.let { onSuccess(it) }
                } else {
                    onError("Error en registro: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error: ${e.message}")
            }
        }
    }
}

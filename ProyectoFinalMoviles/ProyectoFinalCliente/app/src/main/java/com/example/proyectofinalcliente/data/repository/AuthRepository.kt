package com.example.proyectofinalcliente.data.repository

import com.example.proyectofinalcliente.data.models.AuthResponse
import com.example.proyectofinalcliente.data.models.LoginRequest
import com.example.proyectofinalcliente.data.models.User
import com.example.proyectofinalcliente.data.Api.RetrofitInstance
import retrofit2.Response

class AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Response<AuthResponse> {
        return RetrofitInstance.api.login(loginRequest).execute()
    }

    suspend fun register(user: User): Response<User> {
        return RetrofitInstance.api.register(user).execute()
    }
}

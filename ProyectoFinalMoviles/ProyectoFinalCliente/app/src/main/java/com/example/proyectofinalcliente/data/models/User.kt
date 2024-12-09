package com.example.proyectofinalcliente.data.models

data class User(
    val id: Int? = null, // Será nulo durante el registro
    val name: String,
    val email: String,
    val password: String,
    val role: Int
)

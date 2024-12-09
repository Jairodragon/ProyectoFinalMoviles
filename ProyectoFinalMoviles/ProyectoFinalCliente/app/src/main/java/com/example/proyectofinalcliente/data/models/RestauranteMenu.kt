package com.example.proyectofinalcliente.data.models
data class RestauranteMenu(
    val id: Int,
    val name: String,
    val address: String,
    val latitude: String,
    val longitude: String,
    val logo: String,
    val products: List<Product>
)

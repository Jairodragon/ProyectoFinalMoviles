package com.example.proyectofinalcliente.data.models

data class Pedido(
    val id: Int,
    val fecha: String,
    val estado: String,
    val total: Double
)

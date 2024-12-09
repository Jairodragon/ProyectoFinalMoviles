package com.example.proyectofinalcliente.data.models

import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("restaurant_id") val restaurantId: Int,
    val total: Double,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val details: List<OrderDetail>
)

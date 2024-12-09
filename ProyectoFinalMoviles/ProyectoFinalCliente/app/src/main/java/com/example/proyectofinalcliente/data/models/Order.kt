package com.example.proyectofinalcliente.data.models

import com.google.gson.annotations.SerializedName

data class Order(
    val id: Int,
    val userId: Int,
    val restaurantId: Int,
    val total: Double,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val driverId: Int?,
    val status: String,
    val createdAt: String,
    val deliveryProof: String,
    @SerializedName("order_details") val orderDetails: List<OrderDetail>
)

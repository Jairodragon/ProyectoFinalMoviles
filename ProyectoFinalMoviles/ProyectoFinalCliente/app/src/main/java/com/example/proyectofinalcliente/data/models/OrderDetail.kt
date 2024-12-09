package com.example.proyectofinalcliente.data.models
import com.google.gson.annotations.SerializedName

data class OrderDetail(
    @SerializedName("product_id") val productId: Int,
    @SerializedName("qty") val qty: Int,
    val price: Double,
    val product: Product // Aquí agregamos el producto completo
)

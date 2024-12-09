package com.example.proyectofinalcliente.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarritoItem(
    val product: Product,
    val productId: String = product.id.toString(),
    val productName: String = product.name,
    val productPrice: Double = product.price.toDouble(),
    var qty: Int = 1
) : Parcelable

package com.example.proyectofinalcliente.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalcliente.R
import com.example.proyectofinalcliente.data.models.CarritoItem
import com.example.proyectofinalcliente.data.models.OrderDetail
import com.example.proyectofinalcliente.data.models.OrderRequest
import com.example.proyectofinalcliente.data.Api.RetrofitInstance
import com.example.proyectofinalcliente.databinding.ActivityCartBinding
import com.example.proyectofinalcliente.Adapters.CartAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val cartItems: List<CarritoItem> by lazy {
        intent.getParcelableArrayListExtra<CarritoItem>("cart_items") ?: emptyList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        // Mostrar el total del carrito
        calculateAndDisplayTotal()

        binding.btnCheckout.setOnClickListener {
            val total = calculateTotal()
            finalizeOrder(cartItems, total)
        }
    }

    private fun setupRecyclerView() {
        val cartAdapter = CartAdapter()
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
        cartAdapter.submitList(cartItems)
    }

    private fun calculateAndDisplayTotal() {
        val total = calculateTotal()
        binding.tvTotal.text = getString(R.string.total_placeholder, total)
    }

    private fun calculateTotal(): Double {
        return cartItems.sumOf { it.product.price.toDouble() * it.qty }
    }

    private fun finalizeOrder(cartItems: List<CarritoItem>, total: Double) {
        val orderDetails = cartItems.map {
            OrderDetail(
                productId = it.product.id,
                qty = it.qty,
                price = it.productPrice,
                product = it.product
            )
        }

        val orderRequest = OrderRequest(
            restaurantId = cartItems.firstOrNull()?.product?.restaurant_id ?: 0, // Validar que exista al menos un producto
            total = total,
            address = "Av. Banzer 100",
            latitude = -17.769170204594246,
            longitude = -63.182973113152056,
            details = orderDetails
        )

        // Llamada a la API para crear el pedido
        RetrofitInstance.api.createOrder(orderRequest).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@CartActivity,
                        "Pedido creado exitosamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(
                        this@CartActivity,
                        "Error al crear el pedido: $errorBody",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CartActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

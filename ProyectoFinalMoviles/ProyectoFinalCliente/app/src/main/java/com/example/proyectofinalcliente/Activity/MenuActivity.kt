package com.example.proyectofinalcliente.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalcliente.data.models.CarritoItem
import com.example.proyectofinalcliente.data.models.Product
import com.example.proyectofinalcliente.data.models.RestauranteMenu
import com.example.proyectofinalcliente.data.Api.RetrofitInstance
import com.example.proyectofinalcliente.databinding.ActivityMenuBinding
import com.example.proyectofinalcliente.Adapters.ProductAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var productAdapter: ProductAdapter
    private val cartItems = mutableListOf<CarritoItem>()
    private var restaurantId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurantId = intent.getIntExtra("restaurant_id", -1)
        val restaurantName = intent.getStringExtra("restaurant_name") ?: "Restaurante"

        binding.tvRestaurantName.text = restaurantName
        binding.btnCart.setOnClickListener {
            openCart()
        }

        productAdapter = ProductAdapter { product ->
            addToCart(product)
        }
        binding.recyclerViewMenu.adapter = productAdapter
        binding.recyclerViewMenu.layoutManager = LinearLayoutManager(this)

        loadMenu()
    }

    private fun loadMenu() {
        RetrofitInstance.api.getRestaurantMenu(restaurantId).enqueue(object : Callback<RestauranteMenu> {
            override fun onResponse(
                call: Call<RestauranteMenu>,
                response: Response<RestauranteMenu>
            ) {
                if (response.isSuccessful) {
                    val restaurantWithMenu = response.body()
                    if (restaurantWithMenu != null) {
                        binding.tvRestaurantName.text = restaurantWithMenu.name
                        productAdapter.submitList(restaurantWithMenu.products)
                    } else {
                        Toast.makeText(this@MenuActivity, "No se encontraron productos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MenuActivity, "Error al cargar el menú", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RestauranteMenu>, t: Throwable) {
                Toast.makeText(this@MenuActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addToCart(product: Product) {
        val existingItem = cartItems.find { it.product.id == product.id }
        if (existingItem != null) {
            existingItem.qty++
        } else {
            cartItems.add(CarritoItem(product = product, qty = 1))
        }
        Toast.makeText(this, "${product.name} agregado al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun openCart() {
        val intent = Intent(this, CartActivity::class.java)
        intent.putParcelableArrayListExtra("cart_items", ArrayList(cartItems))
        startActivity(intent)
    }
}

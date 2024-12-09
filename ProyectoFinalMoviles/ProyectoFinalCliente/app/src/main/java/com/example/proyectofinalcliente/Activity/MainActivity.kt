package com.example.proyectofinalcliente.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalcliente.data.models.Restaurant
import com.example.proyectofinalcliente.data.Api.RetrofitInstance
import com.example.proyectofinalcliente.databinding.ActivityMainBinding
import com.example.proyectofinalcliente.Adapters.RestaurantAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var restaurantAdapter: RestaurantAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewRestaurants.layoutManager = LinearLayoutManager(this)
        restaurantAdapter = RestaurantAdapter { restaurant ->
            val intent = Intent(this, MenuActivity::class.java).apply {
                putExtra("restaurant_id", restaurant.id)
                putExtra("restaurant_name", restaurant.name)
            }
            startActivity(intent)
        }

        binding.recyclerViewRestaurants.adapter = restaurantAdapter

        binding.btnPedidos.setOnClickListener {
            val intent = Intent(this, OrdersActivity::class.java)
            startActivity(intent)
        }

        loadRestaurants()
    }

    private fun loadRestaurants() {
        // Realiza una solicitud a la API para obtener los restaurantes
        RetrofitInstance.api.getRestaurants().enqueue(object : Callback<List<Restaurant>> {
            override fun onResponse(call: Call<List<Restaurant>>, response: Response<List<Restaurant>>) {
                if (response.isSuccessful) {
                    val restaurants = response.body()
                    if (restaurants != null) {
                        restaurantAdapter.submitList(restaurants)
                    } else {
                        Toast.makeText(this@MainActivity, "No se encontraron restaurantes", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Toast.makeText(this@MainActivity, "Error al cargar restaurantes: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Restaurant>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

package com.example.proyectofinalcliente.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofinalcliente.data.models.Order
import com.example.proyectofinalcliente.data.Api.RetrofitInstance
import com.example.proyectofinalcliente.databinding.ActivityOrdersBinding
import com.example.proyectofinalcliente.Adapters.OrdersAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrdersBinding
    private lateinit var ordersAdapter: OrdersAdapter
    private val ordersList = mutableListOf<Order>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        fetchOrders()
    }

    private fun setupRecyclerView() {
        // Inicializar el adaptador
        ordersAdapter = OrdersAdapter(ordersList)

        // Configurar RecyclerView
        binding.recyclerViewOrders.apply {
            layoutManager = LinearLayoutManager(this@OrdersActivity)
            adapter = ordersAdapter
        }

        ordersAdapter.setOnItemClickListener { order ->
            val intent = Intent(this, OrderDetailActivity::class.java)
            intent.putExtra("orderId", order.id)
            intent.putExtra("restaurantLatitude", order.latitude.toDouble())
            intent.putExtra("restaurantLongitude", order.longitude.toDouble())
            intent.putExtra("orderDetails", ArrayList(order.orderDetails)) // Enviar detalles como lista serializable
            intent.putExtra("status", order.status)
            startActivity(intent)
        }
    }

    private fun fetchOrders() {
        RetrofitInstance.api.getOrders().enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    handleSuccessResponse(response.body())
                } else {
                    handleErrorResponse(response.code())
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                handleFailure(t)
            }
        })
    }

    private fun handleSuccessResponse(orders: List<Order>?) {
        if (orders.isNullOrEmpty()) {
            Toast.makeText(this@OrdersActivity, "No se encontraron pedidos.", Toast.LENGTH_SHORT).show()
        } else {
            ordersList.clear()
            ordersList.addAll(orders)
            ordersAdapter.notifyDataSetChanged()
        }
    }

    private fun handleErrorResponse(code: Int) {
        Toast.makeText(this@OrdersActivity, "Error al cargar pedidos: Código $code", Toast.LENGTH_LONG).show()
    }

    private fun handleFailure(t: Throwable) {
        Toast.makeText(this@OrdersActivity, "Error de red: ${t.message}", Toast.LENGTH_LONG).show()
    }
}

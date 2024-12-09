package com.example.proyectofinalcliente.Activity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalcliente.R
import com.example.proyectofinalcliente.data.models.OrderDetail
import com.example.proyectofinalcliente.Adapters.OrderDetailsAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class OrderDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private var restaurantLatitude: Double = 0.0
    private var restaurantLongitude: Double = 0.0
    private var customerLatitude: Double = 0.0
    private var customerLongitude: Double = 0.0
    private lateinit var orderStatus: String
    private lateinit var orderDetails: List<OrderDetail>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        restaurantLatitude = intent.getDoubleExtra("restaurantLatitude", 0.0)
        restaurantLongitude = intent.getDoubleExtra("restaurantLongitude", 0.0)
        customerLatitude = intent.getDoubleExtra("customerLatitude", 0.0)
        customerLongitude = intent.getDoubleExtra("customerLongitude", 0.0)
        orderStatus = intent.getStringExtra("status") ?: "Sin estado"
        orderDetails = intent.getParcelableArrayListExtra("orderDetails") ?: emptyList()

        findViewById<TextView>(R.id.textViewOrderDetailStatus).text =
            getString(R.string.order_status, orderStatus)

        setupRecyclerView()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOrderDetails)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OrderDetailsAdapter(orderDetails)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        if (restaurantLatitude == 0.0 || restaurantLongitude == 0.0 || customerLatitude == 0.0 || customerLongitude == 0.0) {
            Toast.makeText(this, "Coordenadas no disponibles", Toast.LENGTH_SHORT).show()
            return
        }

        val restaurantLocation = LatLng(restaurantLatitude, restaurantLongitude)
        val customerLocation = LatLng(customerLatitude, customerLongitude)

        googleMap.addMarker(
            MarkerOptions()
                .position(restaurantLocation)
                .title("Restaurante")
        )
        googleMap.addMarker(
            MarkerOptions()
                .position(customerLocation)
                .title("Cliente")
        )

        val bounds = LatLngBounds.builder()
            .include(restaurantLocation)
            .include(customerLocation)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }
}

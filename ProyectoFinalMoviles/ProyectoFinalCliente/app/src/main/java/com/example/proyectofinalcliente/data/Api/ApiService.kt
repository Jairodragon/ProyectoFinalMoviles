package com.example.proyectofinalcliente.data.Api

import com.example.proyectofinalcliente.data.models.AuthResponse
import com.example.proyectofinalcliente.data.models.LoginRequest
import com.example.proyectofinalcliente.data.models.Order
import com.example.proyectofinalcliente.data.models.OrderRequest
import com.example.proyectofinalcliente.data.models.Restaurant
import com.example.proyectofinalcliente.data.models.RestauranteMenu
import com.example.proyectofinalcliente.data.models.User
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @POST("users/login")
    fun login(@Body loginRequest: LoginRequest): Call<AuthResponse>

    @POST("users")
    fun register(@Body user: User): Call<User>

    @GET("restaurants")
    fun getRestaurants(): Call<List<Restaurant>>

    @GET("orders")
    fun getOrders(): Call<List<Order>>

    @GET("restaurants/{restaurant_id}")
    fun getRestaurantMenu(
        @Path("restaurant_id") restaurantId: Int
    ): Call<RestauranteMenu>

    @POST("orders")
    fun createOrder(@Body orderRequest: OrderRequest): Call<Void>

    @GET("orders/{order_id}")
    fun getOrderDetails(
        @Path("order_id") orderId: Int
    ): Call<Order>
}

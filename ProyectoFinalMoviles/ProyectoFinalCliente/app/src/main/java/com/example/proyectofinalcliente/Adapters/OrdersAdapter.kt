package com.example.proyectofinalcliente.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinalcliente.R
import com.example.proyectofinalcliente.data.models.Order

class OrdersAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    private var onItemClickListener: ((Order) -> Unit)? = null

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.textViewOrderDate)
        val totalTextView: TextView = view.findViewById(R.id.textViewOrderTotal)
        val statusTextView: TextView = view.findViewById(R.id.textViewOrderStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.dateTextView.text = "Fecha: ${order.createdAt}"
        holder.totalTextView.text = "Total: $${order.total}"
        holder.statusTextView.text = "Estado: ${getStatusDescription(order.status)}"

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(order)
        }
    }

    override fun getItemCount() = orders.size

    private fun getStatusDescription(status: String): String {
        return when (status) {
            "1" -> "En Proceso"
            "2" -> "Aceptado por repartidor"
            "3" -> "En camino"
            "4" -> "Entregado"
            else -> "Desconocido"
        }
    }

    fun setOnItemClickListener(listener: (Order) -> Unit) {
        onItemClickListener = listener
    }
}

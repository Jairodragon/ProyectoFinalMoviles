package com.example.proyectofinalcliente.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalcliente.data.models.OrderDetail
import com.example.proyectofinalcliente.databinding.ItemOrderDetailBinding

class OrderDetailsAdapter(private val orderDetails: List<OrderDetail>) :
    RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(orderDetail: OrderDetail) {
            binding.textViewProductName.text = orderDetail.product.name
            binding.textViewProductQuantity.text = "Cantidad: ${orderDetail.qty}"
            binding.textViewProductPrice.text = "Precio: $${orderDetail.price}"
            Glide.with(binding.imageViewProduct.context)
                .load(orderDetail.product.image)
                .into(binding.imageViewProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderDetails[position])
    }

    override fun getItemCount() = orderDetails.size
}

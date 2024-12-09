package com.example.proyectofinalcliente.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyectofinalcliente.data.models.CarritoItem
import com.example.proyectofinalcliente.databinding.ItemCartBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartItems: List<CarritoItem> = emptyList()

    fun submitList(items: List<CarritoItem>) {
        cartItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cartItem: CarritoItem) {
            binding.tvCartProductName.text = cartItem.product.name
            binding.tvCartProductPrice.text = "$${cartItem.product.price} x ${cartItem.qty}"
            Glide.with(binding.ivCartProductImage.context)
                .load(cartItem.product.image)
                .into(binding.ivCartProductImage)
        }
    }
}

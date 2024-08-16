package com.example.thirdsemproject.repository.cart

import com.example.thirdsemproject.model.CartModel
import com.example.thirdsemproject.model.CategoryModel

interface CartRepo {

    fun addCart(cartModel: CartModel, callback: (Boolean, String?) -> Unit)

    fun deleteCart(cartID:String,callback: (Boolean, String?) -> Unit)

    fun getCart(callback: (List<CartModel>?,Boolean, String?) -> Unit)
}
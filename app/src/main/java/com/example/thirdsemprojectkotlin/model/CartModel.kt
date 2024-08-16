package com.example.thirdsemproject.model

data class CartModel (
    var cartId : String= "",
    var productId : String= "",
    var productPrice : Int= 0,
    var productImage : String= "",
    var productName : String= "",
    var quantity : Int=1,
    var userId :String =""
){
}
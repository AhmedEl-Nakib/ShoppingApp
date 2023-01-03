package com.example.shoppingapp.RemoteDB


data class ProductAPI (
    val id:Int,
    val title: String,
                       val price: Double,
                       val description: String,
                       val category: String,
                       val image:String,
                       val rate:Double,
                       val quantity: Int=1){
}
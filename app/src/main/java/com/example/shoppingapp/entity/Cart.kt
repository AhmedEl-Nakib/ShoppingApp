package com.example.shoppingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cart (var id: Int = 0,
                 var title: String = "",
                 var price: Double = 0.0,
                 var description: String = "",
                 var category: String = "",
                 var image:String = "",
                 var quantity: Int=1){
    @PrimaryKey(autoGenerate = true)
    var cartItemId :Int = 0
}
package com.example.shoppingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Wishlist (var id: Int = 0,
                     val title: String,
                     val price: Double,
                     val description: String,
                     val category: String,
                     val image:String){
    @PrimaryKey(autoGenerate = true)
    var wishlistItemId :Int = 0
}
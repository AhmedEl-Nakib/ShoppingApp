package com.example.shoppingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (val title: String,
                    val price: Double,
                    val description: String,
                    val category: String,
                    val image:String,
                    val rate:Double,
                    val quantity: Int=1){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
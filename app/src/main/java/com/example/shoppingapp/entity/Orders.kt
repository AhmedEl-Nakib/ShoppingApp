package com.example.shoppingapp.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Orders(
    var totalCount: Int=0,
    var totalAmount: Int=0,
    var cart: List<Cart>,
    var userID: String,
    var insertedDate: Long,
    var status: String = "Processing"
    ) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}
package com.example.shoppingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address (
    val fullName : String,
    val country: String,
    val city: String,
    val area: Int,
    val streetNumber: Int,
    val house: Int,
    val postalCode: Int,
    val phoneNumber: Int,
    val homeDeliveryAddress: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
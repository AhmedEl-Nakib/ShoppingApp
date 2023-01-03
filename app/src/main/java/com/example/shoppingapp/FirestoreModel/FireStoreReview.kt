package com.example.shoppingapp.FirestoreModel

import androidx.room.PrimaryKey

class FireStoreReview(
    val productID:String,
    val review:String,
    val userName:String) {
}
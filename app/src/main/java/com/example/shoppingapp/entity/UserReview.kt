package com.example.shoppingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_review")
data class UserReview (
    val productID:String,
    val review:String,
    val userName:String){
    @PrimaryKey(autoGenerate = true) var userReviewID: Long = 0
}
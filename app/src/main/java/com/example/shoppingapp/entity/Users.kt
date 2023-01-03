package com.example.shoppingapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Users(val firstName : String,
            val lastName: String,
            val email: String,
            val password: String) {
    @PrimaryKey(autoGenerate = true) var userId: Long = 0

}
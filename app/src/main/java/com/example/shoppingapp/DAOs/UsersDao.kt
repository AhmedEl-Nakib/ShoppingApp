package com.example.shoppingapp.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppingapp.entity.Users

@Dao
interface UsersDao {


    @Insert
    fun addUser(user: Users)

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<Users>

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    fun getUser(email:String , password:String): List<Users>

//    @Query("UPDATE Users SET selected = true WHERE userId= :ID")
//    fun updateSelectedUsers(ID: Long)
//
//    @Query("UPDATE Users SET selected= false")
//    fun updateUnselectedUsers()
}
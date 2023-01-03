package com.example.practice2.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppingapp.entity.Users

@Dao
interface UserDao {

    @Insert
    fun addUser(user: Users)

    @Query("SELECT * FROM Users")
    fun getAllUsers(): List<Users>

    @Query("DELETE FROM Users")
    fun deleteAllUsers()

//    @Query("DELETE FROM Users WHERE id= :ID")
//    fun deleteUserByID(ID: String)


}
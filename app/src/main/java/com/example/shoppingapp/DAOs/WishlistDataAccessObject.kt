package com.example.shoppingapp.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.Product
import com.example.shoppingapp.entity.Wishlist

@Dao
interface WishlistDataAccessObject {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addProductToWishlist(product: Wishlist)

    @Query("SELECT * FROM Wishlist")
    fun getAllWishlist(): List<Wishlist>

    @Query("SELECT * FROM Wishlist WHERE id=:id")
    fun getWishlistProductById(id:String): List<Wishlist>


    @Query("DELETE FROM Wishlist")
    fun deleteAllWishlistData()

    @Query("DELETE FROM Wishlist WHERE id= :ID")
    fun deleteFromWishlistByID(ID: String)

}
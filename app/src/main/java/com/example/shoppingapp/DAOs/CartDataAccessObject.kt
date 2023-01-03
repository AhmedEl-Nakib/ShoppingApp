package com.example.shoppingapp.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.Product

@Dao
interface CartDataAccessObject {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addProduct(product: Cart)

    @Query("SELECT * FROM Cart")
    fun getAllCart(): List<Cart>

    @Query("SELECT * FROM Cart WHERE id=:id")
    fun getProductById(id:String): List<Cart>


    @Query("DELETE FROM Cart")
    fun deleteAllCartData()

    @Query("DELETE FROM Cart WHERE id= :ID")
    fun deleteFromCartByID(ID: String)

    @Query("UPDATE Cart SET quantity= :quantity WHERE id= :ID")
    fun updateCartProduct(quantity: String, ID: String)

}
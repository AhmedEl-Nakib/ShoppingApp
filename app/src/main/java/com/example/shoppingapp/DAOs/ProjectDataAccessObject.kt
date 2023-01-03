package com.example.shoppingapp.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingapp.entity.Product

@Dao
interface ProjectDataAccessObject {

    @Insert
    fun addProduct(product: Product)

    @Query("SELECT * FROM Product")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM Product WHERE id=:id")
    fun getProductById(id:String): Product


    @Query("DELETE FROM Product")
    fun deleteAllProducts()

    @Query("DELETE FROM Product WHERE id= :ID")
    fun deleteProductByID(ID: String)

    @Query("UPDATE Product SET title= :title , price = :price , description= :description WHERE id= :ID")
    fun updateProduct(title: String, price:String,description:String,ID: String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(plants: List<Product>)
}
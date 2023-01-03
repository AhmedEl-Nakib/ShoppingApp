package com.example.shoppingapp.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppingapp.entity.Cart
import com.example.shoppingapp.entity.Orders

@Dao
interface OrdersDAO {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addOrder(order: Orders)

    @Query("SELECT * FROM Orders WHERE userID= :userID")
    fun getAllOrders(userID: String): List<Orders>

    @Query("SELECT * FROM Orders")
    fun getOrders(): List<Orders>

    @Query("SELECT * FROM Orders WHERE status= :status")
    fun getOrdersByStatus(status:String): List<Orders>


    @Query("UPDATE Orders SET status= :status WHERE id= :ID")
    fun updateOrder(status: String, ID: String)

    @Query(" SELECT * FROM Orders WHERE insertedDate BETWEEN :fromTime AND :toTime AND status= :status")
    fun getOrdersTime(fromTime: Long, toTime: Long, status: String): List<Orders>

    @Query(" SELECT * FROM Orders WHERE insertedDate BETWEEN :fromTime AND :toTime")
    fun getOrdersTime(fromTime: Long, toTime: Long): List<Orders>

}
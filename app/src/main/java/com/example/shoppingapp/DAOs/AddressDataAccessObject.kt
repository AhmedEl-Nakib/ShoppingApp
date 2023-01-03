package com.example.shoppingapp.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.shoppingapp.entity.Address

@Dao
interface AddressDataAccessObject {

    @Insert
    fun addAddress(address: Address)

    @Query("SELECT * FROM Address")
    fun getAllAddresses(): List<Address>

    @Query("DELETE FROM Address")
    fun deleteAllAddresses()

    @Query("DELETE FROM Address WHERE id= :ID")
    fun deleteAddressByID(ID: Int)

    @Query("UPDATE Address SET homeDeliveryAddress = :check WHERE id= :ID")
    fun updateSelectedAddress(check:Boolean,ID: Int)

    @Query("UPDATE Address SET homeDeliveryAddress = :check")
    fun updateUnselectedAddress(check:Boolean)
}
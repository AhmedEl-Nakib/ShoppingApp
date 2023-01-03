package com.example.shoppingapp.view.AddNewAddressFeature

import android.app.Application
import androidx.room.Room
import com.example.shoppingapp.room.AppDatabase
import com.example.shoppingapp.entity.Address

class AddNewAddressRepo (application : Application) {
    val db = Room.databaseBuilder(application, AppDatabase::class.java, "usersDB")
        .allowMainThreadQueries().build()

    fun addAddress (address: Address){
        db.AddressDao().addAddress(address)
    }
}
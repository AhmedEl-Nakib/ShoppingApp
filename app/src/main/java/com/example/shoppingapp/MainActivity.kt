package com.example.shoppingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.shoppingapp.entity.Product
import com.example.shoppingapp.room.AppDatabase
import com.example.shoppingapp.ui.theme.ShoppingAppTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkDatabase()
        setContent {
            ShoppingAppTheme {
                Nav()
            }
        }
    }

    private fun checkDatabase(){
        try {
            val filename = "products.json"
            applicationContext.assets.open(filename).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val productType = object : TypeToken<List<Product>>() {}.type
                    val productList:List<Product> = Gson().fromJson(jsonReader,productType)
                    val db = Room.databaseBuilder(this, AppDatabase::class.java, "usersDB")
                        .allowMainThreadQueries().build()
                    if(db.ProductDao().getAllProducts().isEmpty()){
                        db.ProductDao().insertAll(productList)
                    }
                    Log.i("Insert Success","Room ${db.ProductDao().getAllProducts()}")
                }
            }
        } catch (ex: Exception) {
            Log.e("ExceptionMain", "Error seeding database", ex)
        }
    }
}

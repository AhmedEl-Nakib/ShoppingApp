package com.example.shoppingapp.RemoteDB

import com.example.shoppingapp.entity.Product
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitAPI {

    @GET("AyaZayyan/d54ee121ddadd4af7008822007e905c2/raw/a272db11cfb1da179b6fc4e2bf3c8c5209a477ea/products.json")
    fun getProducts() : Call<ArrayList<Product>>

}
package com.example.shoppingapp.entity

import android.util.Base64.encodeToString
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun listToJsonString(value: List<Cart>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<Cart>::class.java).toList()

}
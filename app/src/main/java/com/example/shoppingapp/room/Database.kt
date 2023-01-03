package com.example.shoppingapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.TypeConverters
import com.example.practice2.room.UserDao
import com.example.shoppingapp.DAOs.AddressDataAccessObject
import com.example.shoppingapp.DAOs.ProjectDataAccessObject
import com.example.shoppingapp.DAOs.*
import com.example.shoppingapp.entity.UserReview
import com.example.shoppingapp.entity.*

@Database(entities = [Product::class, Address::class, UserReview::class,Users::class,Cart::class,Wishlist::class, Orders::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun ProductDao(): ProjectDataAccessObject
    abstract fun AddressDao(): AddressDataAccessObject
    abstract fun UsersDao(): UsersDao
    abstract fun CartDataAccessObject(): CartDataAccessObject
    abstract fun WishlistDataAccessObject(): WishlistDataAccessObject
    abstract fun OrdersDAO(): OrdersDAO


    //abstract fun getcategoryDao(): CategoryDao
    //abstract fun getproductDao(): ProductDao
    //abstract fun getuserDao(): com.example.cspart2.data.mdao.UserDao
    abstract fun getUserReviewDao(): UserReviewDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }


}

package com.example.shoppingapp.DAOs

import androidx.room.*
import com.example.shoppingapp.entity.UserReview


@Dao
interface UserReviewDao {
    //@Insert(onConflict = OnConflictStrategy.ABORT)
    //suspend fun addReview()

    @Query("SELECT * FROM user_review")
    fun readAllData(): List<UserReview>

    @Query("SELECT * FROM user_review WHERE productID=:id")
    fun readAllData(id:String): List<UserReview>

    @Query("SELECT * FROM user_review WHERE userReviewID = :cId")
    suspend fun getUserReviewByID(cId: Long): UserReview?

    //@Query("SELECT * FROM user_review WHERE userID = :cId")
    //suspend fun getUserReviewByUserID(cId: Long): UserReview?

//    @Query("SELECT * FROM user_review WHERE productID = :cId")
//    suspend fun getUserReviewByProductID(cId: Long): UserReview?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(entity: UserReview)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(entities: Collection<UserReview>)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(entity: UserReview)

    @Delete
    fun delete(entity: UserReview)

    @Query("DELETE FROM user_review WHERE userReviewID= :ID")
    fun deleteFromReviewByID(ID: String)

}
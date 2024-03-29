package com.example.aop_part6_chapter01.data.db.dao

import androidx.room.*
import com.example.aop_part6_chapter01.data.entity.RestaurantFoodEntity

@Dao
interface FoodMenuBasketDao {

    @Query("SELECT * FROM RestaurantFoodEntity WHERE id=:id AND restaurantId=:restaurantId")
    suspend fun get(restaurantId: Long, id: Long): RestaurantFoodEntity?

    @Query("SELECT * FROM RestaurantFoodEntity")
    suspend fun getAll(): List<RestaurantFoodEntity>

    @Query("SELECT * FROM RestaurantFoodEntity WHERE restaurantId=:restaurantId")
    suspend fun getAllByRestaurantId(restaurantId: Long): List<RestaurantFoodEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(FoodEntity: RestaurantFoodEntity)

    @Query("DELETE FROM RestaurantFoodEntity WHERE id=:id")
    suspend fun delete(id: String)

    @Query("DELETE FROM RestaurantFoodEntity")
    suspend fun deleteAll()

}
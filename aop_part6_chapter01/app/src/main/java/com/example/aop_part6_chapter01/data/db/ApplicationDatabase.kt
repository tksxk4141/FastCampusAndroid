package com.example.aop_part6_chapter01.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.aop_part6_chapter01.data.db.dao.FoodMenuBasketDao
import com.example.aop_part6_chapter01.data.db.dao.LocationDao
import com.example.aop_part6_chapter01.data.db.dao.RestaurantDao
import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.entity.RestaurantEntity
import com.example.aop_part6_chapter01.data.entity.RestaurantFoodEntity

@Database(
    entities = [LocationLatLngEntity::class, RestaurantEntity::class, RestaurantFoodEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ApplicationDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun LocationDao(): LocationDao

    abstract fun FoodMenuBasketDao(): FoodMenuBasketDao

    abstract fun RestaurantDao(): RestaurantDao
}
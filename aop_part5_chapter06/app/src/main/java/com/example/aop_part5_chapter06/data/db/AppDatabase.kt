package com.example.aop_part5_chapter06.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aop_part5_chapter06.data.entity.ShippingCompany
import com.example.aop_part5_chapter06.data.entity.TrackingItem


@Database(
    entities = [TrackingItem::class, ShippingCompany::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun trackingItemDao(): TrackingItemDao
    abstract fun shippingCompanyDao(): ShippingCompanyDao

    companion object {
        private const val DATABASE_NAME = "tracking.db"

        fun build(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}
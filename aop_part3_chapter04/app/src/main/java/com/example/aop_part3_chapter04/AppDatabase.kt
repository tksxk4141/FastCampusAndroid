package com.example.aop_part3_chapter04

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.aop_part3_chapter04.dao.HistoryDao
import com.example.aop_part3_chapter04.dao.ReviewDao
import com.example.aop_part3_chapter04.model.History
import com.example.aop_part3_chapter04.model.Review

@Database(entities = [History::class, Review::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun reviewDao(): ReviewDao

}

fun getAppDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "BookSearchDB"
    ).build()
}
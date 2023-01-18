package com.example.aop_part5_chapter02.data.db

import android.content.Context
import androidx.room.Room

internal fun provideDB(context: Context): ProductDatabase =
    Room.databaseBuilder(context, ProductDatabase::class.java, ProductDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ProductDatabase) = database.productDao()
package com.example.aop_part5_chapter05.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubwayEntity(
    @PrimaryKey val subwayId: Int,
)
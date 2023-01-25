package com.example.aop_part5_chapter05.data.db.entity

import androidx.room.Entity

@Entity(primaryKeys = ["stationName", "subwayId"])
data class StationSubwayCrossEntity(
    val stationName: String,
    val subwayId: Int
)
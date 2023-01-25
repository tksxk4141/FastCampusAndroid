package com.example.aop_part5_chapter05.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class StationWithSubwaysEntity(
    @Embedded val station: StationEntity,
    @Relation(
        parentColumn = "stationName",
        entityColumn = "subwayId",
        associateBy = Junction(StationSubwayCrossEntity::class)
    )
    val subways: List<SubwayEntity>
)
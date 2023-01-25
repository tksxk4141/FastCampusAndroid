package com.example.aop_part5_chapter05.data.api.response.mapper

import com.example.aop_part5_chapter05.data.api.response.RealtimeArrival
import com.example.aop_part5_chapter05.data.db.entity.StationEntity
import com.example.aop_part5_chapter05.data.db.entity.SubwayEntity
import com.example.aop_part5_chapter05.domain.ArrivalInformation
import com.example.aop_part5_chapter05.domain.Station
import com.example.aop_part5_chapter05.domain.Subway
import java.text.SimpleDateFormat
import java.util.*


private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.'0'", Locale.KOREA)
private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.KOREA)

private const val INVALID_FIELD = "-"

fun RealtimeArrival.toArrivalInformation(): ArrivalInformation =
    ArrivalInformation(
        subway = Subway.findById(subwayId),
        direction = trainLineNm?.split("-")
            ?.get(1)
            ?.trim()
            ?: INVALID_FIELD,
        destination = bstatnNm ?: INVALID_FIELD,
        message = arvlMsg2
            ?.replace(statnNm.toString(), "당역")
            ?.replace("[\\[\\]]".toRegex(), "")
            ?: INVALID_FIELD,
        updatedAt = recptnDt
            ?.let { apiDateFormat.parse(it) }
            ?.let { dateFormat.format(it) }
            ?: INVALID_FIELD
    )

fun Station.toStationEntity() =
    StationEntity(
        stationName = name,
        isFavorited = isFavorited,
    )

fun List<RealtimeArrival>.toArrivalInformation(): List<ArrivalInformation> = map { it.toArrivalInformation() }

fun List<SubwayEntity>.toSubways(): List<Subway> = map { Subway.findById(it.subwayId) }
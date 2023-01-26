package com.example.aop_part5_chapter06.data.repository

import com.example.aop_part5_chapter06.data.entity.Level
import com.example.aop_part5_chapter06.data.entity.ShippingCompany
import com.example.aop_part5_chapter06.data.entity.TrackingInformation
import com.example.aop_part5_chapter06.data.entity.TrackingItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TrackingItemRepositoryStub() : TrackingItemRepository {

    override val trackingItems: Flow<List<TrackingItem>> = flowOf(emptyList())

    override suspend fun getTrackingItemInformation(): List<Pair<TrackingItem, TrackingInformation>> =
        listOf(
            TrackingItem("1", ShippingCompany("1", "대한통운")) to TrackingInformation(itemName = "운동화", level = Level.START),
            TrackingItem("2", ShippingCompany("1", "대한통운")) to TrackingInformation(itemName = "장난감", level = Level.START),
            TrackingItem("3", ShippingCompany("1", "대한통운")) to TrackingInformation(itemName = "의류", level = Level.START),
            TrackingItem("4", ShippingCompany("1", "대한통운")) to TrackingInformation(itemName = "가전", level = Level.START),
            TrackingItem("5", ShippingCompany("1", "대한통운")) to TrackingInformation(itemName = "음반/DVD", level = Level.ON_TRANSIT),
            TrackingItem("6", ShippingCompany("1", "대한통운")) to TrackingInformation(itemName = "도서", level = Level.COMPLETE),
        )

    override suspend fun getTrackingInformation(companyCode: String, invoice: String): TrackingInformation? = null

    override suspend fun saveTrackingItem(trackingItem: TrackingItem) = Unit

    override suspend fun deleteTrackingItem(trackingItem: TrackingItem) = Unit
}
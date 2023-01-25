package com.example.aop_part5_chapter05.data.api

import android.util.Log
import com.example.aop_part5_chapter05.data.db.entity.StationEntity
import com.example.aop_part5_chapter05.data.db.entity.SubwayEntity
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class StationStorageApi(
    firebaseStorage: FirebaseStorage
) : StationApi {

    private val sheetReference = firebaseStorage.reference.child(STATION_DATA_FILE_NAME)

    override suspend fun getStationDataUpdatedTimeMillis(): Long =
        sheetReference.metadata.await().updatedTimeMillis

    override suspend fun getStationSubways(): List<Pair<StationEntity, SubwayEntity>> {
        val downloadSizeBytes = sheetReference.metadata.await().sizeBytes
        val byteArray = sheetReference.getBytes(downloadSizeBytes).await()

        val list = byteArray.decodeToString().lines().drop(1).map { it.split(",") }.toMutableList()

        Log.e("subway", list.removeAt(list.size - 1).toString())

        return list.map { StationEntity(it[1]) to SubwayEntity(it[0].toInt()) }.toList()
    }

    companion object {
        private const val STATION_DATA_FILE_NAME = "station_data.csv"
    }
}
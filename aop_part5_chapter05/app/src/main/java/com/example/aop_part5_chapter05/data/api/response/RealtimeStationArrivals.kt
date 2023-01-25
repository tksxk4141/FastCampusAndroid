package com.example.aop_part5_chapter05.data.api.response

import com.google.gson.annotations.SerializedName

data class RealtimeStationArrivals(
    @SerializedName("errorMessage")
    val errorMessage: ErrorMessage? = null,
    @SerializedName("realtimeArrivalList")
    val realtimeArrivalList: List<RealtimeArrival>? = null
)
package com.example.aop_part4_chapter02.service

import com.google.gson.annotations.SerializedName

data class MusicEntity(
    @SerializedName("track") val track: String,
    @SerializedName("stramUrl") val streamUrl: String,
    @SerializedName("artist") val artist: String,
    @SerializedName("coverUrl") val coverUrl: String
)
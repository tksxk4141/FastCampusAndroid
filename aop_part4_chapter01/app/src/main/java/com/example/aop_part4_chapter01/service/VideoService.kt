package com.example.aop_part4_chapter01.service

import com.example.aop_part4_chapter01.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {
    @GET("/v3/595dce92-6150-4469-a6e1-96486ff44a6d")
    fun listVideos(): Call<VideoDto>

}
package com.example.aop_part4_chapter02.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {
    @GET("/v3/3de39293-0690-407f-9a50-a2eceedff0b6")
    fun listMusics(): Call<MusicDto>
}
package com.example.aop_part3_chapter07

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("/v3/8d931c1e-2e8d-459f-a5c0-0ec995e9c6b1")
    fun getHouseList(): Call<HouseDto>
}
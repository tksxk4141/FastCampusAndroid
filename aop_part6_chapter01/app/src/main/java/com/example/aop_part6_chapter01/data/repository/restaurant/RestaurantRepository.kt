package com.example.aop_part6_chapter01.data.repository.restaurant

import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.entity.RestaurantEntity
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantCategory

interface RestaurantRepository {

    suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ): List<RestaurantEntity>


}
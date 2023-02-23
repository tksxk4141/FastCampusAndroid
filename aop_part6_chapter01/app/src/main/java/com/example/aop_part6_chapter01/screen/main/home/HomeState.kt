package com.example.aop_part6_chapter01.screen.main.home

import androidx.annotation.StringRes
import com.example.aop_part6_chapter01.data.entity.MapSearchInfoEntity

sealed class HomeState {
    object Uninitialized: HomeState()

    object Loading: HomeState()

    data class Success(
        val mapSearchInfoEntity: MapSearchInfoEntity,
        val isLocationSame: Boolean
//        val foodMenuListInBasket: List<RestaurantFoodEntity>? = null
    ): HomeState()

    data class Error(
        @StringRes val messageId: Int
    ): HomeState()
}

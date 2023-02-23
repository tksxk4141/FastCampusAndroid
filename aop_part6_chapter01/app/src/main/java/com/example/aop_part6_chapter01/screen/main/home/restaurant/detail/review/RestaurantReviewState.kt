package com.example.aop_part6_chapter01.screen.main.home.restaurant.detail.review

import com.example.aop_part6_chapter01.model.restaurant.RestaurantReviewModel

sealed class RestaurantReviewState {

    object Uninitialized: RestaurantReviewState()

    object Loading: RestaurantReviewState()

    data class Success(
        val reviewList: List<RestaurantReviewModel>
    ): RestaurantReviewState()

}
package com.example.aop_part6_chapter01.screen.main.home.restaurant.detail

import androidx.annotation.StringRes
import com.example.aop_part6_chapter01.R

enum class RestaurantDetailCategory(
    @StringRes val categoryNameId: Int
) {

    MENU(R.string.menu), REVIEW(R.string.review)

}
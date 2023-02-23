package com.example.aop_part6_chapter01.widget.adapter.listener.restaurant

import com.example.aop_part6_chapter01.model.restaurant.RestaurantModel

interface RestaurantLikeListListener: RestaurantListListener {

    fun onDislikeItem(model: RestaurantModel)

}

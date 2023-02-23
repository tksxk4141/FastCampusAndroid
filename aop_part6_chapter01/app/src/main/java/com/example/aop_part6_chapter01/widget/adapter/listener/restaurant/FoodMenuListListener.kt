package com.example.aop_part6_chapter01.widget.adapter.listener.restaurant

import com.example.aop_part6_chapter01.model.restaurant.FoodModel
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener

interface FoodMenuListListener: AdapterListener {

    fun onClickItem(model: FoodModel)

}
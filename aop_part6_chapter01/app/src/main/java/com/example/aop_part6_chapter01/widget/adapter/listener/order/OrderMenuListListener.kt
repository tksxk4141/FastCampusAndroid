package com.example.aop_part6_chapter01.widget.adapter.listener.order

import com.example.aop_part6_chapter01.model.restaurant.FoodModel
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener

interface OrderMenuListListener: AdapterListener {

    fun onRemoveItem(model: FoodModel)

}
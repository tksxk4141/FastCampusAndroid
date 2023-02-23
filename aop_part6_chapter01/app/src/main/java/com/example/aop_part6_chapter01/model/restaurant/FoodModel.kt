package com.example.aop_part6_chapter01.model.restaurant

import com.example.aop_part6_chapter01.data.entity.RestaurantFoodEntity
import com.example.aop_part6_chapter01.model.CellType
import com.example.aop_part6_chapter01.model.Model

data class FoodModel(
    override val id: Long,
    override val type: CellType = CellType.FOOD_CELL,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long,
    val foodId: String,
    val restaurantTitle: String
) : Model(id, type) {

    fun toEntity(basketIndex: Int) = RestaurantFoodEntity(
        "${foodId}_${basketIndex}", title, description, price, imageUrl, restaurantId, restaurantTitle
    )

}
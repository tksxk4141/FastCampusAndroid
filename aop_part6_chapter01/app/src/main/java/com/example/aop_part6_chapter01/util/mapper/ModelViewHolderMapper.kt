package com.example.aop_part6_chapter01.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.aop_part6_chapter01.databinding.*
import com.example.aop_part6_chapter01.model.CellType
import com.example.aop_part6_chapter01.model.Model
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.viewholder.EmptyViewHolder
import com.example.aop_part6_chapter01.widget.adapter.viewholder.ModelViewHolder
import com.example.aop_part6_chapter01.widget.adapter.viewholder.food.FoodMenuViewHolder
import com.example.aop_part6_chapter01.widget.adapter.viewholder.order.OrderMenuViewHolder
import com.example.aop_part6_chapter01.widget.adapter.viewholder.order.OrderViewHolder
import com.example.aop_part6_chapter01.widget.adapter.viewholder.restaurant.LikeRestaurantViewHolder
import com.example.aop_part6_chapter01.widget.adapter.viewholder.restaurant.RestaurantViewHolder
import com.example.aop_part6_chapter01.widget.adapter.viewholder.review.RestaurantReviewViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M: Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourceProvider: ResourceProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = when(type) {
            CellType.EMPTY_CELL -> EmptyViewHolder(
                ViewholderEmptyBinding.inflate(inflater, parent, false),
                viewModel,
                resourceProvider
            )
            CellType.RESTAURANT_CELL -> RestaurantViewHolder(
                ViewholderRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourceProvider
            )
            CellType.LIKE_RESTAURANT_CELL ->
                LikeRestaurantViewHolder(
                    ViewholderLikeRestaurantBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourceProvider
                )
            CellType.FOOD_CELL ->
                FoodMenuViewHolder(
                    ViewholderFoodMenuBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourceProvider
                )
            CellType.REVIEW_CELL ->
                RestaurantReviewViewHolder(
                    ViewholderRestaurantReviewBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourceProvider
                )
            CellType.ORDER_FOOD_CELL ->
                OrderMenuViewHolder(
                    ViewholderOrderMenuBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourceProvider
                )
            CellType.ORDER_CELL ->
                OrderViewHolder(
                    ViewholderOrderBinding.inflate(inflater, parent, false),
                    viewModel,
                    resourceProvider
                )
        }
        return viewHolder as ModelViewHolder<M>
    }
}
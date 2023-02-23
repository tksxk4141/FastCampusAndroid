package com.example.aop_part6_chapter01.widget.adapter.viewholder.restaurant

import com.example.aop_part6_chapter01.R
import com.example.aop_part6_chapter01.databinding.ViewholderRestaurantBinding
import com.example.aop_part6_chapter01.extensions.clear
import com.example.aop_part6_chapter01.extensions.load
import com.example.aop_part6_chapter01.model.restaurant.RestaurantModel
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener
import com.example.aop_part6_chapter01.widget.adapter.listener.restaurant.RestaurantListListener
import com.example.aop_part6_chapter01.widget.adapter.viewholder.ModelViewHolder

class RestaurantViewHolder(
    private val binding: ViewholderRestaurantBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
): ModelViewHolder<RestaurantModel>(binding, viewModel, resourceProvider) {

    override fun reset() = with(binding) {
        restaurantImage.clear()
    }

    override fun bindData(model: RestaurantModel) {
        super.bindData(model)
        with(binding) {
            restaurantImage.load(model.restaurantImageUrl, 24f)
            restaurantTitleText.text = model.restaurantTitle
            gradeText.text = resourceProvider.getString(R.string.grade_format, model.grade)
            reviewCountText.text = resourceProvider.getString(R.string.review_count, model.reviewCount)
            val (minTime, maxTime) = model.deliveryTimeRange
            deliveryTimeText.text = resourceProvider.getString(R.string.delivery_time, minTime, maxTime)

            val (minTip, maxTip) = model.deliveryTipRange
            deliveryTipText.text = resourceProvider.getString(R.string.delivery_tip, minTip, maxTip)
        }
    }

    override fun bindViews(model: RestaurantModel, adapterListener: AdapterListener) = with(binding) {
        if(adapterListener is RestaurantListListener) {
            root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }

}
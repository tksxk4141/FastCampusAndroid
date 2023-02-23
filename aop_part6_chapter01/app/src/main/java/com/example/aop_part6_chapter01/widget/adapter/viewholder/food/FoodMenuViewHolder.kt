package com.example.aop_part6_chapter01.widget.adapter.viewholder.food

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.aop_part6_chapter01.R
import com.example.aop_part6_chapter01.databinding.ViewholderFoodMenuBinding
import com.example.aop_part6_chapter01.extensions.clear
import com.example.aop_part6_chapter01.extensions.load
import com.example.aop_part6_chapter01.model.restaurant.FoodModel
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener
import com.example.aop_part6_chapter01.widget.adapter.listener.restaurant.FoodMenuListListener
import com.example.aop_part6_chapter01.widget.adapter.viewholder.ModelViewHolder

class FoodMenuViewHolder(
    private val binding: ViewholderFoodMenuBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
): ModelViewHolder<FoodModel>(binding, viewModel, resourceProvider) {

    override fun reset() = with(binding) {
        foodImage.clear()
    }

    override fun bindData(model: FoodModel) {
        super.bindData(model)
        with(binding) {
            foodImage.load(model.imageUrl, 24f, CenterCrop())
            foodTitleText.text = model.title
            foodDescriptionText.text = model.description
            priceText.text = resourceProvider.getString(R.string.price, model.price)
        }
    }

    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
        if (adapterListener is FoodMenuListListener) {
            binding.root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }

}
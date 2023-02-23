package com.example.aop_part6_chapter01.widget.adapter.viewholder.order

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.aop_part6_chapter01.R
import com.example.aop_part6_chapter01.databinding.ViewholderOrderMenuBinding
import com.example.aop_part6_chapter01.extensions.clear
import com.example.aop_part6_chapter01.extensions.load
import com.example.aop_part6_chapter01.model.restaurant.FoodModel
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener
import com.example.aop_part6_chapter01.widget.adapter.listener.order.OrderMenuListListener
import com.example.aop_part6_chapter01.widget.adapter.viewholder.ModelViewHolder

class OrderMenuViewHolder(
    private val binding: ViewholderOrderMenuBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
): ModelViewHolder<FoodModel>(binding, viewModel, resourceProvider) {

    override fun reset() = with(binding) {
        foodImage.clear()
    }

    override fun bindData(model: FoodModel) = with(binding) {
        super.bindData(model)

        foodImage.load(model.imageUrl, 24f, CenterCrop())
        foodTitleText.text = model.title
        foodDescriptionText.text = model.description
        priceText.text = resourceProvider.getString(R.string.price, model.price)
    }

    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
        if (adapterListener is OrderMenuListListener) {
            binding.root.setOnClickListener {
                adapterListener.onRemoveItem(model)
            }
        }
    }

}
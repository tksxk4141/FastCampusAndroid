package com.example.aop_part6_chapter01.widget.adapter.viewholder.order

import com.example.aop_part6_chapter01.R
import com.example.aop_part6_chapter01.databinding.ViewholderOrderBinding
import com.example.aop_part6_chapter01.model.order.OrderModel
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener
import com.example.aop_part6_chapter01.widget.adapter.listener.order.OrderListListener
import com.example.aop_part6_chapter01.widget.adapter.viewholder.ModelViewHolder

class OrderViewHolder(
    private val binding: ViewholderOrderBinding,
    viewModel: BaseViewModel,
    resourceProvider: ResourceProvider
) : ModelViewHolder<OrderModel>(binding, viewModel, resourceProvider) {

    override fun reset() {
        binding.orderContentText.text = ""
    }

    override fun bindData(model: OrderModel) =  with(binding) {
        super.bindData(model)

        orderTitleText.text = resourceProvider.getString(R.string.order_history_title, model.orderId)
        val foodMenuList = model.foodMenuList
        foodMenuList
            .groupBy { it.title }
            .entries.forEach { (title, menuList) ->
                val orderDataStr =
                    orderContentText.text.toString() + "메뉴 : $title | 가격 : ${menuList.first().price}원 X ${menuList.size}\n"
                orderContentText.text = orderDataStr
            }
        orderContentText.text = orderContentText.text.trim()
        orderTotalPriceText.text =
            resourceProvider.getString(R.string.price, foodMenuList.map { it.price }.reduce { total, price -> total + price })

    }

    override fun bindViews(model: OrderModel, adapterListener: AdapterListener) {
        if (adapterListener is OrderListListener) {
            binding.root.setOnClickListener {
                adapterListener.writeRestaurantReview(model.orderId, model.restaurantTitle)
            }
        }
    }

}

package com.example.aop_part6_chapter01.widget.adapter.viewholder.review

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.aop_part6_chapter01.databinding.ViewholderRestaurantReviewBinding
import com.example.aop_part6_chapter01.extensions.clear
import com.example.aop_part6_chapter01.extensions.load
import com.example.aop_part6_chapter01.model.restaurant.RestaurantReviewModel
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.example.aop_part6_chapter01.widget.adapter.listener.AdapterListener
import com.example.aop_part6_chapter01.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding: ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourceProvider
) : ModelViewHolder<RestaurantReviewModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        reviewThumbnailImage.clear()
        reviewThumbnailImage.isGone = true
    }

    override fun bindData(model: RestaurantReviewModel) {
        super.bindData(model)
        with(binding) {
            if (model.thumbnailImageUri != null) {
                reviewThumbnailImage.isVisible = true
                reviewThumbnailImage.load(model.thumbnailImageUri.toString())
            } else {
                reviewThumbnailImage.isGone = true
            }

            reviewTitleText.text = model.title
            reviewText.text = model.description
            ratingBar.rating = model.grade
        }
    }

    override fun bindViews(model: RestaurantReviewModel, adapterListener: AdapterListener) = Unit

}
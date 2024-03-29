package com.example.aop_part6_chapter01.screen.main.home.restaurant.detail.review

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part6_chapter01.data.entity.ReviewEntity
import com.example.aop_part6_chapter01.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.review.RestaurantReviewRepository
import com.example.aop_part6_chapter01.model.restaurant.RestaurantReviewModel
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantReviewListViewModel(
    private val restaurantTitle: String,
    private val restaurantReviewRepository: RestaurantReviewRepository
) : BaseViewModel() {

    val reviewStateLiveData = MutableLiveData<RestaurantReviewState>(RestaurantReviewState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        reviewStateLiveData.value = RestaurantReviewState.Loading
//        val reviews = restaurantReviewRepository.getReviews(restaurantTitle)
//        reviewStateLiveData.value = RestaurantReviewState.Success(
//            reviews.map{
//                RestaurantReviewModel(
//                    id = it.hashCode().toLong(),
//                    title = it.title,
//                    description = it.description,
//                    grade = it.grade.toFloat(),
//                    thumbnailImageUri = it.images?.first()
//                )
//            }
//        )
        val result = restaurantReviewRepository.getReviews(restaurantTitle)
        when (result) {
            is DefaultRestaurantReviewRepository.Result.Success<*> -> {
                val reviews = result.data as List<ReviewEntity>
                Log.e("data", reviews.toString())
                reviewStateLiveData.value = RestaurantReviewState.Success(
                    reviews.map {
                        RestaurantReviewModel(
                            id = it.hashCode().toLong(),
                            title = it.title,
                            description = it.content,
                            grade = it.rating,
                            thumbnailImageUri = if (it.imageUrlList.isNullOrEmpty()) {
                                null
                            } else {
                                Uri.parse(it.imageUrlList.first())
                            }
                        )
                    }
                )
            }
            else -> Unit
        }

    }

}
package com.example.aop_part6_chapter01.screen.main.home.restaurant

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.repository.restaurant.RestaurantRepository
import com.example.aop_part6_chapter01.model.restaurant.RestaurantModel
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class RestaurantListViewModel(
    private val restaurantCategory: RestaurantCategory,
    private var locationLatLng: LocationLatLngEntity,
    private val restaurantRepository: RestaurantRepository,
    private var restaurantFilterOrder: RestaurantFilterOrder = RestaurantFilterOrder.DEFAULT
): BaseViewModel() {

    val restaurantLiveData = MutableLiveData<List<RestaurantModel>>()

    override fun fetchData(): Job  = viewModelScope.launch {
        val restaurantList = restaurantRepository.getList(restaurantCategory, locationLatLng)

        val sortedList = when (restaurantFilterOrder) {
            RestaurantFilterOrder.DEFAULT -> {
                restaurantList
            }
            RestaurantFilterOrder.LOW_DELIVERY_TIP -> {
                restaurantList.sortedBy { it.deliveryTipRange.first }
            }
            RestaurantFilterOrder.FAST_DELIVERY -> {
                restaurantList.sortedBy { it.deliveryTimeRange.first }
            }
            RestaurantFilterOrder.TOP_RATE -> {
                restaurantList.sortedByDescending { it.grade }
            }
        }
        restaurantLiveData.value = sortedList.map{
            RestaurantModel(
                id = it.id,
                restaurantInfoId = it.restaurantInfoId,
                restaurantCategory = it.restaurantCategory,
                restaurantTitle = it.restaurantTitle,
                restaurantImageUrl = it.restaurantImageUrl,
                grade = it.grade,
                reviewCount = it.reviewCount,
                deliveryTimeRange = it.deliveryTimeRange,
                deliveryTipRange = it.deliveryTipRange,
                restaurantTelNumber = it.restaurantTelNumber
            )
        }
    }

    fun setLocationLatLng(locationLatLngEntity: LocationLatLngEntity) {
        this.locationLatLng = locationLatLngEntity
        fetchData()
    }

    fun setRestaurantFilterOrder(order: RestaurantFilterOrder) {
        this.restaurantFilterOrder = order
        fetchData()
    }
}
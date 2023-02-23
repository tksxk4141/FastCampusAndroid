package com.example.aop_part6_chapter01.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aop_part6_chapter01.R
import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.entity.MapSearchInfoEntity
import com.example.aop_part6_chapter01.data.entity.RestaurantFoodEntity
import com.example.aop_part6_chapter01.data.repository.map.MapRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.aop_part6_chapter01.data.repository.user.UserRepository
import com.example.aop_part6_chapter01.screen.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    private val restaurantFoodRepository: RestaurantFoodRepository
) : BaseViewModel() {

    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.Uninitialized)

    val foodMenuBasketLiveData = MutableLiveData<List<RestaurantFoodEntity>>()

    fun loadReverseGeoInformation(locationLatLngEntity: LocationLatLngEntity) =
        viewModelScope.launch {
            homeStateLiveData.value = HomeState.Loading
            val userLocation = userRepository.getUserLocation()
            val currentLocation = userLocation ?: locationLatLngEntity

            val addressInfo = mapRepository.getReverseGeoInformation(currentLocation)
            addressInfo?.let { info ->
                homeStateLiveData.value = HomeState.Success(
                    mapSearchInfoEntity = info.toSearchInfoEntity(locationLatLngEntity),
                    isLocationSame = currentLocation == locationLatLngEntity
                )
            } ?: kotlin.run {
                homeStateLiveData.value = HomeState.Error(
                    messageId = R.string.can_not_load_address_info
                )
            }
        }

    fun getMapSearchInfo(): MapSearchInfoEntity? {
        when (val data = homeStateLiveData.value) {
            is HomeState.Success -> {
                return data.mapSearchInfoEntity
            }
            else -> Unit
        }
        return null
    }

    fun checkMyBasket() = viewModelScope.launch {
        foodMenuBasketLiveData.value = restaurantFoodRepository.getAllFoodMenuListInBasket()
    }

    companion object {
        const val MY_LOCATION_KEY = "MyLocation"
    }
}
package com.example.aop_part6_chapter01.viewmodel.restaurant

import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.repository.restaurant.RestaurantRepository
import com.example.aop_part6_chapter01.model.restaurant.RestaurantModel
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantCategory
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantFilterOrder
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantListViewModel
import com.example.aop_part6_chapter01.util.Log
import com.example.aop_part6_chapter01.viewmodel.ViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.test.inject
import org.koin.core.parameter.parametersOf

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
internal class RestaurantListViewModelTest: ViewModelTest() {

    private var restaurantCategory = RestaurantCategory.ALL
    private val locationLatLngEntity = LocationLatLngEntity(0.0, 0.0)

    private val restaurantListViewModel: RestaurantListViewModel by inject { parametersOf(restaurantCategory, locationLatLngEntity) }
    private val restaurantRepository: RestaurantRepository by inject()

    @Test
    fun `test load restaurant list category All`() = runTest {
        val testObservable = restaurantListViewModel.restaurantLiveData.test()

        restaurantListViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                restaurantRepository.getList(restaurantCategory, locationLatLngEntity).map {
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
            )
        )
    }

    @Test
    fun `test load restaurant list category Excepted`() = runTest {
        restaurantCategory = RestaurantCategory.CAFE_DESSERT

        val testObservable = restaurantListViewModel.restaurantLiveData.test()

        restaurantListViewModel.fetchData()

        testObservable.assertValueSequence(
            listOf(
                listOf()
            )
        )
    }

    @Test
    fun `test load restaurant list order by fast delivery time`() = runTest {
        val testObservable = restaurantListViewModel.restaurantLiveData.test()

        restaurantListViewModel.setRestaurantFilterOrder(RestaurantFilterOrder.FAST_DELIVERY)

        testObservable.assertValueSequence(
            listOf(
                restaurantRepository.getList(restaurantCategory, locationLatLngEntity).sortedBy {
                    it.deliveryTimeRange.first
                }.map {
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
            )
        )

    }
}
package com.example.aop_part6_chapter01.di

import com.example.aop_part6_chapter01.data.TestOrderRepository
import com.example.aop_part6_chapter01.data.TestRestaurantFoodRepository
import com.example.aop_part6_chapter01.data.TestRestaurantRepository
import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.repository.order.OrderRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.RestaurantRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantCategory
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantListViewModel
import com.example.aop_part6_chapter01.screen.order.OrderMenuListViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    viewModel { (restaurantCategory: RestaurantCategory, locationLatLngEntity: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory, locationLatLngEntity, get())
    }

    viewModel { (firebaseAuth: FirebaseAuth) -> OrderMenuListViewModel(get(), get(), firebaseAuth) }

    single<RestaurantRepository> { TestRestaurantRepository() }

    single<RestaurantFoodRepository> { TestRestaurantFoodRepository() }

    single<OrderRepository> { TestOrderRepository() }
}
package com.example.aop_part6_chapter01.di

import com.example.aop_part6_chapter01.data.entity.LocationLatLngEntity
import com.example.aop_part6_chapter01.data.entity.MapSearchInfoEntity
import com.example.aop_part6_chapter01.data.entity.RestaurantEntity
import com.example.aop_part6_chapter01.data.entity.RestaurantFoodEntity
import com.example.aop_part6_chapter01.data.preference.AppPreferenceManager
import com.example.aop_part6_chapter01.data.repository.map.DefaultMapRepository
import com.example.aop_part6_chapter01.data.repository.map.MapRepository
import com.example.aop_part6_chapter01.data.repository.order.DefaultOrderRepository
import com.example.aop_part6_chapter01.data.repository.order.OrderRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.DefaultRestaurantRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.RestaurantRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.example.aop_part6_chapter01.data.repository.restaurant.review.RestaurantReviewRepository
import com.example.aop_part6_chapter01.data.repository.user.DefaultUserRepository
import com.example.aop_part6_chapter01.data.repository.user.UserRepository
import com.example.aop_part6_chapter01.screen.main.MainViewModel
import com.example.aop_part6_chapter01.screen.main.home.HomeViewModel
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantCategory
import com.example.aop_part6_chapter01.screen.main.home.restaurant.RestaurantListViewModel
import com.example.aop_part6_chapter01.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import com.example.aop_part6_chapter01.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import com.example.aop_part6_chapter01.screen.main.home.restaurant.detail.review.RestaurantReviewListViewModel
import com.example.aop_part6_chapter01.screen.main.like.RestaurantLikeListViewModel
import com.example.aop_part6_chapter01.screen.main.my.MyViewModel
import com.example.aop_part6_chapter01.screen.mylocation.MyLocationViewModel
import com.example.aop_part6_chapter01.screen.order.OrderMenuListViewModel
import com.example.aop_part6_chapter01.screen.review.gallery.GalleryPhotoRepository
import com.example.aop_part6_chapter01.screen.review.gallery.GalleryViewModel
import com.example.aop_part6_chapter01.util.event.MenuChangeEventBus
import com.example.aop_part6_chapter01.util.provider.DefaultResourceProvider
import com.example.aop_part6_chapter01.util.provider.ResourceProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single { MenuChangeEventBus() }

    viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { RestaurantLikeListViewModel(get()) }
    viewModel { MyViewModel(get(), get(), get()) }

    viewModel { (restaurantCategory: RestaurantCategory, locationLatLng: LocationLatLngEntity) -> RestaurantListViewModel(restaurantCategory, locationLatLng, get())}
    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) -> MyLocationViewModel(mapSearchInfoEntity, get(), get()) }
    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity, get(), get())}
    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>) -> RestaurantMenuListViewModel(restaurantId, restaurantFoodList, get()) }
    viewModel { (restaurantTitle: String) -> RestaurantReviewListViewModel(restaurantTitle, get()) }
    viewModel { OrderMenuListViewModel(get(), get(), get()) }
    viewModel { GalleryViewModel(get()) }

    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get(), get()) }
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(), get(), get()) }
    single<RestaurantReviewRepository> { DefaultRestaurantReviewRepository(get(), get()) }
    single<OrderRepository> { DefaultOrderRepository(get(), get()) }
    single { GalleryPhotoRepository(androidApplication()) }

    single { AppPreferenceManager(androidApplication()) }

    single<ResourceProvider> { DefaultResourceProvider(androidApplication()) }
    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }
    single { provideRestaurantDao(get()) }
    single { provideFoodMenuBasketDao(get()) }

    single { provideGsonConvertFactory() }
    single { buildOkHttpClient() }

    single(named("map")) { provideMapRetrofit(get(), get()) }
    single(named("food")) { provideFoodRetrofit(get(), get()) }
    single { provideMapApiService(get(qualifier = named("map"))) }
    single { provideFoodApiService(get(qualifier = named("food"))) }

    single { Firebase.firestore }
    single { Firebase.storage }
    single { FirebaseAuth.getInstance() }
}
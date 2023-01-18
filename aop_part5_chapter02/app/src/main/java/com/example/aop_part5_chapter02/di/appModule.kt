package com.example.aop_part5_chapter02.di

import com.example.aop_part5_chapter02.data.db.provideDB
import com.example.aop_part5_chapter02.data.db.provideToDoDao
import com.example.aop_part5_chapter02.data.network.buildOkHttpClient
import com.example.aop_part5_chapter02.data.network.provideGsonConverterFactory
import com.example.aop_part5_chapter02.data.network.provideProductApiService
import com.example.aop_part5_chapter02.data.network.provideProductRetrofit
import com.example.aop_part5_chapter02.data.preference.PreferenceManager
import com.example.aop_part5_chapter02.data.repository.DefaultProductRepository
import com.example.aop_part5_chapter02.data.repository.ProductRepository
import com.example.aop_part5_chapter02.domain.product.*
import com.example.aop_part5_chapter02.domain.product.DeleteOrderedProductListUseCase
import com.example.aop_part5_chapter02.domain.product.GetOrderedProductListUseCase
import com.example.aop_part5_chapter02.domain.product.GetProductItemUseCase
import com.example.aop_part5_chapter02.domain.product.GetProductListUseCase
import com.example.aop_part5_chapter02.domain.product.OrderProductItemUseCase
import com.example.aop_part5_chapter02.presentation.detail.ProductDetailViewModel
import com.example.aop_part5_chapter02.presentation.list.ProductListViewModel
import com.example.aop_part5_chapter02.presentation.main.MainViewModel
import com.example.aop_part5_chapter02.presentation.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    // ViewModel
    viewModel { MainViewModel() }
    viewModel { ProductListViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { (productId: Long) -> ProductDetailViewModel(productId, get(), get())}

    // UseCase
    factory { GetProductItemUseCase(get()) }
    factory { GetProductListUseCase(get()) }
    factory { GetOrderedProductListUseCase(get()) }
    factory { OrderProductItemUseCase(get()) }
    factory { DeleteOrderedProductListUseCase(get()) }

    // Repository
    single<ProductRepository> { DefaultProductRepository(get(), get(), get()) }

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }
    single { provideProductRetrofit(get(), get()) }
    single { provideProductApiService(get()) }

    single { PreferenceManager(androidContext()) }
    // Database
    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}
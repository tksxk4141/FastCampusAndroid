package com.example.aop_part5_chapter05.di

import android.app.Activity
import com.example.aop_part5_chapter05.BuildConfig
import com.example.aop_part5_chapter05.data.api.StationApi
import com.example.aop_part5_chapter05.data.api.StationArrivalsApi
import com.example.aop_part5_chapter05.data.api.StationStorageApi
import com.example.aop_part5_chapter05.data.api.Url
import com.example.aop_part5_chapter05.data.db.AppDatabase
import com.example.aop_part5_chapter05.data.preference.PreferenceManager
import com.example.aop_part5_chapter05.data.preference.SharedPreferenceManager
import com.example.aop_part5_chapter05.data.repository.StationRepository
import com.example.aop_part5_chapter05.data.repository.StationRepositoryImpl
import com.example.aop_part5_chapter05.presentation.stationarrivals.StationArrivalsContract
import com.example.aop_part5_chapter05.presentation.stationarrivals.StationArrivalsFragment
import com.example.aop_part5_chapter05.presentation.stationarrivals.StationArrivalsPresenter
import com.example.aop_part5_chapter05.presentation.stations.StationsContract
import com.example.aop_part5_chapter05.presentation.stations.StationsFragment
import com.example.aop_part5_chapter05.presentation.stations.StationsPresenter
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single { Dispatchers.IO }

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().stationDao() }

    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get())  }

    // Api
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .build()
    }
    single<StationArrivalsApi> {
        Retrofit.Builder().baseUrl(Url.SEOUL_DATA_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }
    single<StationApi> { StationStorageApi(Firebase.storage) }

    // Repository
    single<StationRepository> { StationRepositoryImpl(get(), get(),get(),get(),get())  }

    // Presentation
    scope<StationsFragment> {
        scoped<StationsContract.Presenter> { StationsPresenter(get(), get()) }
    }
    scope<StationArrivalsFragment> {
        scoped<StationArrivalsContract.Presenter> { StationArrivalsPresenter(get(), get(), get()) }
    }
}
package com.example.aop_part5_chapter07.di

import android.app.Activity
import com.example.aop_part5_chapter07.data.api.*
import com.example.aop_part5_chapter07.data.preference.PreferenceManager
import com.example.aop_part5_chapter07.data.preference.SharedPreferenceManager
import com.example.aop_part5_chapter07.data.repository.*
import com.example.aop_part5_chapter07.domain.model.Movie
import com.example.aop_part5_chapter07.domain.usecase.*
import com.example.aop_part5_chapter07.presentation.home.HomeContract
import com.example.aop_part5_chapter07.presentation.home.HomeFragment
import com.example.aop_part5_chapter07.presentation.home.HomePresenter
import com.example.aop_part5_chapter07.presentation.mypage.MyPageContract
import com.example.aop_part5_chapter07.presentation.mypage.MyPageFragment
import com.example.aop_part5_chapter07.presentation.mypage.MyPagePresenter
import com.example.aop_part5_chapter07.presentation.reviews.MovieReviewsContract
import com.example.aop_part5_chapter07.presentation.reviews.MovieReviewsFragment
import com.example.aop_part5_chapter07.presentation.reviews.MovieReviewsPresenter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { Dispatchers.IO }
}

val dataModule = module {
    single { Firebase.firestore }

    single<MovieApi> { MovieFirestoreApi(get()) }
    single<ReviewApi> { ReviewFirestoreApi(get()) }
    single<UserApi> { UserFirestoreApi(get()) }

    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<ReviewRepository> { ReviewRepositoryImpl(get(), get()) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), get()) }

    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManager(get()) }
}

val domainModule = module {
    factory { GetRandomFeaturedMovieUseCase(get(), get()) }
    factory { GetAllMoviesUseCase(get()) }
    factory { GetAllReviewsUseCase(get(), get()) }
    factory { GetMyReviewedMoviesUseCase(get(), get(), get()) }
    factory { SubmitReviewUseCase(get(), get()) }
    factory { DeleteReviewUseCase(get()) }
}

val presenterModule = module {
    scope<HomeFragment> {
        scoped<HomeContract.Presenter> { HomePresenter(get(), get(), get()) }
    }
    scope<MovieReviewsFragment> {
        scoped<MovieReviewsContract.Presenter> { (movie: Movie) ->
            MovieReviewsPresenter(movie, get(), get(), get(), get())
        }
    }
    scope<MyPageFragment> {
        scoped<MyPageContract.Presenter> { MyPagePresenter(get(), get()) }
    }
}
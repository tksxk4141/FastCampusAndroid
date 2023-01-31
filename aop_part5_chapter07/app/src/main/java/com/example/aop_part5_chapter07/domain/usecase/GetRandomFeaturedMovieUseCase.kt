package com.example.aop_part5_chapter07.domain.usecase

import android.os.Debug
import android.util.Log
import com.example.aop_part5_chapter07.data.repository.MovieRepository
import com.example.aop_part5_chapter07.data.repository.ReviewRepository
import com.example.aop_part5_chapter07.domain.model.FeaturedMovie

class GetRandomFeaturedMovieUseCase(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(): FeaturedMovie? {
        val featuredMovies = movieRepository.getAllMovies()
            .filter { it.id.isNullOrBlank().not() }
            .filter { it.isFeatured == true }

        if (featuredMovies.isNullOrEmpty()) {
            return null
        }

        return featuredMovies.random()
            .let { movie ->
                val latestReview = reviewRepository.getLatestReview(movie.id!!)
                FeaturedMovie(movie, latestReview)
            }
    }
}
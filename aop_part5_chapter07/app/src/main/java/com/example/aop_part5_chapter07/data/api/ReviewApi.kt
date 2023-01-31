package com.example.aop_part5_chapter07.data.api

import com.example.aop_part5_chapter07.domain.model.Review

interface ReviewApi {

    suspend fun getLatestReview(movieId: String): Review?

    suspend fun getAllReviews(movieId: String): List<Review>

    suspend fun getAllUserReviews(userId: String): List<Review>

    suspend fun addReview(review: Review): Review

    suspend fun removeReview(review: Review)
}
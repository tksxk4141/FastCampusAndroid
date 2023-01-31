package com.example.aop_part5_chapter07.domain.model

data class FeaturedMovie(
    val movie: Movie,
    val latestReview: Review?
)
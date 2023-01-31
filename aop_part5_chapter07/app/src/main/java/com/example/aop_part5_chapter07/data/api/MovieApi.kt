package com.example.aop_part5_chapter07.data.api

import com.example.aop_part5_chapter07.domain.model.Movie

interface MovieApi {

    suspend fun getAllMovies(): List<Movie>

    suspend fun getMovies(movieIds: List<String>): List<Movie>
}
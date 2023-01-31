package com.example.aop_part5_chapter07.data.repository

import com.example.aop_part5_chapter07.domain.model.Movie

interface MovieRepository {

    suspend fun getAllMovies(): List<Movie>

    suspend fun getMovies(movieIds: List<String>): List<Movie>
}
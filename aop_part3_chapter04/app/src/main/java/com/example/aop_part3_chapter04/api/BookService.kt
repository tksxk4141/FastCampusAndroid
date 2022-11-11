package com.example.aop_part3_chapter04.api

import com.example.aop_part3_chapter04.model.SearchBooksDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookService {
    @GET("/v1/search/book.json")
    fun getBooksByName(
        @Header("X-Naver-Client-Id") id: String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") keyword: String,
        @Query("display") display: Int = 30
    ): Call<SearchBooksDto>
}
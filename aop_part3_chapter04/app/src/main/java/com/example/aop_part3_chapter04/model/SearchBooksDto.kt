package com.example.aop_part3_chapter04.model

import com.google.gson.annotations.SerializedName

data class SearchBooksDto(
    @SerializedName("items") val books: List<Book>
)

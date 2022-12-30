package com.example.aop_part4_chapter03.response.search

data class SearchPoiInfo (
    val totalCount: String,
    val count: String,
    val page: String,
    val pois: Pois
    )
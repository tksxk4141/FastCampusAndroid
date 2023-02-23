package com.example.aop_part6_chapter01.data.entity

import android.net.Uri

data class RestaurantReviewEntity(
    override val id: Long,
    val title: String,
    val description: String,
    val grade: Int,
    val images: List<Uri>? = null
): Entity
package com.example.aop_part6_chapter01.model.restaurant

import android.net.Uri
import com.example.aop_part6_chapter01.model.CellType
import com.example.aop_part6_chapter01.model.Model

data class RestaurantReviewModel(
    override val id: Long,
    override val type: CellType = CellType.REVIEW_CELL,
    val title: String,
    val description: String,
    val grade: Float,
    val thumbnailImageUri: Uri? = null
): Model(id, type)
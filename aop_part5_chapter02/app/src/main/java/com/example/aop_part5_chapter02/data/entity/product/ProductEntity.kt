package com.example.aop_part5_chapter02.data.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class ProductEntity(
    @PrimaryKey val id: Long,
    val createdAt: Date,
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productType: String,
    val productIntroductionImage: String
)
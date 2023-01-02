package com.example.aop_part4_chapter05.data.response

import com.example.aop_part4_chapter05.data.entity.GithubRepoEntity

data class GithubRepoSearchResponse(
    val totalCount: Int,
    val items: List<GithubRepoEntity>
)
package com.example.aop_part5_chapter07.data.api

import com.example.aop_part5_chapter07.domain.model.User

interface UserApi {

    suspend fun saveUser(user: User): User
}
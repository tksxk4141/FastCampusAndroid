package com.example.aop_part5_chapter07.data.api

import com.example.aop_part5_chapter07.domain.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserFirestoreApi(
    private val firestore: FirebaseFirestore
) : UserApi {

    override suspend fun saveUser(user: User): User =
        firestore.collection("users")
            .add(user)
            .await()
            .let { User(it.id) }
}
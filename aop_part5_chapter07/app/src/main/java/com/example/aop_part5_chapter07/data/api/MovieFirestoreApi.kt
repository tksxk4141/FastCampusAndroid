package com.example.aop_part5_chapter07.data.api

import com.example.aop_part5_chapter07.domain.model.Movie
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class MovieFirestoreApi(
    private val firestore: FirebaseFirestore
): MovieApi {
    override suspend fun getAllMovies(): List<Movie>  =
        firestore.collection("movies")
            .get()
            .await()
            .map{it.toObject<Movie>()}

    override suspend fun getMovies(movieIds: List<String>): List<Movie> =
        firestore.collection("movies")
            .whereIn(FieldPath.documentId(), movieIds)
            .get()
            .await()
            .map { it.toObject<Movie>() }


}
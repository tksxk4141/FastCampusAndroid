package com.example.aop_part5_chapter07.domain.usecase

import com.example.aop_part5_chapter07.data.repository.ReviewRepository
import com.example.aop_part5_chapter07.data.repository.UserRepository
import com.example.aop_part5_chapter07.domain.model.Movie
import com.example.aop_part5_chapter07.domain.model.Review
import com.example.aop_part5_chapter07.domain.model.User

class SubmitReviewUseCase(
    private val userRepository: UserRepository,
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(
        movie: Movie,
        content: String,
        score: Float
    ): Review {
        var user = userRepository.getUser()

        if (user == null) {
            userRepository.saveUser(User())
            user = userRepository.getUser()
        }

        return reviewRepository.addReview(
            Review(
                userId = user!!.id,
                movieId = movie.id,
                content = content,
                score = score
            )
        )
    }
}
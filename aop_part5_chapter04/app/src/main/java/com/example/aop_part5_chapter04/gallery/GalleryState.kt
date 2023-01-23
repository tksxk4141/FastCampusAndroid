package com.example.aop_part5_chapter04.gallery

import androidx.annotation.IdRes

sealed class GalleryState {

    object Uninitialized: GalleryState()

    object Loading: GalleryState()

    data class Success(
        val photoList: List<GalleryPhoto>,
        @IdRes val toastId: Int? = null
    ): GalleryState()

    data class Confirm(
        val photoList: List<GalleryPhoto>
    ): GalleryState()

}
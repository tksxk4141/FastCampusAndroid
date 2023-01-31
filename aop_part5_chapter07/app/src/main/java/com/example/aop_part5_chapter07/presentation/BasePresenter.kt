package com.example.aop_part5_chapter07.presentation

import androidx.annotation.CallSuper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

interface BasePresenter {

    val scope: CoroutineScope

    fun onViewCreated()

    fun onDestroyView()

    @CallSuper
    fun onDestroy(){
        scope.cancel()
    }
}
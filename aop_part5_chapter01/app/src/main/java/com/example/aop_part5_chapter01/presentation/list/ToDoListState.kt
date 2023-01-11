package com.example.aop_part5_chapter01.presentation.list

import com.example.aop_part5_chapter01.data.entity.ToDoEntity

sealed class ToDoListState {

    object UnInitialized: ToDoListState()

    object Loading: ToDoListState()

    data class Success(
        val toDoList: List<ToDoEntity>
    ): ToDoListState()

    object Error: ToDoListState()

}
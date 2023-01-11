package com.example.aop_part5_chapter01.domain.todo

import com.example.aop_part5_chapter01.data.entity.ToDoEntity
import com.example.aop_part5_chapter01.data.repository.ToDoRepository
import com.example.aop_part5_chapter01.domain.UseCase

internal class InsertToDoUseCase(
    private val  toDoRepository: ToDoRepository
): UseCase {

    suspend operator fun invoke(toDoItem: ToDoEntity): Long {
        return toDoRepository.insertToDoItem(toDoItem)
    }
}
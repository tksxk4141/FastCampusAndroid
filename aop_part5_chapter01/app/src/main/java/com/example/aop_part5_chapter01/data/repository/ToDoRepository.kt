package com.example.aop_part5_chapter01.data.repository

import com.example.aop_part5_chapter01.data.entity.ToDoEntity

interface ToDoRepository {

    suspend fun getToDoList(): List<ToDoEntity>

    suspend fun insertToDoItem(toDoItem: ToDoEntity): Long

    suspend fun insertToDoList(todoList: List<ToDoEntity>)

    suspend fun updateToDoItem(toDoItem: ToDoEntity)

    suspend fun getToDoItem(itemId: Long): ToDoEntity?

    suspend fun deleteAll()

    suspend fun deleteToDoItem(id: Long)
}
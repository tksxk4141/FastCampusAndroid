package com.example.aop_part5_chapter01.di

import android.content.Context
import androidx.room.Room
import com.example.aop_part5_chapter01.data.local.db.ToDoDatabase
import com.example.aop_part5_chapter01.data.repository.DefaultToDoRepository
import com.example.aop_part5_chapter01.data.repository.ToDoRepository
import com.example.aop_part5_chapter01.domain.todo.DeleteAllToDoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.DeleteToDoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.GetToDoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.GetToDoListUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertToDoListUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertToDoUseCase
import com.example.aop_part5_chapter01.domain.todo.UpdateToDoUseCase
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import com.example.aop_part5_chapter01.presentation.detail.DetailViewModel
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {

    single { Dispatchers.Main }
    single { Dispatchers.IO }

    //viewModel
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }

    //useCase
    factory { GetToDoItemUseCase(get()) }
    factory { GetToDoListUseCase(get()) }
    factory { InsertToDoListUseCase(get()) }
    factory { InsertToDoUseCase(get()) }
    factory { UpdateToDoUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }

    //repository
    single<ToDoRepository> { DefaultToDoRepository(get(), get()) }

    single { provideDB(androidApplication()) }
    single { provideToDoDao(get()) }
}

internal fun provideDB(context: Context): ToDoDatabase =
    Room.databaseBuilder(context, ToDoDatabase::class.java, ToDoDatabase.DB_NAME).build()

internal fun provideToDoDao(database: ToDoDatabase) = database.toDoDao()
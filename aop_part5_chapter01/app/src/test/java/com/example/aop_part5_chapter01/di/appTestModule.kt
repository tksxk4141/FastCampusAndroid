package com.example.aop_part5_chapter01.di

import com.example.aop_part5_chapter01.data.repository.TestToDoRepository
import com.example.aop_part5_chapter01.data.repository.ToDoRepository
import com.example.aop_part5_chapter01.domain.todo.*
import com.example.aop_part5_chapter01.presentation.detail.DetailMode
import com.example.aop_part5_chapter01.presentation.detail.DetailViewModel
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appTestModule = module {

    //viewModel
    viewModel { ListViewModel(get(), get(), get()) }
    viewModel { (detailMode: DetailMode, id: Long) -> DetailViewModel(detailMode, id, get(), get(), get(), get()) }

    //useCase
    factory { GetToDoItemUseCase(get()) }
    factory { GetToDoListUseCase(get())}
    factory { InsertToDoListUseCase(get())}
    factory { InsertToDoUseCase(get())}
    factory { UpdateToDoUseCase(get()) }
    factory { DeleteAllToDoItemUseCase(get()) }
    factory { DeleteToDoItemUseCase(get()) }

    //repository
    single<ToDoRepository> {TestToDoRepository()}
}
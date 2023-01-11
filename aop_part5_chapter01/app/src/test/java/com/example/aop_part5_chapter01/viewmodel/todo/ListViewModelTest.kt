package com.example.aop_part5_chapter01.viewmodel.todo

import com.example.aop_part5_chapter01.viewmodel.ViewModelTest
import com.example.aop_part5_chapter01.data.entity.ToDoEntity
import com.example.aop_part5_chapter01.domain.todo.GetToDoItemUseCase
import com.example.aop_part5_chapter01.domain.todo.InsertToDoListUseCase
import com.example.aop_part5_chapter01.presentation.list.ListViewModel
import com.example.aop_part5_chapter01.presentation.list.ToDoListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
internal class ListViewModelTest: ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()
    private val getToDoItemUseCase: GetToDoItemUseCase by inject()

    private val mockList = (0 until 10).map{
        ToDoEntity(
            id= it.toLong(),
            title= "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    @Before
    fun init(){
        initData()
    }

    private fun initData() = runTest {
        insertToDoListUseCase(mockList)
    }

    // Test : 입력된 데이터를 불러와서 검증
    @Test
    fun `test viewModel fetch`(): Unit = runTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(mockList)
            )
        )
    }

    @Test
    fun `test Item update`(): Unit = runTest {
        val todo = ToDoEntity(
            id = 1,
            title = "title 1",
            description = "description 1",
            hasCompleted = true
        )
        viewModel.updateEntity(todo)
        assert((getToDoItemUseCase(1)?.hasCompleted ?: false) == todo.hasCompleted)
    }

    @Test
    fun `test Item Delete All`(): Unit = runTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.deleteAll()
        testObservable.assertValueSequence(
            listOf(
                ToDoListState.UnInitialized,
                ToDoListState.Loading,
                ToDoListState.Success(listOf())
            )
        )
    }
}
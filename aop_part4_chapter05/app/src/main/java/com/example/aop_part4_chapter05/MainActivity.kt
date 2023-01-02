package com.example.aop_part4_chapter05

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import com.example.aop_part4_chapter05.data.database.DatabaseProvider
import com.example.aop_part4_chapter05.data.entity.GithubOwner
import com.example.aop_part4_chapter05.data.entity.GithubRepoEntity
import com.example.aop_part4_chapter05.databinding.ActivityMainBinding
import com.example.aop_part4_chapter05.view.RepositoryRecyclerAdapter
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RepositoryRecyclerAdapter

    private val repositoryDao by lazy {DatabaseProvider.provideDB(applicationContext).searchHistoryDao()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()

//        launch {
//            addMockData()
//            val githubRepositories = loadGithubRepositories()
//            withContext(coroutineContext){
//                Log.e("repositories", githubRepositories.toString())
//            }
//        }
    }

    private fun initAdapter() {
        adapter = RepositoryRecyclerAdapter()
    }

    private fun initViews() = with(binding){
        recyclerView.adapter = adapter
        searchButton.setOnClickListener {
            startActivity(
                Intent(this@MainActivity, SearchActivity::class.java)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        launch(coroutineContext) {
            loadSearchHistory()
        }
    }

    private suspend fun loadSearchHistory() = withContext(Dispatchers.IO) {
        val histories = DatabaseProvider.provideDB(this@MainActivity).searchHistoryDao().getHistory()
        withContext(Dispatchers.Main) {
            setData(histories)
        }
    }

    private fun setData(githubRepoList: List<GithubRepoEntity>) = with(binding) {
        if (githubRepoList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setSearchResultList(githubRepoList) {
                startActivity(
                    Intent(this@MainActivity, RepositoryActivity::class.java).apply {
                        putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                        putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                    }
                )
            }
        }
    }

    private suspend fun addMockData() = with(Dispatchers.IO) {
        val mockData = (0 until 10).map{
            GithubRepoEntity(
                name = "repo $it",
                fullName = "name $it",
                owner = GithubOwner(
                    "login",
                    "avatarUrl"
                ),
                description = null,
                language = null,
                updatedAt = Date().toString(),
                stargazersCount = it
            )
        }
        repositoryDao.insertAll(mockData)
    }

    private suspend fun loadGithubRepositories() = withContext(Dispatchers.IO) {
        return@withContext repositoryDao.getHistory()
    }
}
package com.example.aop_part3_chapter04

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aop_part3_chapter04.adapter.BookAdapter
import com.example.aop_part3_chapter04.adapter.HistoryAdapter
import com.example.aop_part3_chapter04.api.BookService
import com.example.aop_part3_chapter04.databinding.ActivityMainBinding
import com.example.aop_part3_chapter04.model.History
import com.example.aop_part3_chapter04.model.SearchBooksDto
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var bookService : BookService

    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = getAppDatabase(this)

        initBookRecyclerView()
        initHistoryRecyclerView()
        initSearchEditText()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)
        bookService.getBooksByName(getString(R.string.naverAPI_ID),getString(R.string.naverAPI_secretkey),getString(R.string.default_keyword))
            .enqueue(object :Callback<SearchBooksDto>{
                override fun onResponse(
                    call: Call<SearchBooksDto>,
                    response: Response<SearchBooksDto>
                ) {
                    if(response.isSuccessful.not())
                        return
                    response.body()?.let{
                        adapter.submitList(it.books)
                    }
                }
                override fun onFailure(call: Call<SearchBooksDto>, t: Throwable) {
                }
            })
    }

    private fun initBookRecyclerView(){
        adapter = BookAdapter(itemClickedListener = {
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra("bookModel", it)
            startActivity(intent)
        })
        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }
    private fun initHistoryRecyclerView(){
        historyAdapter = HistoryAdapter(historyDeleteClickedListener = {
            deleteSearchKeyword(it)
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
    }
    private fun initSearchEditText(){
        binding.searchEditText.setOnKeyListener{ v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action==MotionEvent.ACTION_DOWN){
                val imm: InputMethodManager = getSystemService(
                    INPUT_METHOD_SERVICE
                ) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0) //hide keyboard

                hideHistoryView()
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        binding.searchEditText.setOnTouchListener{ v, event->
            if(event.action == MotionEvent.ACTION_DOWN){
                showHistoryView()
            }
            return@setOnTouchListener false
        }
    }
    private fun search(keyword: String){
        bookService.getBooksByName(getString(R.string.naverAPI_ID),getString(R.string.naverAPI_secretkey),keyword)
            .enqueue(object :Callback<SearchBooksDto>{
                override fun onResponse(
                    call: Call<SearchBooksDto>,
                    response: Response<SearchBooksDto>
                ) {
                    saveSearchKeyword(keyword)
                    if(response.isSuccessful.not()){
                        return
                    }
                    adapter.submitList(response.body()?.books.orEmpty())
                }
                override fun onFailure(call: Call<SearchBooksDto>, t: Throwable) {
                }
            })
    }

    private fun showHistoryView(){
        Thread{
            val keywords = db.historyDao().getAll().reversed()
            runOnUiThread{
                binding.historyRecyclerView.isVisible = true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
    }
    private fun hideHistoryView(){
        binding.historyRecyclerView.isVisible = false
    }

    private fun saveSearchKeyword(keyword: String){
        Thread{
            db.historyDao().insertHistory(History(null, keyword))
        }.start()
    }
    private fun deleteSearchKeyword(keyword: String){
        Thread{
            db.historyDao().delete(keyword)
            showHistoryView()
        }.start()
    }

    companion object{
        private const val TAG = "MainActivity"
        private const val BASE_URL = "https://openapi.naver.com/"
    }
}
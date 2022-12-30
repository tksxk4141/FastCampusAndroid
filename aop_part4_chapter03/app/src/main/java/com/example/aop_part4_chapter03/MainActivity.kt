package com.example.aop_part4_chapter03

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import com.example.aop_part4_chapter03.MapActivity.Companion.SEARCH_RESULT_EXTRA_KEY
import com.example.aop_part4_chapter03.databinding.ActivityMainBinding
import com.example.aop_part4_chapter03.model.LocationLatLngEntity
import com.example.aop_part4_chapter03.model.SearchResultEntity
import com.example.aop_part4_chapter03.response.search.Poi
import com.example.aop_part4_chapter03.response.search.Pois
import com.example.aop_part4_chapter03.utility.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        initAdapter()
        initViews()
        bindViews()
        initData()
    }
    private fun initAdapter()  {
        adapter = SearchRecyclerAdapter()
    }
    private fun initViews() = with(binding){
        emptyResultTextview.isVisible = false
        recyclerView.adapter = adapter
    }
    private fun bindViews() = with(binding){
        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }

    private fun initData(){
        adapter.notifyDataSetChanged()
    }
    private fun setData(pois: Pois) {
        val dataList = pois.poi.map{
            SearchResultEntity(
                name = it.name ?:"",
                fullAddress = makeMainAdress(it),
                locationLatLng = LocationLatLngEntity(
                    it.noorLat,
                    it.noorLon,
                )
            )
        }
        adapter.setSearchResultList(dataList){
            startActivity(
                Intent(this, MapActivity::class.java).apply {
                    putExtra(SEARCH_RESULT_EXTRA_KEY, it)
                }
            )
        }
    }
    private fun searchKeyword(keywordString: String) {
        launch(coroutineContext) {
            try{
                withContext(Dispatchers.IO){
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keywordString
                    )
                    if(response.isSuccessful){
                        val body = response.body()
                        withContext(Dispatchers.Main){
                            Log.d("response", body.toString())
                            body?.let{ searchResponse ->
                                setData(searchResponse.searchPoiInfo.pois)
                            }
                        }
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
    private fun makeMainAdress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }
}
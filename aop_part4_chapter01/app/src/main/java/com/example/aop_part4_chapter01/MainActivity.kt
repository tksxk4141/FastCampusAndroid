package com.example.aop_part4_chapter01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aop_part4_chapter01.adapter.VideoAdapter
import com.example.aop_part4_chapter01.dto.VideoDto
import com.example.aop_part4_chapter01.service.VideoService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PlayerFragment())
            .commit()

        videoAdapter = VideoAdapter(callback = { url, title ->
            supportFragmentManager.fragments.find{it is PlayerFragment}?.let{
                (it as PlayerFragment).play(url, title)
            }
        })

        findViewById<RecyclerView>(R.id.mainRecyclerView).apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        getVideoList()
    }

    private fun getVideoList(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also {
            it.listVideos()
                .enqueue(object : Callback<VideoDto>{
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                     if(response.isSuccessful.not()){
                         return
                     }
                        response.body()?.let{ videoDto ->
                            videoAdapter.submitList(videoDto.videos)
                        }
                    }
                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                        return
                    }
                })
        }
    }
}
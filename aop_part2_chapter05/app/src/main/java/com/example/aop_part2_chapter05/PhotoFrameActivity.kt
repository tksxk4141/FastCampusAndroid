package com.example.aop_part2_chapter05

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoImageView : ImageView by lazy {
        findViewById<ImageView>(R.id.photoImageView)
    }
    private val bgPhotoImageView : ImageView by lazy {
        findViewById<ImageView>(R.id.bgPhotoImageView)
    }

    private val photoList = mutableListOf<Uri>()
    private var cnt = 0
    private var timer: Timer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)

        getPhotoUriFromIntent()
    }

    private fun getPhotoUriFromIntent(){
        val size = intent.getIntExtra("photoListSize",0)

        for(i in 0..size){
            intent.getStringExtra("photo$i")?.let{
                photoList.add(Uri.parse(it))
            }
        }
    }
    private fun startTimer(){
        timer = timer(period=5*1000){
            runOnUiThread{
                val current = cnt
                val next = if(photoList.size<=cnt+1) 0 else cnt+1

                bgPhotoImageView.setImageURI(photoList[current])
                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView.animate()
                    .alpha(1.0f)
                    .setDuration(1000)
                    .start()

                cnt = next
            }
        }
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
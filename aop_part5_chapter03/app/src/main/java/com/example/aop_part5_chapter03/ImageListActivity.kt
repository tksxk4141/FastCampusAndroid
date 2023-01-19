package com.example.aop_part5_chapter03

import android.app.Activity
import android.content.Intent
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.aop_part5_chapter03.adapter.ImageViewPagerAdapter
import com.example.aop_part5_chapter03.databinding.ActivityImageListBinding
import com.example.aop_part5_chapter03.util.PathUtil
import java.io.File
import java.io.FileNotFoundException

class ImageListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageListBinding
    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter

    private val uriList by lazy<List<Uri>> { intent.getParcelableArrayListExtra(URI_LIST_KEY)!! }
    private var currentUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        setupImageList()
    }

        private fun setupImageList() = with(binding){
        if(::imageViewPagerAdapter.isInitialized.not()){
            imageViewPagerAdapter = ImageViewPagerAdapter(uriList as MutableList<Uri>)
        }
        imageViewPager.adapter = imageViewPagerAdapter
        indicator.setViewPager(imageViewPager)
        imageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                toolbar.title = getString(R.string.images_page, position +1, imageViewPagerAdapter.uriList.size)
            }
        })
        deleteButton.setOnClickListener {
            removeImage(uriList[imageViewPager.currentItem])
        }
    }
    private fun removeImage(uri: Uri) {
        try{
            val file = File(PathUtil.getPath(this, uri) ?: throw FileNotFoundException())
            file.delete()
            imageViewPagerAdapter.uriList?.let{
                val imageList = it.toMutableList()
                imageList.remove(uri)
                imageViewPagerAdapter.uriList = imageList
                imageViewPagerAdapter.notifyDataSetChanged()
            }
            MediaScannerConnection.scanFile(this, arrayOf(file.path), arrayOf("image/jpeg"), null)
            binding.indicator.setViewPager(binding.imageViewPager)
            if(imageViewPagerAdapter.uriList.isEmpty()){
                finish()
            }
        }catch (e: FileNotFoundException){
            e.printStackTrace()
            Toast.makeText(this, "이미지가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private const val URI_LIST_KEY = "uriList"

        const val IMAGE_LIST_REQUEST_CODE = 100

        fun newIntent(activity: Activity, uriList: List<Uri>) =
            Intent(activity, ImageListActivity::class.java).apply {
                putExtra(URI_LIST_KEY, ArrayList<Uri>().apply { uriList.forEach { add(it) } })
            }
    }
}
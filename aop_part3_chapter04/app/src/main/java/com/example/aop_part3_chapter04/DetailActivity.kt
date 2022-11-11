package com.example.aop_part3_chapter04

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.aop_part3_chapter04.databinding.ActivityDetailBinding
import com.example.aop_part3_chapter04.model.Book
import com.example.aop_part3_chapter04.model.Review

class DetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailBinding

    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = getAppDatabase(this)

        val model = intent.getParcelableExtra<Book>("bookModel")

        binding.titleTextView.text = model?.title.orEmpty()

        Glide
            .with(binding.coverImageView.context)
            .load(model?.coverSmallUrl)
            .into(binding.coverImageView)

        binding.descriptionTextView.text = model?.description.orEmpty()

        Thread{
            val review = db.reviewDao().getOneReview(model?.id.orEmpty())
            runOnUiThread{
                binding.reviewEditText.setText(review?.review.orEmpty())
            }
        }.start()

        binding.saveButton.setOnClickListener{
            Thread{
                db.reviewDao().saveReview(
                    Review(
                        model?.id.orEmpty(),
                        binding.reviewEditText.text.toString()
                    )
                )
            }.start()
        }

    }
}
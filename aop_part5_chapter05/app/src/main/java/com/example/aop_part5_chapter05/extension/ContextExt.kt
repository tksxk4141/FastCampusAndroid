package com.example.aop_part5_chapter05.extension

import android.content.Context
import androidx.annotation.Px

@Px
fun Context.dip(dipValue: Float) = (dipValue * resources.displayMetrics.density).toInt()
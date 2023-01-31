package com.example.aop_part5_chapter07.extension

import java.text.DecimalFormat

fun Float.toDecimalFormatString(format: String): String = DecimalFormat(format).format(this)
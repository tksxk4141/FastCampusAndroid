package com.example.aop_part5_chapter07.data.preference

interface PreferenceManager {

    fun getString(key: String): String?

    fun putString(key: String, value: String)
}
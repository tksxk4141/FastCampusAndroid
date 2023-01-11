package com.example.aop_part5_chapter01

import android.app.Application
import com.example.aop_part5_chapter01.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApoPart5Chapter01Application: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ApoPart5Chapter01Application)
            modules(appModule)
        }
    }

}
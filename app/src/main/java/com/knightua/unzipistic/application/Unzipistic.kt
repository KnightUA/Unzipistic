package com.knightua.unzipistic.application

import android.app.Application
import android.content.Context
import timber.log.Timber

class Unzipistic : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
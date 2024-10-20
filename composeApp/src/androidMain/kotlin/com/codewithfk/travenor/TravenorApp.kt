package com.codewithfk.travenor

import android.app.Application
import com.codewithfk.travenor.di.initKoin
import org.koin.android.ext.koin.androidContext

class TravenorApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin{
            androidContext(this@TravenorApp)
        }
    }

}

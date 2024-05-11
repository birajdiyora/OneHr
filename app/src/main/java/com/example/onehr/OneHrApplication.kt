package com.example.onehr

import android.app.Application
import android.content.Context
import com.example.onehr.common.app
import com.example.onehr.common.appContext
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class OneHrApplication : Application(){
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        app = this
        appContext = base
    }
}
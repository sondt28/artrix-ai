package com.son.myapplication

import android.app.Application
import android.content.Context
import com.son.myapplication.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    companion object {
        private var instance: App? = null

        fun getInstance(): App {
            if (instance == null) {
                instance = App()
            }
            return instance!!
        }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }
    }

    fun getAppContext(): Context {
        return applicationContext
    }
}
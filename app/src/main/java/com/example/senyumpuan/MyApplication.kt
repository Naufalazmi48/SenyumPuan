package com.example.senyumpuan

import android.app.Application
import com.example.core.di.firebaseModule
import com.example.core.di.repositoryModule
import com.example.senyumpuan.di.useCaseModule
import com.example.senyumpuan.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(firebaseModule)
            modules(repositoryModule)
            modules(useCaseModule)
            modules(viewModelModule)
        }
    }
}
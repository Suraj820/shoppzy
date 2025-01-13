package com.shoppzy

import android.app.Application
import com.shoppzy.data.di.dataModule
import com.shoppzy.di.presentationModule
import com.shoppzy.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShoppzyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShoppzyApp)
            modules(listOf(
                presentationModule,
                dataModule,
                domainModule
            ))
        }
    }
}
package com.erhodes.arsmagica

import android.app.Application
import com.erhodes.arsmagica.dagger.AppComponent
import com.erhodes.arsmagica.dagger.AppModule
import com.erhodes.arsmagica.dagger.DaggerAppComponent

class ArsMagicaApplication : Application() {
    lateinit var component: AppComponent

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

        instance = this
    }

    companion object {
        lateinit var instance: ArsMagicaApplication private set
    }
}
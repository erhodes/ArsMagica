package com.erhodes.arsmagica.dagger

import android.app.Application
import androidx.room.Room
import com.erhodes.arsmagica.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {
    @Provides
    @Singleton
    fun provideApplication() = application

    @Provides
    fun provideDatabase() = Room.databaseBuilder(application, AppDatabase::class.java, "database.db").build()
}
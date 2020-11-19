package com.example.mobilityapp.injection

import android.content.Context
import com.example.mobilityapp.MobilityApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: MobilityApp) {

    @Provides
    @Singleton
    fun context(): Context = app.applicationContext

}
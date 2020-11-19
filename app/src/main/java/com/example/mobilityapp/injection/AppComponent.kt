package com.example.mobilityapp.injection

import com.example.mobilityapp.MainActivity
import com.example.mobilityapp.screen.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        ApiRepositoryModule::class,
        NetworkConnectionModule::class,
        UseCaseModule::class
    ]
)
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}
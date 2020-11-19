package com.example.mobilityapp.injection

import com.example.mobilityapp.client.RetrofitApiClientImplementation
import com.example.mobilityapp.repository.ApiRepository
import dagger.Module
import dagger.Provides

@Module
class ApiRepositoryModule {

    @Provides
    fun apiRepository(retrofitApiClientImplementation: RetrofitApiClientImplementation): ApiRepository =
        ApiRepository(retrofitApiClientImplementation)
}
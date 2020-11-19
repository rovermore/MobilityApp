package com.example.mobilityapp.injection

import com.example.mobilityapp.repository.ApiRepository
import com.example.mobilityapp.usecase.TransportListUseCase

import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getTransportListUseCase(repository: ApiRepository): TransportListUseCase =
        TransportListUseCase(repository)
}
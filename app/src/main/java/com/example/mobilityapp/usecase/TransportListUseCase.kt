package com.example.mobilityapp.usecase

import com.example.mobilityapp.model.Transport
import com.example.mobilityapp.repository.ApiRepository
import javax.inject.Inject

class TransportListUseCase
    @Inject constructor(private val repository: ApiRepository): UseCase {

    override suspend fun request(): List<Transport>? {
        return repository.getResponse()
    }
}
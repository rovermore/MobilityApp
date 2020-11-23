package com.example.mobilityapp.usecase

import com.example.mobilityapp.model.FrameMap
import com.example.mobilityapp.model.Transport
import com.example.mobilityapp.repository.ApiRepository
import javax.inject.Inject

class TransportListUseCase
    @Inject constructor(private val repository: ApiRepository): UseCaseWithParameters {

    override suspend fun requestWithParameter(parameter: Any): List<Transport>? {
        return repository.getResponse(parameter as FrameMap)
    }
}
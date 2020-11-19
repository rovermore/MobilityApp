package com.example.mobilityapp.repository

import com.example.mobilityapp.client.RetrofitApiClientImplementation
import com.example.mobilityapp.model.Transport
import javax.inject.Inject

class ApiRepository
    @Inject constructor(private val api: RetrofitApiClientImplementation): Repository {

    override suspend fun getResponse(): List<Transport>? {
        return api.getResponse()

    }
}
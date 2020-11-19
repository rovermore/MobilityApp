package com.example.mobilityapp.client

import com.example.mobilityapp.model.Transport
import javax.inject.Inject

class RetrofitApiClientImplementation
    @Inject constructor(private val retrofitApiClient: RetrofitApiClient):
    RetrofitApiClient {

    override suspend fun getResponse(): List<Transport>? {
        return retrofitApiClient.getResponse()
    }

}
package com.example.mobilityapp.client

import com.example.mobilityapp.model.Transport
import com.google.android.gms.maps.model.LatLng
import javax.inject.Inject

class RetrofitApiClientImplementation
    @Inject constructor(private val retrofitApiClient: RetrofitApiClient):
    RetrofitApiClient {

    override suspend fun getResponse(
        lowerLeftLatLon: String,
        upperRightLatLon: String
    ): List<Transport>? {
        return retrofitApiClient.getResponse(lowerLeftLatLon, upperRightLatLon)
    }

}
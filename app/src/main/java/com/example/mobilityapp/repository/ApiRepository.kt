package com.example.mobilityapp.repository

import com.example.mobilityapp.client.RetrofitApiClientImplementation
import com.example.mobilityapp.model.FrameMap
import com.example.mobilityapp.model.Transport
import javax.inject.Inject

class ApiRepository
    @Inject constructor(private val api: RetrofitApiClientImplementation): Repository {

    override suspend fun getResponse(frameMap: FrameMap): List<Transport>? {
        val lowerLeftLatLng = frameMap.lowerLeftLatLng.latitude.toString().plus(",").plus(frameMap.lowerLeftLatLng.longitude.toString())
        val upperRightLatLng = frameMap.upperRightLatLng.latitude.toString().plus(",").plus(frameMap.upperRightLatLng.longitude.toString())
        return api.getResponse(lowerLeftLatLng, upperRightLatLng)
    }
}
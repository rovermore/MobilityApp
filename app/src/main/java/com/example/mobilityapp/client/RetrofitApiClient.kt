package com.example.mobilityapp.client

import com.example.mobilityapp.model.Transport
import retrofit2.http.GET

interface RetrofitApiClient {

    @GET("resources?lowerLeftLatLon=38.711046,-9.160096&upperRightLatLon=38.739429,-9.137115")
    suspend fun getResponse()
            : List<Transport>?

}
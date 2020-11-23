package com.example.mobilityapp.client

import com.example.mobilityapp.model.Transport
import com.google.android.gms.maps.model.LatLng
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiClient {

    @GET("resources")
    suspend fun getResponse(
        @Query("lowerLeftLatLon") lowerLeftLatLon: String,
        @Query("upperRightLatLon") upperRightLatLon: String
    )
            : List<Transport>?

}
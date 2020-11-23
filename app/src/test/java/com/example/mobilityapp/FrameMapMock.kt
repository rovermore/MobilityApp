package com.example.mobilityapp

import com.example.mobilityapp.model.FrameMap
import com.google.android.gms.maps.model.LatLng

object FrameMapMock {
    val northEast = LatLng(38.739429,-9.137115)
    val southWest = LatLng(38.711046,-9.160096)
    val frameMap = FrameMap(southWest, northEast)

    val lowerLeftLatLon = "38.711046,-9.160096"
    val upperRightLatLon = "38.739429,-9.137115"
}
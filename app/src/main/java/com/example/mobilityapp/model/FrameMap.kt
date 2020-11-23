package com.example.mobilityapp.model

import com.google.android.gms.maps.model.LatLng

data class FrameMap(
    val lowerLeftLatLng : LatLng,
    val upperRightLatLng : LatLng
) {
}
package com.example.mobilityapp.model

import com.google.android.gms.maps.model.LatLng

data class FrameMap(
    var lowerLeftLatLng : LatLng,
    var upperRightLatLng : LatLng
) {
}
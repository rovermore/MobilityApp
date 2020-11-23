package com.example.mobilityapp.repository

import com.example.mobilityapp.model.FrameMap
import com.example.mobilityapp.model.Transport

interface Repository {

    suspend fun getResponse(frameMap: FrameMap): List<Transport>?

}
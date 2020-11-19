package com.example.mobilityapp.repository

import com.example.mobilityapp.model.Transport

interface Repository {

    suspend fun getResponse(): List<Transport>?

}
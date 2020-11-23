package com.example.mobilityapp.usecase

interface UseCaseWithParameters {
    suspend fun requestWithParameter(parameter: Any): Any?

}
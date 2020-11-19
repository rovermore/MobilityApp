package com.example.mobilityapp.usecase

interface UseCase {

    suspend fun request(): Any?

}
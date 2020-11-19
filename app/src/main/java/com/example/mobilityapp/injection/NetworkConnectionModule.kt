package com.example.mobilityapp.injection

import android.content.Context
import com.example.mobilityapp.utils.NetworkConnection
import dagger.Module
import dagger.Provides

@Module
class NetworkConnectionModule {

    @Provides
    fun getNetworkConnection(context: Context): NetworkConnection =
        NetworkConnection(context)
}
package com.example.mobilityapp

import com.example.mobilityapp.model.Transport

object TransportListMock {
    val transportList = listOf<Transport>(
        Transport("100", "Taxi",  -9.14242, 38.71702, 412),
        Transport("100", "Metro",  -9.14244, 38.71709, 378),
        Transport("100", "Taxi",  -9.14249, 38.71704, 473),
        Transport("100", "Taxi",  -9.14247, 38.71701, 402)
    )
    val nullTransportList : List<Transport>? = null
}
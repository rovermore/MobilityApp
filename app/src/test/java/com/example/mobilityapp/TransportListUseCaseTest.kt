package com.example.mobilityapp

import com.example.mobilityapp.repository.ApiRepository
import com.example.mobilityapp.usecase.TransportListUseCase
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TransportListUseCaseTest {

    lateinit var apiRepository: ApiRepository
    lateinit var transportListUseCase: TransportListUseCase

    private val transportListResponse = TransportListMock.transportList

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        apiRepository = Mockito.mock(ApiRepository::class.java)
        whenever(apiRepository.getResponse()).thenReturn(this@TransportListUseCaseTest.transportListResponse)
        transportListUseCase = TransportListUseCase(apiRepository)
    }


    @Test
    fun `if ApiRepository return transportLIst then TransportListUseCase returns same list`() = runBlockingTest {
        val transportListFromClientImpl = transportListUseCase.request()
        Assert.assertEquals(transportListFromClientImpl, this@TransportListUseCaseTest.transportListResponse)
        Assert.assertEquals(
            transportListFromClientImpl?.get(0)?.id,
            this@TransportListUseCaseTest.transportListResponse[0].id
        )
        Assert.assertEquals(
            transportListFromClientImpl?.get(0)?.name,
            this@TransportListUseCaseTest.transportListResponse[0].name
        )
        Assert.assertEquals(
            transportListFromClientImpl?.get(0)?.x,
            this@TransportListUseCaseTest.transportListResponse[0].x
        )
        Assert.assertEquals(
            transportListFromClientImpl?.get(0)?.y,
            this@TransportListUseCaseTest.transportListResponse[0].y
        )
        Assert.assertEquals(
            transportListFromClientImpl?.get(0)?.companyZoneId,
            this@TransportListUseCaseTest.transportListResponse[0].companyZoneId
        )
    }
}
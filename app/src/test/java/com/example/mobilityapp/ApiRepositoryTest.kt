package com.example.mobilityapp

import com.example.mobilityapp.client.RetrofitApiClientImplementation
import com.example.mobilityapp.model.FrameMap
import com.example.mobilityapp.repository.ApiRepository
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ApiRepositoryTest {

    lateinit var retrofitApiClientImplementation: RetrofitApiClientImplementation
    lateinit var apiRepository: ApiRepository

    var frameMap: FrameMap = FrameMapMock.frameMap

    private val transportListResponse = TransportListMock.transportList

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        retrofitApiClientImplementation = Mockito.mock(RetrofitApiClientImplementation::class.java)
        whenever(retrofitApiClientImplementation.getResponse(FrameMapMock.lowerLeftLatLon, FrameMapMock.upperRightLatLon)).thenReturn(this@ApiRepositoryTest.transportListResponse)
        apiRepository = ApiRepository(retrofitApiClientImplementation)
    }


    @Test
    fun `if RetrofitApiClientImplementation return transportList then ApiRepository calls getResponse method returns same transportList`() = runBlockingTest {
        val transportListFromClientImpl = apiRepository.getResponse(frameMap)
        Assert.assertEquals(transportListFromClientImpl, this@ApiRepositoryTest.transportListResponse)
        assertEquals(transportListFromClientImpl?.get(0)?.id, this@ApiRepositoryTest.transportListResponse[0].id)
        assertEquals(transportListFromClientImpl?.get(0)?.name, this@ApiRepositoryTest.transportListResponse[0].name)
        assertEquals(transportListFromClientImpl?.get(0)?.x, this@ApiRepositoryTest.transportListResponse[0].x)
        assertEquals(transportListFromClientImpl?.get(0)?.y, this@ApiRepositoryTest.transportListResponse[0].y)
        assertEquals(transportListFromClientImpl?.get(0)?.companyZoneId, this@ApiRepositoryTest.transportListResponse[0].companyZoneId)

    }

}
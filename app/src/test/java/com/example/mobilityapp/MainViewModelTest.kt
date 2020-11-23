package com.example.mobilityapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobilityapp.screen.main.MainViewModel
import com.example.mobilityapp.usecase.TransportListUseCase
import com.example.mobilityapp.utils.NetworkConnection
import com.example.mobilityapp.utils.ScreenState
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var transportListUseCase: TransportListUseCase
    lateinit var mainViewModel: MainViewModel
    lateinit var networkConnection: NetworkConnection
    private var frameMap = FrameMapMock.frameMap

    private val mockedTransportList = TransportListMock.transportList
    private val nullTransportList= TransportListMock.nullTransportList

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        transportListUseCase = Mockito.mock(TransportListUseCase::class.java)
        networkConnection = Mockito.mock(NetworkConnection::class.java)
        whenever(transportListUseCase.requestWithParameter(frameMap)).thenReturn(mockedTransportList)
        whenever(networkConnection.isNetworkConnected()).thenReturn(true)
        mainViewModel = MainViewModel(networkConnection,transportListUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `checks uiModel is null when transportList request is null`() = runBlocking {
        whenever(transportListUseCase.requestWithParameter(frameMap)).thenReturn(nullTransportList)
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiModel.value).isNull()
    }

    @Test
    fun `checks uiModel is not empty when transportList request is not null`() = runBlocking {
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiModel.value).isNotNull()
    }

    @Test
    fun `checks uiState is Error when transportList request is null`() = runBlocking {
        whenever(transportListUseCase.requestWithParameter(frameMap)).thenReturn(nullTransportList)
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiState.value).isEqualTo(ScreenState.Error)
    }

    @Test
    fun `checks uiState is Success when transportList request is not null`() = runBlocking {
        mainViewModel.initialize()
        Truth.assertThat(mainViewModel.uiState.value).isEqualTo(ScreenState.Success)
    }
}
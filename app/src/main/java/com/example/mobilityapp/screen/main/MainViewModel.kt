package com.example.mobilityapp.screen.main


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mobilityapp.model.FrameMap
import com.example.mobilityapp.model.Transport
import com.example.mobilityapp.usecase.TransportListUseCase
import com.example.mobilityapp.utils.NetworkConnection
import com.example.mobilityapp.utils.ScreenState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainViewModel
@Inject constructor(private val networkConnection: NetworkConnection,
                    private val transportListUseCase: TransportListUseCase
) : ViewModel() {

    private val coroutineContext: CoroutineContext get() = Job() + Dispatchers.Main
    private val viewModelScope = CoroutineScope(coroutineContext)

    private val _uiModel =
        MutableLiveData<List<Transport>>()
    val uiModel: LiveData<List<Transport>> = _uiModel

    private val _uiState = MutableLiveData<ScreenState>()
    val uiState: LiveData<ScreenState> = _uiState

    private val lowerLeftLatLng = LatLng(38.711046,-9.160096)
    private val upperRightLatLng = LatLng(38.739429,-9.137115)
    private val frameMap = FrameMap(lowerLeftLatLng, upperRightLatLng)
    var borderRegion = FrameMap(lowerLeftLatLng, upperRightLatLng)


    fun initialize() {
        loadData(frameMap)
    }

    fun loadData(frameMap: FrameMap) {
        _uiState.value = ScreenState.Loading
        checkInternetConnection(frameMap)
    }

    private fun checkInternetConnection(frameMap: FrameMap) {
        if (networkConnection.isNetworkConnected())
            setupObservers(frameMap)
        else
            _uiState.setValue(ScreenState.Error)
    }

    private fun setupObservers(frameMap: FrameMap) {
        observeResponse(frameMap)

    }

    private fun observeResponse(frameMap: FrameMap) {
        viewModelScope.launch {
            val response = transportListUseCase.requestWithParameter(frameMap)
            response?.let {
                createAndPostUiModel(it)
            } ?: displayError()
        }
    }

    private fun displayError() {
        viewModelScope.launch {
            _uiState.setValue(ScreenState.Error)
        }
    }

    private fun createAndPostUiModel(response: List<Transport>) {
        viewModelScope.launch {
            _uiModel.setValue(response)
            _uiState.setValue(ScreenState.Success)
        }
    }

}
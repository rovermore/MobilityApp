package com.example.mobilityapp.screen.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mobilityapp.MobilityApp
import com.example.mobilityapp.R
import com.example.mobilityapp.model.FrameMap
import com.example.mobilityapp.model.Transport
import com.example.mobilityapp.model.getCompanyZoneIdList
import com.example.mobilityapp.utils.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


class MainFragment : Fragment(), OnMapReadyCallback {

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var hashMapOfColors = hashMapOf<Int, Int>()
    private var googleMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.initialize()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupObservers()
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    init { MobilityApp.daggerAppComponent().inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setupReloadButton()
    }

    private fun setupReloadButton() {
        reloadButton.setOnClickListener {
            mainViewModel.initialize()
        }
    }

    private fun setupObservers() {
        observeData()
        observeState()
    }

    private fun observeState() {
        mainViewModel.uiState.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
    }

    private fun observeData() {
        mainViewModel.uiModel.observe(viewLifecycleOwner, Observer {
            addMarkers(it.toMutableList())
        })
    }

    private fun setupUi() {
        setupMap()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.big_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        googleMap?.let {
            val isGoogleServicesAvailable = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext())
            if (isGoogleServicesAvailable == ConnectionResult.SUCCESS) {
                it.apply {
                    setMinZoomPreference(15.5f)
                    moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(38.725307, -9.151907),
                            16.0f
                        )
                    )
                    setOnCameraMoveListener {
                        loadMoreMarkers()
                    }
                    uiSettings.apply {
                        isZoomControlsEnabled = true
                        isZoomGesturesEnabled = true
                        isScrollGesturesEnabled = true
                        isTiltGesturesEnabled = true
                        isScrollGesturesEnabledDuringRotateOrZoom = true
                    }

                }
                big_map.visible()
            }
        }
    }

    private fun loadMoreMarkers() {
        val cameraPosition = googleMap?.cameraPosition?.target
        if (cameraPosition?.latitude!! < mainViewModel.borderRegion.lowerLeftLatLng.latitude
            || cameraPosition.latitude > mainViewModel.borderRegion.upperRightLatLng.latitude
            || cameraPosition.longitude < mainViewModel.borderRegion.lowerLeftLatLng.longitude
            || cameraPosition.longitude > mainViewModel.borderRegion.upperRightLatLng.longitude
        ){
            Log.v("FRAME: ", "OUT")
            googleMap?.apply {
                mainViewModel.loadData(
                    FrameMap(
                        projection.visibleRegion.latLngBounds.southwest,
                        projection.visibleRegion.latLngBounds.northeast
                    )
                )
                if (projection.visibleRegion.latLngBounds.southwest.longitude < mainViewModel.borderRegion.lowerLeftLatLng.longitude)
                    mainViewModel.borderRegion.lowerLeftLatLng = LatLng(mainViewModel.borderRegion.lowerLeftLatLng.latitude, projection.visibleRegion.latLngBounds.southwest.longitude)
                if(projection.visibleRegion.latLngBounds.southwest.latitude < mainViewModel.borderRegion.lowerLeftLatLng.latitude)
                    mainViewModel.borderRegion.lowerLeftLatLng = LatLng(projection.visibleRegion.latLngBounds.southwest.latitude, mainViewModel.borderRegion.lowerLeftLatLng.latitude)
                if(projection.visibleRegion.latLngBounds.northeast.longitude > mainViewModel.borderRegion.upperRightLatLng.longitude)
                    mainViewModel.borderRegion.upperRightLatLng = LatLng(mainViewModel.borderRegion.upperRightLatLng.latitude, projection.visibleRegion.latLngBounds.northeast.longitude)
                if(projection.visibleRegion.latLngBounds.northeast.latitude > mainViewModel.borderRegion.upperRightLatLng.latitude)
                    mainViewModel.borderRegion.upperRightLatLng = LatLng(projection.visibleRegion.latLngBounds.northeast.latitude, mainViewModel.borderRegion.upperRightLatLng.longitude)
            }
        } else {
            Log.v("FRAME: ", "IN")
        }
    }

    private fun addMarkers(newTransportList: MutableList<Transport>){
        val newHashMapOfColors = CompanyZoneColor(newTransportList.getCompanyZoneIdList()).getHashMapOfColors()
        for (key in newHashMapOfColors.keys){
            if (!hashMapOfColors.containsKey(key))
                newHashMapOfColors[key]?.let { hashMapOfColors.put(key, it) }
        }
        for (transport in newTransportList) {
            val marker = googleMap?.addMarker(
                MarkerOptions().position(LatLng(transport.y, transport.x))
                    .title(transport.name)
            )
            marker?.tag = transport
            marker?.setIcon(IconCreator(hashMapOfColors, transport.companyZoneId, requireContext()).getIcon())
        }
    }

    private fun updateUI(screenState: ScreenState) {
        when (screenState) {
            ScreenState.Error -> {
                progressBar.gone()
                errorView.visible()
            }
            ScreenState.Loading -> {
                progressBar.visible()
                progressBar.bringToFront()
            }
            ScreenState.Success -> {
                progressBar.gone()
            }
        }
    }
}
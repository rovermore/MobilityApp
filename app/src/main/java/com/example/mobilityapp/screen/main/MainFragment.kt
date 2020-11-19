package com.example.mobilityapp.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.mobilityapp.MobilityApp
import com.example.mobilityapp.R
import com.example.mobilityapp.model.Transport
import com.example.mobilityapp.utils.ScreenState
import com.example.mobilityapp.utils.gone
import com.example.mobilityapp.utils.visible
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

    private var transportList = listOf<Transport>()

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
        setupUI()
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
        mainViewModel.uiState.observe(this, Observer {
            updateUI(it)
        })
    }

    private fun observeData() {
        mainViewModel.uiModel.observe(this, Observer {
            transportList = it
        })
    }

    private fun setupUI() {
        setupMap()
    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.big_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            if (transportList.isNotEmpty()) {
                for (transport in transportList) {
                    val transportLocation = LatLng(transport.y, transport.x)
                    val marker = googleMap.addMarker(
                        MarkerOptions().position(transportLocation)
                            .title(transport.name)
                    )
                    marker?.tag = transport
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            transportLocation,
                            10.0f
                        )
                    )
                }
                it.uiSettings.apply {
                    isZoomControlsEnabled = true
                    isZoomGesturesEnabled = true
                    isScrollGesturesEnabled = true
                    isTiltGesturesEnabled = true
                    isScrollGesturesEnabledDuringRotateOrZoom = true
                }
                //googleMap?.setOnInfoWindowClickListener(this)
            }
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
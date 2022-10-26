package com.example.map.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.map.R
import com.example.map.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


class MapsFragment : Fragment() {
    private val markers: ArrayList<Marker> = arrayListOf()
    lateinit var map: GoogleMap
    private var _binding: FragmentMapsBinding? = null
    private val binding: FragmentMapsBinding
        get() {
            return _binding!!
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val samara = LatLng(53.22, 50.19)
        googleMap.addMarker(MarkerOptions().position(samara).title("Marker in Samara"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(samara))
        map.setOnMapLongClickListener {
            addMarkerToArray(it)
            polyLine()
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(samara, 15f))


    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }


    private fun polyLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                PolylineOptions()
                    .add(previous, current)
                    .color(Color.BLACK)
                    .width(10f)
            )


        }


    }

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), R.drawable.img)
        markers.add(marker)


    }

    private fun setMarker(
        location: LatLng,
        searchText: String,
        resourceId: Int,
    ): Marker = map.addMarker(
        MarkerOptions()
            .position(location)
            .title(searchText)
            .icon(BitmapDescriptorFactory.fromResource(resourceId))

    )!!

    companion object {
        fun newInstance() = MapsFragment()

    }


}








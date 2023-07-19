package com.zida.wisata061.ui
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.zida.wisata061.R
import com.zida.wisata061.application.TourApp
import com.zida.wisata061.databinding.FragmentSecondBinding
import com.zida.wisata061.model.Tour


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener{

    private var _binding: FragmentSecondBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val tourViewModel: TourViewModel by viewModels {
        TourViewModelFactory((applicationContext as TourApp).repository)
    }
    private val args: SecondFragmentArgs by navArgs()
    private var tour: Tour? = null
    private lateinit var mMap:GoogleMap
    private var currentLatlang:LatLng?= null
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tour = args.tour

        if (tour !=null){
            binding.deletebutton.visibility = View.VISIBLE
            binding.saveButton.text ="Ubah"
            binding.nameEditText.setText(tour?.name)
            binding.addressEditText.setText(tour?.address)
            binding.streetEditText.setText(tour?.street)
        }


        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        checkPermission()


        val name = binding.nameEditText.text
        val address = binding.addressEditText.text
        val street = binding.streetEditText.text
        binding.saveButton.setOnClickListener {
            if (name.isEmpty()){
                Toast.makeText(context,"Nama Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
            }
            else if (address.isEmpty()){
                Toast.makeText(context,"Alamat Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
            }
            else if (street.isEmpty()){
                Toast.makeText(context,"Jalan Tidak Boleh Kosong",Toast.LENGTH_SHORT).show()
            }




           else {
                if (tour == null) {
                    val tour = Tour(0, name.toString(), address.toString(), street.toString(),currentLatlang?.latitude,currentLatlang?.longitude)
                    tourViewModel.insert(tour)
                } else {
                    val tour =
                        Tour(tour?.id!!, name.toString(), address.toString(), street.toString(),currentLatlang?.latitude,currentLatlang?.longitude)
                    tourViewModel.update(tour)

                }

                findNavController().popBackStack()
            }
        }
        binding.deletebutton.setOnClickListener {
            tour?.let {tourViewModel.delete(it) }
            findNavController().popBackStack()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerDragListener(this)

        val uiSettings = mMap.uiSettings
        uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerDragListener(this)
    }

    override fun onMarkerDrag(p0: Marker) {
    }

    override fun onMarkerDragEnd(marker : Marker) {
        val newposition = marker.position
        currentLatlang = LatLng(newposition.latitude, newposition.longitude)
        Toast.makeText(context,currentLatlang.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragStart(p0: Marker) {

    }
    private fun checkPermission(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            getCurrentLocation()
        } else{
            Toast.makeText(applicationContext,"Akses Lokasi ditolak", Toast.LENGTH_SHORT).show()
        }
    }
    private fun getCurrentLocation(){

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener {location ->
                if (location != null){
                    var latLang = LatLng(location.latitude, location.longitude)
                    currentLatlang = latLang
                    var title = "Marker"

                    if (tour != null) {
                        title = tour?.name.toString()
                        val newCurrentLocation = LatLng(tour?.latitude!!, tour?.longitude!!)
                        latLang = newCurrentLocation
                    }
                    val markerOptions = MarkerOptions()
                        .position(latLang)
                        .title(title)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.destination))
                    mMap.addMarker(markerOptions)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLang,15f))

                }
            }

    }
}
package com.example.androidtask.view.activity


import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.androidtask.R
import com.example.androidtask.databinding.ActivityMainBinding
import com.example.androidtask.view.adapter.PersonDetailsAdapter
import com.example.androidtask.viewModel.MainViewModel
import com.example.androidtask.viewModel.ViewmodelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class MainActivity : AppCompatActivity() {
    var viewModel: MainViewModel? = null
    lateinit var adapter: PersonDetailsAdapter
    lateinit var dataBinding: ActivityMainBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    var permissionId = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModelAndDataBinding()
        callApi()
        getResponse()
    }

    private fun initViewModelAndDataBinding() {
        viewModel = ViewModelProvider(this, ViewmodelFactory(this))[MainViewModel::class.java]
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }

    fun callApi() {
        viewModel?.getPersonDetails()
    }

    fun getResponse() {
        viewModel?.getPersonDetailResult()?.observe(this) {
            adapter = PersonDetailsAdapter(this, it)
            dataBinding.rvPersonList.adapter = adapter;

        }
        viewModel?.geterror()?.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this, ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            ) as List<Address>
                        val sb_Address = StringBuilder()
                        dataBinding.apply {
                            tvLatLong.text = "Latitude\n${list[0].latitude}" + "Longitude\n${list[0].longitude}"
                            sb_Address.append("Address\n${list[0].getAddressLine(0)}")
                            sb_Address.append("Locality\n${list[0].locality}" + "Country Name\n${list[0].countryName}")
                            tvAddress.text = sb_Address
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
}
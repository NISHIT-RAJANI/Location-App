package com.example.location_based_sensor

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.ActivityCompat
import java.util.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION), 111)
        }
        if((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED))
        {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 112)
        }

        var lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)


        var ll = object:LocationListener{
            override fun onLocationChanged(location: Location) {
                updateMyLocation(location)
            }

            override fun onProviderDisabled(provider: String) {
            }

            override fun onProviderEnabled(provider: String) {
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
            }
        }

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,1000.2f,ll)

    }

    private fun updateMyLocation(location: Location){
        var gc = Geocoder(this, Locale.getDefault())
        var addresses = gc.getFromLocation(location.latitude,location.longitude,2)
        var addr = addresses.get(0)

        var tv = findViewById<TextView>(R.id.myTextView)

        tv.setText("Location : \n ${addr.getAddressLine(0)}")
    }
}
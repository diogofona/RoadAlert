package ipvc.estg.ecgm.diogo_araujo.roadalert

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.EndPoints
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.OutputReports
import ipvc.estg.ecgm.diogo_araujo.roadalert.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    lateinit var preferences: SharedPreferences
    private lateinit var mMap: GoogleMap
    private lateinit var reports: List<OutputReports>
    private lateinit var report: OutputReports
    private lateinit var username: String
    private var DataRemembered: Int = 0
    private lateinit var lastLocation: Location
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var lat: String
    private lateinit var log: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val MM = findViewById<ImageView>(R.id.menu_maps)
        MM.setOnClickListener{
            val intent = Intent(this@MapsActivity, Menu::class.java)
            startActivity(intent)
            finish()
        }

        val AR = findViewById<ImageView>(R.id.add_report)
        AR.setOnClickListener{
            val intent = Intent(this@MapsActivity, NovoReport::class.java)
            //Toast.makeText(this@MapsActivity, lat + "--" + log, Toast.LENGTH_LONG).show()
            intent.putExtra("latitude", lat)
            intent.putExtra("longitude", log)
            startActivity(intent)
        }


        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        if(preferences.getString("username", "") != "") {
            username = preferences.getString("username", "").toString()
            DataRemembered = 2
        }else if(intent.getStringExtra("username") != ""){
            username = intent.getStringExtra("username").toString()
            DataRemembered = 1
        }

        //initialize fusedLocationCliente
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReports()
        var loc : LatLng

        // call service and markers
        call.enqueue(object : Callback<List<OutputReports>> {
            override fun onResponse(call: Call<List<OutputReports>>, response: Response<List<OutputReports>>) {
                if(response.isSuccessful){
                    reports = response.body()!!
                    for(report in reports) {
                        val coords = report.location!!.split(",")!!.toTypedArray()
                        loc =
                            LatLng(coords[0].toString().toDouble(), coords[1].toString().toDouble())
                        if (username == report.username){
                            mMap.addMarker(
                                MarkerOptions().position(loc).title(report.id.toString()).snippet(
                                    "Titulo: " + report.title
                                )
                            )
                                .setIcon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_ORANGE
                                    )
                                )
                        }else{
                            mMap.addMarker(
                                MarkerOptions().position(loc).title(report.id.toString()).snippet(
                                    "Titulo: " + report.title
                                )
                            )
                                .setIcon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_RED
                                    )
                                )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<OutputReports>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "Erro a carregar os marcadores", Toast.LENGTH_LONG).show()
            }
        })

        //added to implement location periodic updates
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                lastLocation = p0!!.lastLocation
                var loc = LatLng(lastLocation.latitude, lastLocation.longitude)
                lat = lastLocation.latitude.toString()
                log = lastLocation.longitude.toString()
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 15.0f))
                //findViewById<TextView>()
            }
        }

        //request creation
        createLocationRequest()

    }



    private fun createLocationRequest(){
        locationRequest = LocationRequest()
        // interval specifies the rate at wich the app will like to recive location updates
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("DIOGO", "onPause")
    }

    public override fun onResume() {
        super.onResume()
        startLocationUpdates()
        Log.d("DIOGO", "onResume")
    }

    private fun startLocationUpdates(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    fun setUpMap(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        } else {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener(this){ location ->
                if(location != null) {
                    lastLocation = location
                    //Toast.makeText(this@MapsActivity, lastLocation.toString(), Toast.LENGTH_SHORT).show()
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                }
            }
        }
    }

    /** WWwaa
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnInfoWindowClickListener(this)
        setUpMap()
    }

    override fun onInfoWindowClick(p0: Marker?){
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getReportEdit(p0?.title!!.toInt())

        call.enqueue(object : Callback<OutputReports>{
            override fun onResponse(call: Call<OutputReports>, response: Response<OutputReports>){
                if(response.isSuccessful){
                    report = response.body()!!
                    if(DataRemembered == 2) {
                        if(username == report.username) {
                            val intent = Intent(this@MapsActivity, EditarReport::class.java)
                            intent.putExtra("ID", report.id.toString())
                            intent.putExtra("USERNAMES", report.username)
                            intent.putExtra("TITLE", report.title)
                            intent.putExtra("SITUATION", report.situation)
                            intent.putExtra("LOCATION", report.location)
                            intent.putExtra("latitude", lat)
                            intent.putExtra("longitude", log)
                            intent.putExtra("IMAGE", report.image)
                            intent.putExtra("DATE", report.date)
                            startActivity(intent)
                        } else {
                            val intent = Intent(this@MapsActivity, VerReport::class.java)
                            intent.putExtra("ID", report.id.toString())
                            intent.putExtra("USERNAMES", report.username)
                            intent.putExtra("TITLE", report.title)
                            intent.putExtra("SITUATION", report.situation)
                            intent.putExtra("LOCATION", report.location)
                            intent.putExtra("latitude", lat)
                            intent.putExtra("longitude", log)
                            intent.putExtra("IMAGE", report.image)
                            intent.putExtra("DATE", report.date)
                            startActivity(intent)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<OutputReports>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "Erro a identificar o marcador.", Toast.LENGTH_LONG).show()
            }
        })
    }

    //next
}

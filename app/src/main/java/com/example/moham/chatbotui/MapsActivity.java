package com.example.moham.chatbotui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    // Location constants
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1996;
    // location variables
    private boolean mLocationPermissionGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location currentLocation;

    private GoogleMap mMap;
    private MapListener mapListener;
    private Marker marker;
    private LatLng destination;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getLocationPermission();
    }

    private void initMap()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapListener = new MapListener();
    }

    private void getLocationPermission()
    {
        String[] permissions = {FINE_LOCATION, COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(getApplicationContext(), COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted = true;
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getDeviceLocation()
    {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try
        {
            if(mLocationPermissionGranted)
            {
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if(task.isSuccessful())
                        {
                            currentLocation = (Location) task.getResult();
                            if(currentLocation != null)
                            {
                                LatLng myLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14));
                            }
                        }
                    }
                });
            }
        } catch(SecurityException e) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        mLocationPermissionGranted = false;
        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0)
                {
                    for(int i = 0; i <grantResults.length ; i++)
                    {
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionGranted = true;
                    initMap();
                }
                break;
            default:
                initMap();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Intent goBack = new Intent();
        goBack.putExtra("backpressed", "yes");
        setResult(RESULT_OK, goBack);
        finish();
    }


    // Callback function called when map fragment is loaded within the application. Note that it only runs on android phones with Google Play Services installed and updated.
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        try
        {
            mMap.setMyLocationEnabled(true);
        } catch(SecurityException e) {

        }
        // move the camera to Cairo
        LatLng cairo = new LatLng(30.03, 31.23);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cairo, 14));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMapClickListener(mapListener);
        if(mLocationPermissionGranted)
        {
            getDeviceLocation();
        }
    }

    public void onSendLocationBtnClick(View view)
    {
        if(destination != null)
        {
            Intent goBack = new Intent();
            goBack.putExtra("latitude", Double.toString(destination.latitude));
            goBack.putExtra("longitude", Double.toString(destination.longitude));
            goBack.putExtra("callingActivity", "MapsActivity");
            setResult(RESULT_OK, goBack);
            finish();
        }
    }

    class MapListener implements GoogleMap.OnMapClickListener
    {
        @Override
        public void onMapClick(LatLng latLng)
        {
            if(marker != null)
            {
                marker.remove();
            }
            destination = latLng;
            marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Destination"));
        }
    }
}

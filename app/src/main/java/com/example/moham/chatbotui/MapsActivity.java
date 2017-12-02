package com.example.moham.chatbotui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;
    private MapListener mapListener;
    private Marker marker;
    private LatLng destination;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapListener = new MapListener();
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
        // move the camera to Cairo
        LatLng cairo = new LatLng(30.03, 31.23);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cairo, 14));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMapClickListener(mapListener);
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

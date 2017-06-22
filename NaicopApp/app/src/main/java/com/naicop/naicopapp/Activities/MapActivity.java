package com.naicop.naicopapp.Activities;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Config.Permissions;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Handlers.MapActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

import java.util.ArrayList;

public class MapActivity extends NaicopActivity implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private MapActivityHandler handler;
    private ArrayList<Event> eventArrayList;
    private Location userLocation;
    private TextView eventNameTextView;
    private TextView eventDateTextView;
    private TextView eventPriceTextView;
    private TextView eventDescriptionTextView;
    private TextView eventDetailsButton;
    private TextView backButton;
    private LinearLayout mapMenu;
    private LinearLayout markerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new MapActivityHandler(this);
        setContentView(R.layout.activity_map);
        findViewsById();
        setMapMenu();
        //eventArrayList = handler.getEvents();
        if (Permissions.accessFineLocationPermissionEnabled(this)) {
            userLocation = handler.getUserLocation();
            setMap();
        } else {
            Permissions.requestAccessFineLocationPermission(this);
        }

    }

    private void findViewsById() {
        eventNameTextView = (TextView) findViewById(R.id.eventName);
        eventDateTextView = (TextView) findViewById(R.id.eventDate);
        eventPriceTextView = (TextView) findViewById(R.id.eventPrice);
        eventDescriptionTextView = (TextView) findViewById(R.id.eventDescription);
        eventDetailsButton = (TextView) findViewById(R.id.eventDetailsButton);
        backButton = (TextView) findViewById(R.id.backButton);
        mapMenu = (LinearLayout) findViewById(R.id.mapMenu);
        markerMenu = (LinearLayout) findViewById(R.id.markerMenu);
    }

    private void setMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 14));
        eventArrayList = new ArrayList<>();
        eventArrayList.add(new Event("-34.883206", "-56.056027", "Punchi", 600, "La mejor fiesta en años", "27/08/2017"));
        eventArrayList.add(new Event("-34.880583", "-56.049676", "Cachengue", 350, "Fiesta para re mamarse", "25/07/2017"));
        eventArrayList.add(new Event("-34.877634", "-56.054504", "Fiesta Swinger", 2000, "Vas a coger y te van a coger", "12/07/2017"));
        eventArrayList.add(new Event("-34.874615", "-56.061553", "Rombai", 400, "De fiesta!", "25/06/2017"));
        for (Event event : eventArrayList) {
            try {
                LatLng eventLocation = new LatLng(Double.parseDouble(event.latitude), Double.parseDouble(event.longitude));
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(eventLocation)
                        .title(event.title));
                marker.setTag(event);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_REQUEST_GPS_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    userLocation = handler.getUserLocation();
                    setMap();
                } else {
                    finish();
                }
            }

        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Event event = (Event) marker.getTag();
        setMarkerMenu();
        eventNameTextView.setText(event.title);
        eventDateTextView.setText(event.startDate);
        eventDescriptionTextView.setText(event.description);
        eventPriceTextView.setText("$ "+String.valueOf(event.price));
        return false;
    }

    private void setMarkerMenu(){
        mapMenu.setVisibility(View.GONE);
        markerMenu.setVisibility(View.VISIBLE);
    }

    private void setMapMenu(){
        mapMenu.setVisibility(View.VISIBLE);
        markerMenu.setVisibility(View.GONE);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        setMapMenu();
    }
}
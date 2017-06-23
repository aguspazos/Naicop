package com.naicop.naicopapp.Handlers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.naicop.naicopapp.Config.Permissions;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.Persistance.EventSQL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 20/06/2017.
 */

public class MapActivityHandler {

    protected NaicopActivity activity;
    private ArrayList<Event> events;

    public MapActivityHandler(NaicopActivity activity){
        this.activity =activity;
        events = new ArrayList<>();
    }

    public Location getUserLocation(){
        Location userLocation = null;
        LocationManager manager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            userLocation = getBestLocation();
        } catch (SecurityException ec) {
            return userLocation;
        }
        return userLocation;
    }

   private Location getBestLocation(){
       try {
           LocationManager mLocationManager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
           List<String> providers = mLocationManager.getProviders(true);
           Location bestLocation = null;
           for (String provider : providers) {
               Location l = mLocationManager.getLastKnownLocation(provider);
               if (l == null) {
                   continue;
               }
               if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                   bestLocation = l;
               }
           }
           return bestLocation;
       }catch (SecurityException ex){
           ex.printStackTrace();
           return null;
       }
    }

    public ArrayList<Event> getEvents(){
        if(events.size()==0)
            events =  EventSQL.getAllActive("", Category.ALL_ID);
        return events;
    }


}

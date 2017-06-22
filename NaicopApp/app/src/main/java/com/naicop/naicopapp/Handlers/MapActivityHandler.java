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
        LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        try {
            userLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException ec) {
            return userLocation;
        }
        return userLocation;
    }

    public ArrayList<Event> getEvents(){
        if(events.size()==0)
            events =  EventSQL.getAllActive("", Category.ALL_ID);
        return events;
    }


}

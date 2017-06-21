package com.naicop.naicopapp.Config;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

import com.naicop.naicopapp.Activities.MapActivity;
import com.naicop.naicopapp.NaicopActivity;

/**
 * Created by Martin on 20/06/2017.
 */

public class Permissions {

    public static boolean accessFineLocationPermissionEnabled(Context context){
       return  ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestAccessFineLocationPermission(NaicopActivity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constants.PERMISSION_REQUEST_GPS_LOCATION);
    }
}

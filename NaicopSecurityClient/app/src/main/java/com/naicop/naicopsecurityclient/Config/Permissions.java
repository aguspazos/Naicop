package com.naicop.naicopsecurityclient.Config;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


/**
 * Created by Martin on 20/06/2017.
 */

public class Permissions {

    public static boolean cameraPermissionEnabled(Context context){
       return  ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestCameraPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, Constants.PERMISSION_REQUEST_CAMERA);
    }
}

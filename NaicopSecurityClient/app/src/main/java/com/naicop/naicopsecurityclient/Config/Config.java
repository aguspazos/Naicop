package com.naicop.naicopsecurityclient.Config;

import android.content.Context;
import android.content.SharedPreferences;

import com.naicop.naicopsecurityclient.R;

/**
 * Created by Martin on 21/06/2017.
 */

public class Config {

    public  static String  getSavedToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);
        String token = sharedPref.getString(context.getString(R.string.token), "");
        return token;
    }
    public static void updateSavedToken(Context context,String token){
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.token),token);
        editor.commit();
    }

    public static void setLastUpdated(Context context,String lastUpdated){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.preferences), 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("lastUpdated",lastUpdated);
        edit.commit();
    }

    public static String getLastUpdated(Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.preferences), 0);
        String lastUpdated=sp.getString("lastUpdated", "1900-01-01 00:00:00");
        return lastUpdated;
    }

    public static void resetLastUpdated(Context context){
        SharedPreferences sp = context.getSharedPreferences(context.getString( R.string.preferences), 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("lastUpdated","0000-00-00 00:00:00");
        edit.commit();
    }
}

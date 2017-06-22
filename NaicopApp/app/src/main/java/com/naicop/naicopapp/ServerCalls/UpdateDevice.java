package com.naicop.naicopapp.ServerCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Entitites.User;
import com.naicop.naicopapp.Persistance.CategorySQL;
import com.naicop.naicopapp.Persistance.DatabaseHelper;
import com.naicop.naicopapp.Persistance.EventSQL;
import com.naicop.naicopapp.Persistance.TicketSQL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pazos on 18-Jun-17.
 */
public class UpdateDevice {
    protected  Activity activity;
    protected  Context context;
    protected  Map<String, String> params;
    protected String prefsLastUpdated;


    public UpdateDevice(final Activity activity,String token) {
        prefsLastUpdated="1900-01-01 00:00:00";
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();
        params.put("lastUpdated", Config.getLastUpdated(context));
        params.put("token",token);
        String url = Constants.DOMAIN + "/api/devices/getUpdated";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.has("Events")){
                                JSONArray eventsArray = jsonResponse.getJSONArray("Events");
                                SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
                                db.beginTransaction();
                                try {
                                    String lastUpdated = EventSQL.updateAllIncoming(db,eventsArray);
                                    changeLastUpdated(lastUpdated);
                                db.setTransactionSuccessful();
                                }finally {
                                    db.endTransaction();
                                }
                            }

                            if(jsonResponse.has("Categories")){
                                JSONArray categoriesArray = jsonResponse.getJSONArray("Categories");
                                SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
                                db.beginTransaction();
                                try {
                                    String lastUpdated = CategorySQL.updateAllIncoming(db,categoriesArray);
                                    changeLastUpdated(lastUpdated);
                                    db.setTransactionSuccessful();
                                }finally {
                                    db.endTransaction();
                                }
                            }
                            if(jsonResponse.has("Tickets")){
                                JSONArray ticketsArray = jsonResponse.getJSONArray("Tickets");
                                SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
                                db.beginTransaction();
                                try {
                                    String lastUpdated = TicketSQL.updateAllIncoming(db,ticketsArray);
                                    changeLastUpdated(lastUpdated);
                                    db.setTransactionSuccessful();
                                }finally {
                                    db.endTransaction();
                                }
                            }
                        } catch (JSONException e) {
                            if (activity != null) {
                                e.printStackTrace();
                            }
                        }
                        Config.setLastUpdated(context,prefsLastUpdated);
                        Intent intent = new Intent(activity,EventsActivity.class);
                        activity.finish();
                        activity.startActivity(intent);
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            if (error.networkResponse != null) {
                                byte[] data = error.networkResponse.data;
                                String dataStr = null;
                                try {
                                    dataStr = new String(data, "UTF-8");
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Log.v("Error - DATA", dataStr);
                            }
                        }
                        Intent intent = new Intent(activity, EventsActivity.class);
                        activity.startActivity(intent);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    private void changeLastUpdated(String lastUpdated){
        if(lastUpdated.compareTo(prefsLastUpdated)>0)
            prefsLastUpdated = lastUpdated;
    }

}

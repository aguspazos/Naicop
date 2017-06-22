package com.naicop.naicopsecurityclient.ServerCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.naicop.naicopsecurityclient.Activities.LoginActivity;
import com.naicop.naicopsecurityclient.Config.Constants;
import com.naicop.naicopsecurityclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 21/06/2017.
 */

public class ScanQr {
    Activity activity;
    Context context;
    Map<String, String> params;


    public ScanQr(final Activity activity, String code) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<>();
        params.put("token",code);
        String url = Constants.DOMAIN + "/api/tickets/validateQrCode";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.has("status")){
                                String status = jsonResponse.getString("status");
                                if(status.equals("Ok")){
                                    activity.setContentView(R.layout.activity_main);
                                    Toast.makeText(activity, "Acceso autorizado", Toast.LENGTH_SHORT).show();
                                }else{
                                    activity.setContentView(R.layout.activity_main);
                                    Toast.makeText(activity, "Acceso denegado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            if (activity != null) {
                                e.printStackTrace();
                                Toast toast = Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                            activity.setContentView(R.layout.activity_main);
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activity.setContentView(R.layout.activity_main);
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
                    }
                }
        );

        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

}

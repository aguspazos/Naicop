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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 21/06/2017.
 */

public abstract class CheckToken {
    Activity activity;
    Context context;
    Map<String, String> params;


    public CheckToken(final Activity activity,String token) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<>();
        params.put("token",token);
        String url = Constants.DOMAIN + "/api/securityClients/checkToken/"+token;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.has("Status")){
                                String status = jsonResponse.getString("Status");
                                if(status.equals("Ok")){
                                    userLogged(jsonResponse);
                                }else{
                                    userNotLogged();
                                }
                            }
                        } catch (JSONException e) {
                            if (activity != null) {
                                e.printStackTrace();
                                Toast toast = Toast.makeText(context, "Error inesperado", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
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
                        Intent intent = LoginActivity.getStartIntent(activity);
                        activity.startActivity(intent);
                    }
                }
        );

        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public abstract void userLogged(JSONObject response);
    public abstract void userNotLogged();
}

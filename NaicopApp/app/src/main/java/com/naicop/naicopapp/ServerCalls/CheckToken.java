package com.naicop.naicopapp.ServerCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Activities.LoginActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Persistance.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pazos on 17-Jun-17.
 */
public abstract class CheckToken {

    Activity activity;
    Context context;
    Map<String, String> params;


    public CheckToken(final Activity activity,String token) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();
//        params.put("token",token);
        String url = Constants.DOMAIN + "/api/users/checkToken/"+token;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.has("status")){
                                String status = jsonResponse.getString("status");
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
                            }
                        }
                        Intent intent = new Intent(activity, EventsActivity.class);
                        activity.startActivity(intent);

                    }
                }
        );

        MySingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public abstract void userLogged(JSONObject response);
    public abstract void userNotLogged();
}

package com.naicop.naicopsecurityclient.ServerCalls;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.naicop.naicopsecurityclient.Config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Martin on 21/06/2017.
 */

public abstract class Login {
    Activity activity;
    Context context;
    Map<String, String> params;

    public Login(final Activity activity, String email, String password){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
        String url = Constants.DOMAIN + "/api/securityClients/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.has("Status") && jsonResponse.getString("Status").equals(Constants.SERVER_RESPONSE_STATUS_OK)){
                                JSONObject jsonEntity = new JSONObject(jsonResponse.getString("Entity"));
                                String token = jsonEntity.getString("Token");
                                success(token);
                            }else{
                                if(jsonResponse.has("Status") && jsonResponse.get("Status").equals(Constants.SERVER_RESPONSE_STATUS_ERROR)){
                                    Toast.makeText(context, jsonResponse.getString("Entity"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            if (activity != null) {
                                e.printStackTrace();
                                Toast toast = Toast.makeText(context, "Error inesperado (JSONException)", Toast.LENGTH_SHORT);
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

                        if (activity != null) {

                            Toast toast = Toast.makeText(context, "Error inesperado (VolleyError)", Toast.LENGTH_SHORT);
                            toast.show();
                        }
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

    public abstract void success(String token);
}

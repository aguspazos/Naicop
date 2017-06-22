package com.naicop.naicopapp.ServerCalls;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pazos on 21-Jun-17.
 */
public abstract class FacebookLogin {
    Activity activity;
    Context context;
    Map<String, String> params;


    public FacebookLogin(final Activity activity,User user) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();
        params.put("email",user.email);
        params.put("password",user.password);
        params.put("phone",user.phone);
        params.put("name",user.name);
        params.put("lastName",user.lastName);
        params.put("facebookId",user.facebookId);

        String url = Constants.DOMAIN + "/api/users/facebookLogin";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.has("Status") && jsonResponse.getString("Status").equalsIgnoreCase("Ok")) {
                                User user = new User(jsonResponse);
                                userRegistered(user);
                            }else{
                                error(jsonResponse.has("Entity")?jsonResponse.getString("Entity"):"Error inesperado");
                            }
                        } catch (JSONException e) {
                            error("Error inesperado, verifique su conexion a internet");
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String dataStr = "Error inesperado, verifique su conexion a internet";
                        if (error != null) {
                            if (error.networkResponse != null) {
                                byte[] data = error.networkResponse.data;
                                try {
                                    dataStr = new String(data, "UTF-8");

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                                Log.v("Error - DATA", dataStr);
                            }
                        }

                        if (activity != null) {
                            Toast toast = Toast.makeText(context, dataStr, Toast.LENGTH_SHORT);
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

    public abstract void userRegistered(User user);
    public abstract void error(String message);
}

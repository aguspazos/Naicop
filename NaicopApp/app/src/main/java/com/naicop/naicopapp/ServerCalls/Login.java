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
 * Created by pazos on 18-Jun-17.
 */
public abstract class Login {

    Activity activity;
    Context context;
    Map<String, String> params;

    public Login(final Activity activity, String email, String password){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();
        params.put("email",email);
        params.put("password",password);
        String url = Constants.DOMAIN + "/api/users/login";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            User user = new User(jsonResponse);
                            success(user);
                        } catch (JSONException e) {
                            error("Error inesperado, verifique su conexion a internet");
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String dataStr = "";
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
                        error("Usuario o contrasena invalidos");
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

    public abstract void success(User user);
    public abstract void error(String message);
}

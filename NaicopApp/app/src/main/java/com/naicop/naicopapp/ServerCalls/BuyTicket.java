package com.naicop.naicopapp.ServerCalls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Ticket;
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
 * Created by pazos on 22-Jun-17.
 */
public abstract class BuyTicket {
    protected Activity activity;
    protected Context context;
    protected Map<String, String> params;


    public BuyTicket(final Activity activity,int eventId) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();
        params.put("userId", Config.getCurrentUserId(context)+"");
        params.put("eventId",""+eventId);
        String url = Constants.DOMAIN + "/api/tickets";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(jsonResponse.has("Status") && jsonResponse.getString("Status").equalsIgnoreCase("Ok")){
                                JSONObject jsonTicket = jsonResponse.getJSONObject("Entity");
                                TicketSQL.updateIncoming(jsonTicket);
                                Ticket ticket = new Ticket(jsonTicket);
                                ticketBought(ticket);
                            }else{
                                error(jsonResponse.has("Entity")?jsonResponse.getString("Entity"):"Error inesperado, verifique su conexion a internet");
                            }
                        } catch (JSONException e) {
                            if (activity != null) {
                                e.printStackTrace();
                                error("Error inesperado, verifique su conexion a internet");

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
                        error("Error inesperado,verifique su conexion a internet");
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

    public abstract void ticketBought(Ticket ticket);
    public abstract void error(String message);


}

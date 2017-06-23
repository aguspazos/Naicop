package com.naicop.naicopapp.ServerCalls;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Ticket;
import com.naicop.naicopapp.Persistance.TicketSQL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pazos on 22-Jun-17.
 */
public abstract class MakePayment {
    private Activity activity;
    private Context context;
    private Map<String, String> params;


    public MakePayment(final Activity activity,Ticket ticket) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        params = new HashMap<String, String>();
        params.put("ID", ticket.id+"");
        String url = Constants.DOMAIN + "/api/tickets/makePayment";
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
                                paymentMade(ticket);
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

    public abstract void paymentMade(Ticket ticket);
    public abstract void error(String message);
}

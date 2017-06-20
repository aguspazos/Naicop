package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.content.Intent;

import com.naicop.naicopapp.Activities.LoginActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.Persistance.DatabaseHelper;
import com.naicop.naicopapp.ServerCalls.CheckToken;
import com.naicop.naicopapp.ServerCalls.UpdateDevice;

import org.json.JSONObject;

/**
 * Created by pazos on 18-Jun-17.
 */
public class LoadingActivityHandler {

    public NaicopActivity activity;

    public LoadingActivityHandler(NaicopActivity activity){
        this.activity =activity;
    }

    public void checkToken(){

        final String token = Config.getSavedToken(activity.context);
        if (token.equals("")){
            Intent intent = new Intent(activity, LoginActivity.class);
            activity.startActivity(intent);
        }else {
            new CheckToken(activity, token) {
                @Override
                public void userLogged(JSONObject response) {
                    new UpdateDevice(activity,token);
                }

                @Override
                public void userNotLogged() {
                    DatabaseHelper.getInstance().DropDatabaseForLogout();
                    Config.resetLastUpdated(activity.context);
                    Intent intent = new Intent(activity,LoginActivity.class);
                    activity.startActivity(intent);
                }
            };
        }
    }
}
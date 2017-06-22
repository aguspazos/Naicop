package com.naicop.naicopsecurityclient.Handlers;

import android.app.Activity;
import android.content.Intent;

import com.naicop.naicopsecurityclient.Activities.LoadingActivity;
import com.naicop.naicopsecurityclient.Activities.LoginActivity;
import com.naicop.naicopsecurityclient.Activities.MainActivity;
import com.naicop.naicopsecurityclient.Config.Config;
import com.naicop.naicopsecurityclient.ServerCalls.CheckToken;

import org.json.JSONObject;

/**
 * Created by Martin on 21/06/2017.
 */

public class LoadingActivityHandler {

    public Activity activity;

    public LoadingActivityHandler(Activity activity){
        this.activity =activity;
    }

    public void checkToken(){
        final String token = Config.getSavedToken(activity);
        if (token.equals("")){
            Intent intent = LoginActivity.getStartIntent(activity);
            activity.startActivity(intent);
        }else {
            new CheckToken(activity, token) {
                @Override
                public void userLogged(JSONObject response) {
                    Intent intent = MainActivity.getStartIntent(activity);
                    activity.startActivity(intent);
                }

                @Override
                public void userNotLogged() {
                    Config.resetLastUpdated(activity);
                    Intent intent = LoginActivity.getStartIntent(activity);
                    activity.startActivity(intent);
                }
            };
        }
    }
}

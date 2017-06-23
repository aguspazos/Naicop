package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.content.Intent;

import com.naicop.naicopapp.Activities.ProfileActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Entitites.User;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.ServerCalls.GetUser;
import com.naicop.naicopapp.ServerCalls.UpdateUser;

/**
 * Created by pazos on 22-Jun-17.
 */
public abstract class ProfileActivityHandler {
    protected NaicopActivity activity;

    public ProfileActivityHandler(NaicopActivity activity){
        this.activity =activity;
    }

    public void obtainUser(){
        activity.loader.show();

        new GetUser(activity, Config.getSavedToken(activity.context)) {
            @Override
            public void success(User user) {
                activity.loader.hide();
                userObtained(user);
            }

            @Override
            public void error(String message) {
                activity.loader.hide();
                activity.alertPopUp.show(message);
            }
        };
    }

    public void save(User user){
        activity.loader.show();
        new UpdateUser(activity, Config.getSavedToken(activity.context), user) {
            @Override
            public void success(User user) {
                activity.loader.hide();
                Intent intent = new Intent(activity, ProfileActivity.class);
                activity.finish();
                activity.startActivity(intent);
            }

            @Override
            public void error(String message) {
                activity.loader.hide();
                activity.alertPopUp.show(message);
            }
        };
    }

    public abstract void userObtained(User user);
}

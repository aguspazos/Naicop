package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.content.Intent;

import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Entitites.User;
import com.naicop.naicopapp.Exceptions.InvalidLoginDataException;
import com.naicop.naicopapp.Helpers.HelperFunctions;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.ServerCalls.FacebookLogin;
import com.naicop.naicopapp.ServerCalls.Login;
import com.naicop.naicopapp.ServerCalls.RegisterUser;
import com.naicop.naicopapp.ServerCalls.UpdateDevice;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pazos on 18-Jun-17.
 */
public class LoginActivityHandler {

    protected NaicopActivity activity;
    public LoginActivityHandler(NaicopActivity activity){
        this.activity =activity;
    }

    public void LoginAction(String email,String password) throws InvalidLoginDataException {
        validateMail(email);
        new Login(activity, email, password) {
            @Override
            public void success(User user) {
                Config.updateSavedToken(activity.context,user.token);
                Config.setCurrentUserId(activity.context,user.id);
                new UpdateDevice(activity,user.token);
            }

            @Override
            public void error(String message) {
                activity.alertPopUp.show(message);
            }
        };
    }

    public void validateMail(String email) throws InvalidLoginDataException {
        if(!HelperFunctions.validEmail(email))
            throw  new InvalidLoginDataException("Formato de mail invalido");
    }

    public void facebookLoginAction(JSONObject jsonUser) throws JSONException {
        String email = jsonUser.has("email")?jsonUser.getString("email"):"-";
        String phone = "-";
        String name = jsonUser.getString("first_name");
        String lastName = jsonUser.getString("last_name");
        String facebookId = jsonUser.getString("id");

        new FacebookLogin(activity, new User(email, phone, name, lastName,"-", facebookId)) {
            @Override
            public void userRegistered(User user) {
                Config.updateSavedToken(activity.context,user.token);
                Config.setCurrentUserId(activity.context,user.id);
                new UpdateDevice(activity,user.token);
            }

            @Override
            public void error(String message) {
                activity.alertPopUp.show(message);
            }
        };

    }
}

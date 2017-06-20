package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.content.Intent;

import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Entitites.User;
import com.naicop.naicopapp.Exceptions.InvalidLoginDataException;
import com.naicop.naicopapp.Helpers.HelperFunctions;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.ServerCalls.Login;
import com.naicop.naicopapp.ServerCalls.UpdateDevice;

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
                new UpdateDevice(activity,user.token);
            }
        };
    }

    public void validateMail(String email) throws InvalidLoginDataException {
        if(!HelperFunctions.validEmail(email))
            throw  new InvalidLoginDataException("Formato de mail invalido");
    }
}

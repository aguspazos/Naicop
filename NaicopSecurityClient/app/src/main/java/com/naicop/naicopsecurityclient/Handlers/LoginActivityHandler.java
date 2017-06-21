package com.naicop.naicopsecurityclient.Handlers;

import android.app.Activity;
import android.content.Intent;

import com.naicop.naicopsecurityclient.Activities.MainActivity;
import com.naicop.naicopsecurityclient.Config.Config;
import com.naicop.naicopsecurityclient.Exceptions.InvalidLoginDataException;
import com.naicop.naicopsecurityclient.Helpers.HelperFunctions;
import com.naicop.naicopsecurityclient.ServerCalls.Login;

/**
 * Created by Martin on 21/06/2017.
 */

public class LoginActivityHandler {

    public Activity activity;

    public LoginActivityHandler(Activity activity){
        this.activity =activity;
    }

    public void LoginAction(String email, String password) throws InvalidLoginDataException{
        validateMail(email);
        new Login(activity, email, password) {
            @Override
            public void success(String token) {
                Config.updateSavedToken(activity,token);
                Intent intent = MainActivity.getStartIntent(activity);
                activity.startActivity(intent);
            }
        };
    }

    public void validateMail(String email) throws InvalidLoginDataException {
        if(!HelperFunctions.validEmail(email))
            throw new InvalidLoginDataException("Formato de mail invalido");
    }
}

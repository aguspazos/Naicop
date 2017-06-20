package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.support.v7.widget.ThemedSpinnerAdapter;

import com.naicop.naicopapp.Entitites.User;
import com.naicop.naicopapp.Exceptions.InvalidRegisterDataException;
import com.naicop.naicopapp.Helpers.HelperFunctions;
import com.naicop.naicopapp.ServerCalls.RegisterUser;
import com.naicop.naicopapp.ServerCalls.UpdateDevice;

/**
 * Created by pazos on 17-Jun-17.
 */
public class RegisterActivityHandler {

    protected Activity activity;

    public RegisterActivityHandler(Activity activity){
        this.activity =activity;
    }

    public void register(String email,String phone,String name,String lastName,String password,String repeatPassword) throws InvalidRegisterDataException{
        validateEmail(email);
        validatePhone(phone);
        validateEmptyText(name);
        validateEmptyText(lastName);
        validatePassword(password,repeatPassword);
        User user = new User(email,phone,name,lastName,password);
        new RegisterUser(activity, user) {
            @Override
            public void userRegistered(User user) {
                new UpdateDevice(activity,user.token);
            }
        };
    }

    private void validateEmail(String email) throws InvalidRegisterDataException {
        if (!HelperFunctions.validEmail(email))
            throw  new InvalidRegisterDataException("Formato de Email invalido");
    }

    private void validatePhone(String phone)throws InvalidRegisterDataException{
        if(!HelperFunctions.validPhone(phone)){
            throw  new InvalidRegisterDataException("Telefono invalido");

        }
    }


    private void validateEmptyText(String text) throws InvalidRegisterDataException {
        if(text.trim().equals("")){
            throw new InvalidRegisterDataException("Los datos no pueden estar vacios");
        }
    }

    private void validatePassword(String password,String repeatPassword) throws InvalidRegisterDataException {
        if(password.length() < 6)
            throw  new InvalidRegisterDataException("La contrasena no debe tener mas de 6 caracteres");
        if(!password.equals(repeatPassword))
            throw  new InvalidRegisterDataException("Las contrasenas no coinciden");

    }
}

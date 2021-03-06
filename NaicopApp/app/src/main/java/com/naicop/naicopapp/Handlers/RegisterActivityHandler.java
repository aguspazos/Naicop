package com.naicop.naicopapp.Handlers;

import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Entitites.User;
import com.naicop.naicopapp.Exceptions.InvalidUserDataException;
import com.naicop.naicopapp.Helpers.HelperFunctions;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.ServerCalls.RegisterUser;
import com.naicop.naicopapp.ServerCalls.UpdateDevice;

/**
 * Created by pazos on 17-Jun-17.
 */
public class RegisterActivityHandler {

    protected NaicopActivity thisActivity;

    public RegisterActivityHandler(NaicopActivity activity){
        this.thisActivity =activity;
    }

    public void register(String email,String phone,String name,String lastName,String password,String repeatPassword) throws InvalidUserDataException {
        validateEmail(email);
        validatePhone(phone);
        validateEmptyText(name);
        validateEmptyText(lastName);
        validatePassword(password,repeatPassword);
        User user = new User(email,phone,name,lastName,password,"");
        new RegisterUser(thisActivity, user) {
            @Override
            public void userRegistered(User user) {
                thisActivity.loader.show();
                Config.updateSavedToken(thisActivity.context,user.token);
                Config.setCurrentUserId(thisActivity.context,user.id);
                new UpdateDevice(thisActivity, user.token) {
                    @Override
                    public void finished() {
                        thisActivity.loader.hide();
                    }
                };
            }

            @Override
            public void registerError(String message) {
                thisActivity.loader.hide();
                thisActivity.alertPopUp.show(message);
            }
        };
    }

    private void validateEmail(String email) throws InvalidUserDataException {
        if (!HelperFunctions.validEmail(email))
            throw  new InvalidUserDataException("Formato de Email invalido");
    }

    private void validatePhone(String phone)throws InvalidUserDataException {
        if(!HelperFunctions.validPhone(phone)){
            throw  new InvalidUserDataException("Telefono invalido");

        }
    }


    private void validateEmptyText(String text) throws InvalidUserDataException {
        if(text.trim().equals("")){
            throw new InvalidUserDataException("Los datos no pueden estar vacios");
        }
    }

    private void validatePassword(String password,String repeatPassword) throws InvalidUserDataException {
        if(password.length() < 6)
            throw  new InvalidUserDataException("La contrasena no debe tener mas de 6 caracteres");
        if(!password.equals(repeatPassword))
            throw  new InvalidUserDataException("Las contrasenas no coinciden");

    }
}

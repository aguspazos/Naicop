package com.naicop.naicopapp.Entitites;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pazos on 17-Jun-17.
 */
public class User {

    public int id;
    public String email;
    public String phone;
    public String name;
    public String lastName;
    public String password;
    public String token;

    public User( String email, String phone, String name, String lastName, String password) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
    }

    public User(JSONObject jsonUser) throws JSONException {
        this.id = jsonUser.getInt("ID");
        this.email = jsonUser.getString("Email");
        this.password = jsonUser.getString("Password");
        this.token = jsonUser.getString("Token");
    }
}

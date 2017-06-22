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
    public String facebookId;

    public User( String email, String phone, String name, String lastName, String password,String facebookId) {
        this.email = email;
        this.phone = phone;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.facebookId = facebookId;
    }

    public User(JSONObject jsonUser) throws JSONException {
        this.id = jsonUser.getInt("ID");
        this.email = jsonUser.getString("Email");
        this.phone = jsonUser.getString("Phone");
        this.name = jsonUser.getString("Name");
        this.lastName = jsonUser.getString("LastName");
        this.password = jsonUser.getString("Password");
        this.token = jsonUser.getString("Token");
        this.facebookId = jsonUser.has("facebookId")?jsonUser.getString("facebookId"):"0";
    }
}

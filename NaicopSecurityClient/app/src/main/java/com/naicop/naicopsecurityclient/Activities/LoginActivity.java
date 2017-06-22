package com.naicop.naicopsecurityclient.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.naicop.naicopsecurityclient.Exceptions.InvalidLoginDataException;
import com.naicop.naicopsecurityclient.Handlers.LoginActivityHandler;
import com.naicop.naicopsecurityclient.R;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityHandler handler;
    private TextView loginButton;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new LoginActivityHandler(this);
        setContentView(R.layout.activity_login);
        findViewsById();
        setButtonColor();
        setBehaviour();
    }

    private void findViewsById(){
        loginButton = (TextView)findViewById(R.id.loginButton);
    }

    private void setButtonColor(){
        GradientDrawable loginShape = (GradientDrawable)loginButton.getBackground();
        loginShape.setColor(Color.parseColor("#51b7dd"));
    }

    private void setBehaviour(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAction();
            }
        });
    }

    private void loginAction(){
        EditText emailText = (EditText)findViewById(R.id.emailText);
        EditText passwordText = (EditText)findViewById(R.id.passwordText);
        try {
            handler.LoginAction(emailText.getText().toString(),passwordText.getText().toString());
        } catch (InvalidLoginDataException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}

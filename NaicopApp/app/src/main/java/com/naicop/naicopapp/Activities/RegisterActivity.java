package com.naicop.naicopapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.naicop.naicopapp.Exceptions.InvalidUserDataException;
import com.naicop.naicopapp.Handlers.RegisterActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

public class RegisterActivity extends NaicopActivity {

    protected TextView register;
    protected RegisterActivityHandler registerActivityHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerActivityHandler = new RegisterActivityHandler(this);

        setContentView(R.layout.actity_register);
        super.comeToLife();
        register = (TextView)findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    registerAction();
                }catch (InvalidUserDataException ex){
                    alertPopUp.show(ex.getMessage());
                }
            }
        });
    }

    private void registerAction() throws InvalidUserDataException {
        EditText emailText = (EditText)findViewById(R.id.emailText);
        EditText phoneText = (EditText)findViewById(R.id.phoneText);
        EditText nameText = (EditText)findViewById(R.id.nameText);
        EditText lastNameText = (EditText)findViewById(R.id.lastNameText);
        EditText passwordText = (EditText)findViewById(R.id.passwordText);
        EditText repeatPasswordText = (EditText)findViewById(R.id.repeatPasswordText);

        registerActivityHandler.register(emailText.getText().toString(),phoneText.getText().toString(),
                nameText.getText().toString(),lastNameText.getText().toString(),
                passwordText.getText().toString(),repeatPasswordText.getText().toString());

    }
}

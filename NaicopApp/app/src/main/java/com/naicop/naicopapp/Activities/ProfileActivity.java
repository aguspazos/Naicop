package com.naicop.naicopapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.naicop.naicopapp.Entitites.User;
import com.naicop.naicopapp.Exceptions.InvalidUserDataException;
import com.naicop.naicopapp.Handlers.ProfileActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

public class ProfileActivity extends NaicopActivity {

    protected TextView saveButton;
    protected EditText emailText;
    protected EditText phoneText;
    protected EditText nameText;
    protected EditText lastNameText;

    protected ProfileActivityHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        super.comeToLife();
        findViewsById();
        handler = new ProfileActivityHandler(this) {
            @Override
            public void userObtained(User user) {
                setUserData(user);
            }
        };
        handler.obtainUser();
        saveButton = (TextView)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveAction();
                }catch (InvalidUserDataException ex){
                    alertPopUp.show(ex.getMessage());
                }
            }
        });
    }

    private void findViewsById(){
        emailText = (EditText)findViewById(R.id.emailText);
        phoneText = (EditText)findViewById(R.id.phoneText);
        nameText = (EditText)findViewById(R.id.nameText);
        lastNameText = (EditText)findViewById(R.id.lastNameText);
    }

    public void setUserData(User user){
        emailText.setText(user.email);
        phoneText.setText(user.phone);
        nameText.setText(user.name);
        lastNameText.setText(user.lastName);
    }

    private void saveAction() throws InvalidUserDataException{
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String name =  nameText.getText().toString();
        String lastName = lastNameText.getText().toString();
//        handler.save(email,phone,name,lastName);
    }

}

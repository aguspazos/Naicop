package com.naicop.naicopapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naicop.naicopapp.Exceptions.InvalidLoginDataException;
import com.naicop.naicopapp.Handlers.LoginActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends NaicopActivity {

    protected LoginActivityHandler handler;
    protected TextView loginButton;
    protected TextView facebookLoginButton;
    protected TextView signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new LoginActivityHandler(this);
        setContentView(R.layout.activity_login);
        findViewsById();
        setButtonsColors();
        setBehaviour();
    }

    private void findViewsById(){
        loginButton = (TextView)findViewById(R.id.loginButton);
        facebookLoginButton = (TextView)findViewById(R.id.facebookLoginButton);
        signUpButton = (TextView)findViewById(R.id.signUpButton);
    }

    private void setButtonsColors(){
        GradientDrawable loginShape = (GradientDrawable)loginButton.getBackground();
        loginShape.setColor(Color.parseColor("#51b7dd"));
        GradientDrawable facebookLoginShape = (GradientDrawable)facebookLoginButton.getBackground();
        facebookLoginShape.setColor(Color.parseColor("#51a3d1"));
        GradientDrawable signUpShape = (GradientDrawable)signUpButton.getBackground();
        signUpShape.setColor(Color.parseColor("#54b16d"));

    }

    private void setBehaviour(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginAction();
            }
        });

        facebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facebookLoginAction();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpAction();
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
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void signUpAction(){
        Intent intent = new Intent(context,RegisterActivity.class);
        startActivity(intent);
    }

    private void facebookLoginAction(){
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(callbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        Log.d("Success", "Login");
//                        AccessToken accessToken = loginResult.getAccessToken();
//                        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
//                                Log.v("facebookCallBack", jsonObject.toString());
//                                facebookLogin(jsonObject);
//                            }
//                        });
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id,first_name,last_name,name,gender,email");
//                        request.setParameters(parameters);
//                        request.executeAsync();
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        PopUpHelper.showAlert(thisActivity,context,"Login cancelado");
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        if (exception instanceof FacebookAuthorizationException) {
//                            if (AccessToken.getCurrentAccessToken() != null) {
//                                LoginManager.getInstance().logOut();
//                            }
//                            PopUpHelper.showAlert(thisActivity,context,"Intente nuevamente");
//                        }else {
//                            PopUpHelper.showAlert(thisActivity, context, exception.getMessage());
//                        }
//                    }
//                });
//        RelativeLayout facebookLogin = (RelativeLayout) findViewById(R.id.facebookLogin);
//        facebookLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<String> permissions = Arrays.asList("public_profile", "user_friends", "email");
//                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, permissions);
//            }
//        });
    }

}

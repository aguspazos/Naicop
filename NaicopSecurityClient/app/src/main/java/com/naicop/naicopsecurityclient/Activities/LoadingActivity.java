package com.naicop.naicopsecurityclient.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.naicop.naicopsecurityclient.Handlers.LoadingActivityHandler;
import com.naicop.naicopsecurityclient.R;

public class LoadingActivity extends AppCompatActivity {

    private LoadingActivityHandler handler;

    public static Intent getStartIntent(Context context){
        return new Intent(context, LoadingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new LoadingActivityHandler(this);
        setContentView(R.layout.activity_loading);
        handler.checkToken();

    }
}

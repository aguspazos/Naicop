package com.naicop.naicopsecurityclient.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.naicop.naicopsecurityclient.Handlers.LoadingActivityHandler;
import com.naicop.naicopsecurityclient.R;

public class LoadingActivity extends AppCompatActivity {

    private LoadingActivityHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new LoadingActivityHandler(this);
        setContentView(R.layout.activity_loading);
        handler.checkToken();

    }
}

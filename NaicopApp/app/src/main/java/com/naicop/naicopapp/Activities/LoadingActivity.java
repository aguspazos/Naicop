package com.naicop.naicopapp.Activities;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.naicop.naicopapp.Handlers.LoadingActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.Persistance.DatabaseHelper;
import com.naicop.naicopapp.R;

public class LoadingActivity extends NaicopActivity {

    protected LoadingActivityHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new LoadingActivityHandler(this);
        setContentView(R.layout.activity_loading);
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.loader);
        progressBar.setIndeterminate(true);
        DatabaseHelper.setContext(getApplicationContext());

        handler.checkToken();
        
    }
}

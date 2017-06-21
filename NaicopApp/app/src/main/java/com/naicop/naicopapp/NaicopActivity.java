package com.naicop.naicopapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.naicop.naicopapp.Widgets.MenuBar;

public class NaicopActivity extends Activity {

    public Context context;
    protected MenuBar menuBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        menuBar = new MenuBar(this,context);
        menuBar.comeToLife();
    }
}

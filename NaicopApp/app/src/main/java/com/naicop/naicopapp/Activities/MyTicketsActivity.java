package com.naicop.naicopapp.Activities;

import android.os.Bundle;

import com.naicop.naicopapp.Handlers.EventActivityHandler;
import com.naicop.naicopapp.Handlers.MyTicketsActivityHandler;

/**
 * Created by pazos on 22-Jun-17.
 */
public class MyTicketsActivity extends EventsActivity {

    @Override
    protected void createHandler(){
        handler = new MyTicketsActivityHandler(this);
    }
}

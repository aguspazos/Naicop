package com.naicop.naicopapp.Activities;

import com.naicop.naicopapp.Handlers.HistoricalEventsActivityHandler;

/**
 * Created by pazos on 22-Jun-17.
 */
public class HistoricalEventsActivity extends EventsActivity {

    @Override
    protected void createHandler() {
        handler = new HistoricalEventsActivityHandler(this);
    }
}

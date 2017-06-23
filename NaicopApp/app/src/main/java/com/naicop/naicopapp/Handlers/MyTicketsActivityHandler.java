package com.naicop.naicopapp.Handlers;

import android.app.Activity;

import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.Persistance.EventSQL;

import java.util.ArrayList;

/**
 * Created by pazos on 22-Jun-17.
 */
public class MyTicketsActivityHandler extends EventsActivityHandler {

    public MyTicketsActivityHandler(NaicopActivity activity) {
        super(activity);
    }

    @Override
    protected void setAllEvents() {
        allEvents = EventSQL.getAllActiveFromUser(Config.getCurrentUserId(activity.context),"", Category.ALL_ID);
    }

    @Override
    protected ArrayList<Event> getFilteredCategories(Category category) {
        return EventSQL.getAllActiveFromUser(Config.getCurrentUserId(activity.context),orderBy,category.id);
    }

    @Override
    protected ArrayList<Event> getOrderedEvents(int categoryId) {
        return EventSQL.getAllActiveFromUser(Config.getCurrentUserId(activity.context),orderBy,categoryId);

    }
}

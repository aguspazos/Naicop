package com.naicop.naicopapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Handlers.EventsActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends NaicopActivity {

    protected EventsActivityHandler handler;
    protected List<Event> filteredEvents;
    protected List<Event> allEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filteredEvents = new ArrayList<>();
        allEvents = new ArrayList<>();

        handler = new EventsActivityHandler(this);
        setContentView(R.layout.activity_events);
        TextView filterCategoriesButton = (TextView) findViewById(R.id.filterCategoriesButton);
        loadCategories();
        filterCategoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void loadCategories(){
        RelativeLayout categoriesContainer = (RelativeLayout)findViewById(R.id.filterCategoriesContainer);
        List<Category> categories = handler.getCategories();
        for(Category category : categories){

        }
    }
    
}

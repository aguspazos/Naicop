package com.naicop.naicopapp.Handlers;

import android.app.Activity;

import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Persistance.CategorySQL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pazos on 18-Jun-17.
 */
public class EventsActivityHandler {
    protected Activity activity;
    private List<Category> categories;

    public EventsActivityHandler(Activity activity){
        this.activity = activity;
        categories = new ArrayList<>();
    }

    public List<Category>getCategories(){
        if(categories.size()==0){
            categories = CategorySQL.getAll();
        }
        return categories;
    }
}

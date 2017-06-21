package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.widget.LinearLayout;

import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Persistance.CategorySQL;
import com.naicop.naicopapp.Persistance.EventSQL;
import com.naicop.naicopapp.Widgets.FilterCategoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pazos on 18-Jun-17.
 */
public class EventsActivityHandler {
    protected Activity activity;
    protected FilterCategoryView filterCategoryView;

    private List<Category> categories;
    protected ArrayList<Event> filteredEvents;
    protected ArrayList<Event> allEvents;
    protected Category categorySelected;


    public EventsActivityHandler(Activity activity){
        this.activity = activity;
        categories = new ArrayList<>();
        allEvents = EventSQL.getAllActive();
        filteredEvents = new ArrayList<>(allEvents);
    }


    public void search(String text){
        if(text.trim().equals("")){
            allEvents = EventSQL.getAllActive();
            filteredEvents = allEvents;
        }else{
            filteredEvents.clear();
            for(Event event : allEvents){
                String upperText = text.toUpperCase();
                if(event.title.toUpperCase().startsWith(upperText) || event.description.toUpperCase().startsWith(upperText)) {
                    this.filteredEvents.add(event);
                }

            }
        }
    }
    public List<Category>getCategories(){
        if(categories.size()==0){
            Category all = new Category();
            all.name = "Todas";
            all.id = Category.ALL_ID;
            categories = CategorySQL.getAll();
            categories.add(0,all);
        }
        return categories;
    }

    public List<Event> getEvents(){
        return filteredEvents;
    }

    public void filterByCategory(Category category){
        if(category.id == Category.ALL_ID)
            filteredEvents = allEvents;
        else
            filteredEvents = EventSQL.getAllActiveFromCategory(category.id);
        categorySelected = category;
    }
    public void resetOrder(){
        filteredEvents = allEvents;
    }

    public void orderByTitle(){
        getOrdered(EventSQL.title.second + " ASC");
    }

    public void orderByDate(){
        getOrdered(EventSQL.startDate.second +" ASC");
    }

    public void orderByPrice(){
        getOrdered(EventSQL.price.second + " DESC");
    }

    private void getOrdered(String orderBy){
        if(categorySelected.id == Category.ALL_ID)
            filteredEvents = EventSQL.getAllOrdered(orderBy);
        else
            filteredEvents = EventSQL.getAllOrdered(orderBy,categorySelected.id);
    }
}

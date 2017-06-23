package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.widget.LinearLayout;

import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.Persistance.CategorySQL;
import com.naicop.naicopapp.Persistance.EventSQL;
import com.naicop.naicopapp.Widgets.FilterCategoryView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pazos on 18-Jun-17.
 */
public class EventsActivityHandler {
    protected NaicopActivity activity;
    protected FilterCategoryView filterCategoryView;

    protected String orderBy;

    private List<Category> categories;
    protected ArrayList<Event> filteredEvents;
    protected ArrayList<Event> allEvents;
    protected Category categorySelected;


    public EventsActivityHandler(NaicopActivity activity){
        this.activity = activity;
        categories = new ArrayList<>();
        orderBy ="";
        setAllEvents();
        filteredEvents = new ArrayList<>(allEvents);
    }



    protected void setAllEvents(){
        allEvents = EventSQL.getAllActive("",Category.ALL_ID);
    }
    public void search(String text){
        if(text.trim().equals("")){
            setAllEvents();
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
        if(category == null || category.id == Category.ALL_ID)
            filteredEvents = allEvents;
        else
            filteredEvents = getFilteredCategories(category);
        categorySelected = category;
    }
    public void resetOrder(){
        filteredEvents = allEvents;
    }

    protected ArrayList<Event> getFilteredCategories(Category category){
        return EventSQL.getAllActive(orderBy,category.id);
    }

    protected ArrayList<Event>getOrderedEvents(int categoryId){
        return EventSQL.getAllActive(orderBy,categoryId);
    }

    public void orderByTitle(){
        orderBy = "ORDER BY "+EventSQL.title.second+" ASC";
        filteredEvents = getOrderedEvents(categorySelected == null?Category.ALL_ID:categorySelected.id);
    }

    public void orderByDate(){
        orderBy = "ORDER BY "+EventSQL.startDate.second+" ASC";
        filteredEvents = getOrderedEvents(categorySelected == null?Category.ALL_ID:categorySelected.id);
    }

    public void orderByPrice(){
        orderBy = "ORDER BY "+EventSQL.price.second+" DESC";
        filteredEvents = getOrderedEvents(categorySelected == null?Category.ALL_ID:categorySelected.id);
    }
}

package com.naicop.naicopapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naicop.naicopapp.Adapters.EventsAdapter;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Handlers.EventsActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.Persistance.EventSQL;
import com.naicop.naicopapp.R;
import com.naicop.naicopapp.Widgets.FilterCategoryView;
import com.naicop.naicopapp.Widgets.MenuBar;
import com.naicop.naicopapp.Widgets.MenuWithSearchBar;

import java.util.ArrayList;
import java.util.List;

public class EventsActivity extends NaicopActivity {

    protected EventsActivityHandler handler;
    protected FilterCategoryView filterCategoryView;
    protected ListView eventsList;
    protected TextView filterCategoriesButton;
    protected RelativeLayout orderByTitleSelectedTab;
    protected RelativeLayout orderByDateSelectedTab;
    protected RelativeLayout orderByPriceSelectedTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createHandler();
        setContentView(R.layout.activity_events);
        super.comeToLife();
        eventsList = (ListView)findViewById(R.id.eventsList);
        menuBar = new MenuWithSearchBar(this,context) {
            @Override
            public void textChanged(String text) {
                handler.search(text);
                eventsList.setAdapter(new EventsAdapter(context,handler.getEvents()));
            }
        }.comeToLife();
        loadCategories();
        filterCategoriesButton = (TextView) findViewById(R.id.filterCategoriesButton);
        filterCategoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MenuWithSearchBar)menuBar).resetSearchText();
                filterCategoryView.show();
            }
        });
        orderBehaviour();
        showEvents();
    }

    protected void createHandler(){
        handler = new EventsActivityHandler(this);
    }
    private void loadCategories(){
        filterCategoryView = new FilterCategoryView(EventsActivity.this, context) {
            @Override
            public void categoryClicked(Category category) {
                handler.filterByCategory(category);
                eventsList.setAdapter(new EventsAdapter(context,handler.getEvents()));
                hide();
            }
        }.comeToLife(handler.getCategories());
    }

    private void orderBehaviour(){
        findSelectedTabsViews();
        final RelativeLayout orderByTitle = (RelativeLayout) findViewById(R.id.orderByTitleTag);
        orderByTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected(orderByTitleSelectedTab)){
                    resetOrderTabs();
                    handler.resetOrder();
                }else {
                    selectTab(orderByTitleSelectedTab);
                    handler.orderByTitle();
                    eventsList.setAdapter(new EventsAdapter(context,handler.getEvents()));
                }
            }
        });
        final RelativeLayout orderByDate = (RelativeLayout) findViewById(R.id.orderByDateTag);
        orderByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected(orderByDateSelectedTab)){
                    resetOrderTabs();
                    handler.resetOrder();
                }else {
                    selectTab(orderByDateSelectedTab);
                    handler.orderByDate();
                    eventsList.setAdapter(new EventsAdapter(context,handler.getEvents()));
                }
            }
        });
        RelativeLayout orderByPrice = (RelativeLayout) findViewById(R.id.orderByPriceTag);
        orderByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelected(orderByPriceSelectedTab)){
                    resetOrderTabs();
                    handler.resetOrder();
                }else {
                    selectTab(orderByPriceSelectedTab);
                    handler.orderByPrice();
                    eventsList.setAdapter(new EventsAdapter(context,handler.getEvents()));
                }
            }
        });

    }

    private void findSelectedTabsViews(){
        orderByTitleSelectedTab = (RelativeLayout)findViewById(R.id.orderByTitleSelectedTab);
        orderByDateSelectedTab = (RelativeLayout)findViewById(R.id.orderByDateSelectedTab);
        orderByPriceSelectedTab = (RelativeLayout)findViewById(R.id.orderByPriceSelectedTag);
    }

    private boolean isSelected(View view){
        return view.getTag() != null && (boolean)view.getTag();
    }
    private void resetOrderTabs(){
        orderByTitleSelectedTab.setVisibility(View.GONE);
        orderByTitleSelectedTab.setTag(false);
        orderByDateSelectedTab.setVisibility(View.GONE);
        orderByDateSelectedTab.setTag(false);
        orderByPriceSelectedTab.setVisibility(View.GONE);
        orderByPriceSelectedTab.setTag(false);
    }
    private void selectTab(View view){
        resetOrderTabs();
        view.setTag(true);
        view.setVisibility(View.VISIBLE);
    }
    private void showEvents(){
        eventsList.setAdapter(new EventsAdapter(context,handler.getEvents()));
        eventsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(EventsActivity.this,EventActivity.class);
                Event event = (Event)adapterView.getAdapter().getItem(i);
                intent.putExtra("event_id",event.id);
                startActivity(intent);

            }
        });
    }


}

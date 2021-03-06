package com.naicop.naicopapp.Widgets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Activities.HistoricalEventsActivity;
import com.naicop.naicopapp.Activities.MapActivity;
import com.naicop.naicopapp.Activities.MyTicketsActivity;
import com.naicop.naicopapp.Activities.ProfileActivity;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

/**
 * Created by pazos on 21-Jun-17.
 */
public class MenuBar {

    protected NaicopActivity activity;
    protected Context context;
    protected LinearLayout itemsLayout;
    protected View menuBar;

    public MenuBar(NaicopActivity activity, Context context){
        this.activity =activity;
        this.context = context;
    }

    public MenuBar comeToLife(){
        menuBar = activity.findViewById(R.id.menuBar);
        if(menuBar != null) {
            itemsLayout = (LinearLayout) activity.findViewById(R.id.itemsLayoutContainer);
            ImageView menuItemsDisplay = (ImageView) menuBar.findViewById(R.id.menuItemsDisplay);
            menuItemsDisplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleItems();
                }
            });
        }
        return this;
    }

    private void toggleItems(){
        if(itemsLayout != null) {
            if (itemsLayout.getVisibility() == View.GONE) {
                showItems();
            } else {
                hideItems();
            }
        }
    }
    private void showItems(){
        itemsLayout.setVisibility(View.VISIBLE);
        Animation a = new ScaleAnimation(1, 1, 0, 1);
        a.setDuration(500);
        itemsLayout.startAnimation(a);
        setItemsListeners();
    }

    private void hideItems(){
        Animation a = new ScaleAnimation(1,1,1,0);
        a.setDuration(500);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                itemsLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        itemsLayout.startAnimation(a);
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Es necesario activar el GPS, deseas hacerlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        activity.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void setItemsListeners(){
        RelativeLayout menuMap = (RelativeLayout)activity.findViewById(R.id.menuMap);
        menuMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideItems();
                final LocationManager manager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else {
                    Intent intent = new Intent(activity, MapActivity.class);
                    activity.startActivity(intent);
                }

            }
        });

        RelativeLayout menuEvents = (RelativeLayout)activity.findViewById(R.id.menuEvents);
        menuEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideItems();
                Intent intent = new Intent(activity,EventsActivity.class);
                activity.startActivity(intent);
            }
        });
        RelativeLayout menuMyTickets = (RelativeLayout)activity.findViewById(R.id.menuMyTickets);
        menuMyTickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideItems();
                Intent intent = new Intent(activity, MyTicketsActivity.class);
                activity.startActivity(intent);
            }
        });
        RelativeLayout menuProfile = (RelativeLayout)activity.findViewById(R.id.menuProfile);
        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideItems();
                Intent intent = new Intent(activity, ProfileActivity.class);
                activity.startActivity(intent);
            }
        });
        RelativeLayout menuHistorical = (RelativeLayout)activity.findViewById(R.id.menuHistoricalTickets);
        menuHistorical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, HistoricalEventsActivity.class);
                activity.startActivity(intent);
            }
        });

    }



}

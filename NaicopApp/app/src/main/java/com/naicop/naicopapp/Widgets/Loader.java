package com.naicop.naicopapp.Widgets;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naicop.naicopapp.R;

/**
 * Created by pazos on 22-Jun-17.
 */
public class Loader {
    protected Activity activity;
    protected Context context;
    protected RelativeLayout loaderContainer;

    public Loader(Activity activity,Context context){
        this.activity =activity;
        this.context = context;
    }

    public Loader comeToLife(){
        loaderContainer = (RelativeLayout)activity.findViewById(R.id.loaderContainer);
        return this;
    }

    public void show(){
        if(loaderContainer != null)
            loaderContainer.setVisibility(View.VISIBLE);
    }

    public void hide(){
        if(loaderContainer != null)
            loaderContainer.setVisibility(View.GONE);
    }
}

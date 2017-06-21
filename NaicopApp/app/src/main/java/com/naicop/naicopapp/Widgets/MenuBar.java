package com.naicop.naicopapp.Widgets;

import android.content.Context;

import com.naicop.naicopapp.NaicopActivity;

/**
 * Created by pazos on 21-Jun-17.
 */
public class MenuBar {

    protected NaicopActivity activity;
    protected Context context;

    public MenuBar(NaicopActivity activity, Context context){
        this.activity =activity;
        this.context = context;
    }

    public MenuBar comeToLife(){
        return this;
    }


}

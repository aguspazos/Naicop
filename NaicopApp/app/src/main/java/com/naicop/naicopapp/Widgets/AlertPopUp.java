package com.naicop.naicopapp.Widgets;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naicop.naicopapp.R;

/**
 * Created by pazos on 21-Jun-17.
 */
public class AlertPopUp {
    protected Activity activity;
    protected Context context;
    RelativeLayout alertContainer;
    TextView alertMessage;

    public AlertPopUp(Activity activity,Context context){
        this.activity =activity;
        this.context = context;
    }

    public AlertPopUp comeToLife(){
        alertContainer = (RelativeLayout)activity.findViewById(R.id.alertContainer);
        if(alertContainer != null){
            alertMessage = (TextView)alertContainer.findViewById(R.id.alertMessage);
            TextView okButton = (TextView)alertContainer.findViewById(R.id.alertOkButton);
            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    okAction();
                }
            });
        }
        return this;
    }

    public void show(String message){
        if(alertContainer != null) {
            alertMessage.setText(message);
            alertContainer.setVisibility(View.VISIBLE);
        }
    }

    protected void okAction(){
        hide();
    }
    public void hide(){
        if (alertContainer != null)
            alertContainer.setVisibility(View.GONE);
    }
}

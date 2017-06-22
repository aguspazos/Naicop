package com.naicop.naicopapp.Widgets;

import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.naicop.naicopapp.Entitites.Ticket;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;

/**
 * Created by pazos on 22-Jun-17.
 */
public abstract class MakePaymentPopUp {

    protected NaicopActivity activity;
    protected RelativeLayout makePaymentContainer;
    protected Ticket ticket;
    public MakePaymentPopUp(NaicopActivity activity){
        this.activity = activity;
    }

    public MakePaymentPopUp comeToLife(Ticket ticket){
        this.ticket = ticket;
        makePaymentContainer = (RelativeLayout)activity.findViewById(R.id.makePaymentContainer);
        final TextView makePaymentButton = (TextView)makePaymentContainer.findViewById(R.id.makePaymentButton);
        final EditText cardNumberText = (EditText) makePaymentContainer.findViewById(R.id.cardNumberText);
        final EditText cardCvvText = (EditText)makePaymentContainer.findViewById(R.id.cardCvvText);

        makePaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePaymentValidation(cardNumberText.getText().toString(),cardCvvText.getText().toString());
            }
        });
        return this;
    }

    protected void makePaymentValidation(String cardNumberText,String cardCvvText){
        if(cardNumberText.length()<8){
            activity.alertPopUp.show("Numero de tarjeta invalido");
        }
        else if(cardCvvText.length()<3){
            activity.alertPopUp.show("Codigo de tarjeta invalido");
        }else{
            makePayment(ticket);
        }
        hide();
    }

    public abstract void makePayment(Ticket ticket);

    public void show(){
        makePaymentContainer.setVisibility(View.VISIBLE);
    }
    public void hide(){
        makePaymentContainer.setVisibility(View.GONE);
    }
}

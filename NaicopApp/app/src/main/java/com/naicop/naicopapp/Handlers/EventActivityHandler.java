package com.naicop.naicopapp.Handlers;

import android.app.Activity;
import android.content.Intent;

import com.naicop.naicopapp.Activities.EventActivity;
import com.naicop.naicopapp.Activities.EventsActivity;
import com.naicop.naicopapp.Config.Config;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Entitites.Ticket;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.Persistance.EventSQL;
import com.naicop.naicopapp.Persistance.TicketSQL;
import com.naicop.naicopapp.ServerCalls.BuyTicket;
import com.naicop.naicopapp.ServerCalls.MakePayment;
import com.naicop.naicopapp.Widgets.MakePaymentPopUp;

/**
 * Created by pazos on 21-Jun-17.
 */
public class EventActivityHandler {
    protected NaicopActivity thisActivity;
    public Event event;
    public Ticket ticket;


    public EventActivityHandler(NaicopActivity activity){
        this.thisActivity =activity;
    }

    public void setData(int eventId){
        event = EventSQL.get(eventId);
        ticket = TicketSQL.getFromUserAndEvent(Config.getCurrentUserId(thisActivity.context),eventId);
    }


    public void shareEvent(){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        thisActivity.startActivityForResult(Intent.createChooser(share, "Compartí con tus amigos"),1);
    }

    public void buyTicket(){
        thisActivity.loader.show();
        new BuyTicket(thisActivity,event.id) {
            @Override
            public void ticketBought(Ticket aTicket) {
                ticket = aTicket;
                showMakePaymentPopUp();
            }

            @Override
            public void error(String message) {
                thisActivity.loader.hide();
                thisActivity.alertPopUp.show(message);
            }
        };
    }

    public void showMakePaymentPopUp(){
        thisActivity.loader.hide();
        new MakePaymentPopUp(thisActivity) {
            @Override
            public void makePayment(Ticket ticket) {
                thisActivity.loader.show();
                new MakePayment(activity,ticket) {
                    @Override
                    public void paymentMade(Ticket ticket) {
                        thisActivity.loader.hide();
                        Intent intent = new Intent(activity,EventActivity.class);
                        intent.putExtra("event_id",ticket.eventId);
                        activity.startActivity(intent);
                    }

                    @Override
                    public void error(String message) {
                        thisActivity.loader.hide();
                        activity.alertPopUp.show(message);
                    }
                };
            }
        }.comeToLife(ticket).show();
    }

}

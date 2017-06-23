package com.naicop.naicopapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Event;
import com.naicop.naicopapp.Entitites.Ticket;
import com.naicop.naicopapp.Entitites.TicketStatus;
import com.naicop.naicopapp.Handlers.EventActivityHandler;
import com.naicop.naicopapp.NaicopActivity;
import com.naicop.naicopapp.R;
import com.squareup.picasso.Picasso;

public class EventActivity extends NaicopActivity {

    protected EventActivityHandler handler;


    protected ImageView eventImage;
    protected TextView eventTitle;
    protected TextView eventDescription;
    protected TextView buyTicketButton;
    protected TextView seeTicketDetailsButton;
    protected ImageView shareButton;
    protected RelativeLayout ticketData;
    protected RelativeLayout ticketDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new EventActivityHandler(this);
        setContentView(R.layout.activity_event);
        super.comeToLife();
        findViewsById();
        handler.setData(getIntent().getIntExtra("event_id",-1));
        if(handler.event != null){
            showEventData();
        }
        setBehaviour();

    }

    private void findViewsById(){
        eventImage = (ImageView)findViewById(R.id.eventImage);
        eventTitle = (TextView)findViewById(R.id.eventTitle);
        eventDescription = (TextView)findViewById(R.id.eventDescription);
        buyTicketButton = (TextView)findViewById(R.id.buyTicketButton);
        seeTicketDetailsButton = (TextView)findViewById(R.id.ticketDetailsButton);
        shareButton = (ImageView)findViewById(R.id.shareButton);
        ticketData = (RelativeLayout)findViewById(R.id.ticketDataContainer);
        ticketDetails = (RelativeLayout)findViewById(R.id.ticketDetailsContainer);
    }

    private void showEventData(){
        Picasso.with(context).load(Constants.DOMAIN+handler.event.imageUrl).into(eventImage);
        eventTitle.setText(handler.event.title);
        eventDescription.setText(handler.event.description);
        if(handler.ticket!= null){
            showTicketData();
        }else{
            showBuyTicket();
        }
    }

    private void setBehaviour(){
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.shareEvent();
            }
        });

    }


    private void showBuyTicket(){
        buyTicketButton.setVisibility(View.VISIBLE);
        buyTicketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.buyTicket();
            }
        });
    }
    private void showTicketData(){
        ticketData.setVisibility(View.VISIBLE);
        showTicketStatusData();
        TextView ticketDetailsButton = (TextView)findViewById(R.id.ticketDetailsButton);
        ticketDetailsButton.setVisibility(View.VISIBLE);
        ticketDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeTicketDetails();
                view.setVisibility(View.GONE);
            }
        });
    }

    private void seeTicketDetails(){
        ticketDetails.setVisibility(View.VISIBLE);
        TextView ticketCodeText  = (TextView)findViewById(R.id.ticketCodeText);
        ticketCodeText.setText(handler.ticket.code);
        ImageView qrImage = (ImageView)findViewById(R.id.qrCodeImage);
        Picasso.with(context).load(Constants.DOMAIN+handler.ticket.qrImageUrl).into(qrImage);
    }

    private void showTicketStatusData(){
        TicketStatus ticketStatus = TicketStatus.get(handler.ticket.status);
        if(ticketStatus != null) {
            ImageView ticketStatusImage = (ImageView) findViewById(R.id.ticketStatusImage);
            Picasso.with(context).load(ticketStatus.imageRes).into(ticketStatusImage);
            TextView ticketStatusText = (TextView) findViewById(R.id.ticketStatusText);
            ticketStatusText.setText(ticketStatus.text);
            if(ticketStatus.id == TicketStatus.WAITING) {
                ticketStatusText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler.showMakePaymentPopUp();
                    }
                });
            }
        }
    }


}

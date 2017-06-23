package com.naicop.naicopapp.Entitites;

import com.naicop.naicopapp.R;

/**
 * Created by pazos on 22-Jun-17.
 */
public class TicketStatus {

    public final static int WAITING = 0;
    public final static int OK = 1;


    public int id;
    public int imageRes;
    public String text;

    public TicketStatus(int id, int imageRes, String text) {
        this.id = id;
        this.imageRes = imageRes;
        this.text = text;
    }

    public static TicketStatus get(int id){
        switch (id){
            case WAITING:
                return new TicketStatus(WAITING, R.drawable.ticket_waiting_status,"Realizar Pago");
            case OK:
                return new TicketStatus(OK,R.drawable.ticket_status_ok,"Pago Ok");
            default:return null;
        }
    }
}

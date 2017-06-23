package com.naicop.naicopapp.Entitites;

import android.database.Cursor;

import com.naicop.naicopapp.Persistance.TicketSQL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pazos on 22-Jun-17.
 */
public class Ticket {

    public int id;
    public int eventId;
    public int userId;
    public String qrImageUrl;
    public boolean used;
    public String code;
    public int status;
    public boolean deleted;

    public Ticket(Cursor modelCursor){
        this.id = modelCursor.getInt(TicketSQL._id.first);
        this.eventId = modelCursor.getInt(TicketSQL.eventId.first);
        this.userId = modelCursor.getInt(TicketSQL.userId.first);
        this.qrImageUrl = modelCursor.getString(TicketSQL.qrImageUrl.first);
        this.used = modelCursor.getInt(TicketSQL.used.first)==1;
        this.code = modelCursor.getString(TicketSQL.code.first);
        this.status = modelCursor.getInt(TicketSQL.status.first);
        this.deleted = modelCursor.getInt(TicketSQL.deleted.first) == 1;
    }

    public Ticket(JSONObject jsonTicket) throws JSONException {
        this.id = jsonTicket.getInt("ID");
        this.eventId = jsonTicket.getInt("EventID");
        this.userId = jsonTicket.getInt("UserID");
        this.qrImageUrl = jsonTicket.getString("QrImageUrl");
        this.used = jsonTicket.getInt("Used") == 1;
        this.code = jsonTicket.getString("Code");
        this.status = jsonTicket.getInt("Status");
        this.deleted = jsonTicket.getInt("Deleted") == 1;
    }
}

package com.naicop.naicopapp.Persistance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.Pair;

import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pazos on 22-Jun-17.
 */
public class TicketSQL {
    public static Pair<Integer, String> _id = new Pair(0, "_id");
    public static Pair<Integer, String> eventId = new Pair(1, "eventId");
    public static Pair<Integer, String> userId = new Pair(2, "userId");
    public static Pair<Integer, String> qrImageUrl = new Pair(3, "qrImageUrl");
    public static Pair<Integer, String> used = new Pair(4, "used");
    public static Pair<Integer, String> code = new Pair(5, "code");
    public static Pair<Integer, String> status = new Pair(6, "status");
    public static Pair<Integer, String> deleted = new Pair(7, "deleted");

    public static final String TABLE_NAME = "Ticket";

    public static final String CREATION_STRING = "CREATE TABLE " + TABLE_NAME + " ("
            + TicketSQL._id.second + " INTEGER PRIMARY KEY, "
            + TicketSQL.eventId.second + " INTEGER,"
            + TicketSQL.userId.second + " INTEGER,"
            + TicketSQL.qrImageUrl.second + " TEXT,"
            + TicketSQL.used.second + " INTEGER,"
            + TicketSQL.code.second + " TEXT,"
            + TicketSQL.status.second + " INTEGER,"
            + TicketSQL.deleted.second + " INTEGER )";

    private static void insert(SQLiteDatabase db, Ticket ticket) {

        ContentValues cv = new ContentValues();
        cv.put(_id.second, ticket.id);
        cv.put(eventId.second,ticket.eventId);
        cv.put(userId.second,ticket.userId);
        cv.put(qrImageUrl.second,ticket.qrImageUrl);
        cv.put(used.second,ticket.used);
        cv.put(code.second,ticket.code);
        cv.put(status.second,ticket.status);
        cv.put(deleted.second, ticket.deleted);
        db.insert(TABLE_NAME, _id.second, cv);
    }

    private static boolean update(SQLiteDatabase db, Ticket ticket) {

        ContentValues cv = new ContentValues();
        cv.put(eventId.second,ticket.eventId);
        cv.put(userId.second,ticket.userId);
        cv.put(qrImageUrl.second,ticket.qrImageUrl);
        cv.put(used.second,ticket.used);
        cv.put(code.second,ticket.code);
        cv.put(status.second,ticket.status);
        cv.put(deleted.second, ticket.deleted);

        db.update(TABLE_NAME, cv, _id.second + " = ?", new String[]{"" + ticket.id});

        return true;
    }

    private static boolean exists(SQLiteDatabase db, int id) {

        Cursor cur = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME + " WHERE  " + _id.second + "= " + id, null);
        cur.moveToFirst();
        int count = cur.getInt(0);
        cur.close();

        boolean exists = false;
        if (count == 1)
            exists = true;

        return exists;
    }

    public static String updateAllIncoming(SQLiteDatabase db,JSONArray array) throws JSONException {

        String lastUpdated= Constants.BEGINNING_OF_TIME;
        for (int i = 0; i < array.length(); i++) {
            JSONObject ticketObj = array.getJSONObject(i);
            Ticket ticket = new Ticket(ticketObj);
            Ticket oldTicket=get(ticket.id);
            if (oldTicket!=null)
                update(db, ticket);
            else
                insert(db, ticket);

            if(ticketObj.has("UpdatedOn") && ticketObj.getString("UpdatedOn").compareTo(lastUpdated)>0)
                lastUpdated=ticketObj.getString("UpdatedOn");
        }

        return lastUpdated;
    }

    public static void updateIncoming(JSONObject jsonObj) throws JSONException {

        SQLiteDatabase db=DatabaseHelper.getInstance().getWritableDatabase();
        Ticket ticket = new Ticket(jsonObj);
        Ticket oldTicket=get(ticket.id);

        if(oldTicket!=null)
            update(db, ticket);
        else
            insert(db, ticket);

    }

    public static ArrayList<Ticket> getAllFromUser(int currentUserId) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+deleted.second + "=0 AND "+userId.second+" = "+currentUserId;
        Cursor modelCursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(query, null);

        ArrayList<Ticket> list = new ArrayList();

        if (modelCursor.moveToFirst()) {
            do {
                Ticket object = new Ticket(modelCursor);
                list.add(object);
            }
            while (modelCursor.moveToNext());
        }
        modelCursor.close();

        return list;
    }

    public static Ticket get(int id) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TicketSQL._id.second + "=" + id;
        Cursor modelCursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(query, null);

        Ticket object = null;

        if (modelCursor.moveToFirst()) {
            object = new Ticket(modelCursor);
        }
        modelCursor.close();


        return object;
    }
    public static Ticket getFromUserAndEvent(int userId ,int eventId) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TicketSQL.userId.second + "=" + userId+
                " AND "+TicketSQL.eventId.second +" = "+eventId+" AND deleted = 0";
        Cursor modelCursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(query, null);

        Ticket object = null;

        if (modelCursor.moveToFirst()) {
            object = new Ticket(modelCursor);
        }
        modelCursor.close();


        return object;
    }
}

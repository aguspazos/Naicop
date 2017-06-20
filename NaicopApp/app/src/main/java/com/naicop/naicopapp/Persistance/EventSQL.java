package com.naicop.naicopapp.Persistance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.Pair;

import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Category;
import com.naicop.naicopapp.Entitites.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pazos on 20-Jun-17.
 */
public class EventSQL {
    public static Pair<Integer, String> _id = new Pair(0, "_id");
    public static Pair<Integer, String> title = new Pair(1, "title");
    public static Pair<Integer,String> description = new Pair<>(2,"description");
    public static Pair<Integer,String> imageUrl = new Pair<>(3,"imageUrl");
    public static Pair<Integer,String> latitude = new Pair<>(4,"latitude");
    public static Pair<Integer,String> longitude = new Pair<>(5,"longitude");
    public static Pair<Integer,String> maxCapacity = new Pair<>(6,"maxCapacity");
    public static Pair<Integer,String> categoryId = new Pair<>(7,"categoryId");
    public static Pair<Integer,String> clientUserId = new Pair<>(8,"clientUserId");
    public static Pair<Integer,String> startDate = new Pair<>(9,"startDate");
    public static Pair<Integer,String> endDate = new Pair<>(10,"endDate");
    public static Pair<Integer,String> price = new Pair<>(11,"price");
    public static Pair<Integer,String> deleted = new Pair<>(12,"deleted");


    public static final String TABLE_NAME = "Event";

    public static final String CREATION_STRING = "CREATE TABLE " + TABLE_NAME + " ("
            + EventSQL._id.second + " INTEGER PRIMARY KEY, "
            + EventSQL.title.second + " TEXT,"
            + EventSQL.description.second + " TEXT,"
            + EventSQL.imageUrl.second + " TEXT,"
            + EventSQL.latitude.second + " TEXT,"
            + EventSQL.longitude.second + " TEXT,"
            + EventSQL.maxCapacity.second + " INTEGER,"
            + EventSQL.categoryId.second + " INTEGER,"
            + EventSQL.clientUserId.second + " INTEGER,"
            + EventSQL.startDate.second + " TEXT,"
            + EventSQL.endDate.second + " TEXT,"
            + EventSQL.price.second + " DOUBLE,"
            + EventSQL.deleted.second + " INTEGER )";

    private static void insert(SQLiteDatabase db, Event event) {

        ContentValues cv = new ContentValues();
        cv.put(_id.second, event.id);
        cv.put(title.second,event.title);
        cv.put(description.second,event.description);
        cv.put(imageUrl.second,event.imageUrl);
        cv.put(latitude.second,event.latitude);
        cv.put(longitude.second,event.longitude);
        cv.put(maxCapacity.second,event.maxCapacity);
        cv.put(categoryId.second,event.categoryId);
        cv.put(clientUserId.second,event.clientUserId);
        cv.put(startDate.second,event.startDate);
        cv.put(endDate.second,event.endDate);
        cv.put(price.second,event.price);
        cv.put(deleted.second, event.deleted);
        db.insert(TABLE_NAME, _id.second, cv);
    }

    private static boolean update(SQLiteDatabase db, Event event) {

        ContentValues cv = new ContentValues();
        cv.put(title.second,event.title);
        cv.put(description.second,event.description);
        cv.put(imageUrl.second,event.imageUrl);
        cv.put(latitude.second,event.latitude);
        cv.put(longitude.second,event.longitude);
        cv.put(maxCapacity.second,event.maxCapacity);
        cv.put(categoryId.second,event.categoryId);
        cv.put(clientUserId.second,event.clientUserId);
        cv.put(startDate.second,event.startDate);
        cv.put(endDate.second,event.endDate);
        cv.put(price.second,event.price);
        cv.put(deleted.second, event.deleted);

        db.update(TABLE_NAME, cv, _id.second + " = ?", new String[]{"" + event.id});

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
            JSONObject eventObj = array.getJSONObject(i);
            Event event = new Event(eventObj);
            Event oldEvent=get(event.id);
            if (oldEvent!=null)
                update(db, event);
            else
                insert(db, event);

            if(eventObj.has("UpdatedOn") && eventObj.getString("UpdatedOn").compareTo(lastUpdated)>0)
                lastUpdated=eventObj.getString("UpdatedOn");
        }

        return lastUpdated;
    }

    public static void updateIncoming(JSONObject jsonObj) throws JSONException {

        SQLiteDatabase db=DatabaseHelper.getInstance().getWritableDatabase();
        Event event = new Event(jsonObj);
        Event oldEvent=get(event.id);

        if(event!=null)
            update(db, event);
        else
            insert(db, event);

    }

    public static ArrayList<Event> getAll() {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+deleted.second + "=0";
        Cursor modelCursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(query, null);

        ArrayList<Event> list = new ArrayList();

        if (modelCursor.moveToFirst()) {
            do {
                Event object = new Event(modelCursor);
                list.add(object);
            }
            while (modelCursor.moveToNext());
        }
        modelCursor.close();

        return list;
    }

    public static Event get(int id) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + EventSQL._id.second + "=" + id;
        Cursor modelCursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(query, null);

        Event object = null;

        if (modelCursor.moveToFirst()) {
            object = new Event(modelCursor);
        }
        modelCursor.close();


        return object;
    }
}

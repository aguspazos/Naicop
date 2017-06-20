package com.naicop.naicopapp.Entitites;

import android.database.Cursor;

import com.naicop.naicopapp.Persistance.EventSQL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pazos on 20-Jun-17.
 */
public class Event {
    public int id;
    public String title;
    public String description;
    public String imageUrl;
    public String latitude;
    public String longitude;
    public int maxCapacity;
    public int categoryId;
    public int clientUserId;
    public String startDate;
    public String endDate;
    public double price;
    public boolean deleted;

    public Event(Cursor modelCursor){
        this.id = modelCursor.getInt(EventSQL._id.first);
        this.title = modelCursor.getString(EventSQL.title.first);
        this.description = modelCursor.getString(EventSQL.description.first);
        this.imageUrl = modelCursor.getString(EventSQL.imageUrl.first);
        this.latitude = modelCursor.getString(EventSQL.latitude.first);
        this.longitude = modelCursor.getString(EventSQL.longitude.first);
        this.maxCapacity = modelCursor.getInt(EventSQL.maxCapacity.first);
        this.categoryId = modelCursor.getInt(EventSQL.categoryId.first);
        this.clientUserId = modelCursor.getInt(EventSQL.clientUserId.first);
        this.startDate = modelCursor.getString(EventSQL.startDate.first);
        this.endDate = modelCursor.getString(EventSQL.endDate.first);
        this.price = modelCursor.getDouble(EventSQL.price.first);
        this.deleted = modelCursor.getInt(EventSQL.deleted.first) == 1;
    }

    public Event (JSONObject jsonEvent) throws JSONException {
        this.id = jsonEvent.getInt("ID");
        this.title = jsonEvent.getString("Title");
        this.description = jsonEvent.getString("Description");
        this.imageUrl = jsonEvent.getString("ImageUrl");
        this.latitude = jsonEvent.getString("Latitude");
        this.longitude = jsonEvent.getString("Longitude");
        this.maxCapacity = jsonEvent.getInt("MaxCapacity");
        this.categoryId = jsonEvent.getInt("CategoryID");
        this.clientUserId = jsonEvent.getInt("ClientUserID");
        this.startDate = jsonEvent.getString("StartDate");
        this.endDate = jsonEvent.getString("EndDate");
        this.price = jsonEvent.getDouble("Price");
        this.deleted = jsonEvent.getInt("Deleted") == 1;

    }

}

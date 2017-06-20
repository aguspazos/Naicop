package com.naicop.naicopapp.Entitites;

import android.database.Cursor;

import com.naicop.naicopapp.Persistance.CategorySQL;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pazos on 18-Jun-17.
 */
public class Category {

    public int id;
    public String name;
    public boolean deleted;

    public Category(Cursor modelCursor){
        this.id = modelCursor.getInt(CategorySQL._id.first);
        this.name = modelCursor.getString(CategorySQL.name.first);
        this.deleted = modelCursor.getInt(CategorySQL.deleted.first) == 1;
    }
    public Category(JSONObject jsonCategory) throws JSONException {
        this.id = jsonCategory.getInt("ID");
        this.name = jsonCategory.getString("Name");
        this.deleted = jsonCategory.has("Deleted") && jsonCategory.getInt("Deleted") == 1;
    }

}

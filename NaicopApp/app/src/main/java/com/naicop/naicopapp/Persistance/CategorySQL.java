package com.naicop.naicopapp.Persistance;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.util.Pair;

import com.naicop.naicopapp.Config.Constants;
import com.naicop.naicopapp.Entitites.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pazos on 18-Jun-17.
 */
public class CategorySQL {
    public static Pair<Integer, String> _id = new Pair(0, "_id");
    public static Pair<Integer, String> name = new Pair(1, "name");
    public static Pair<Integer, String> deleted = new Pair(2, "deleted");

    public static final String TABLE_NAME = "Category";

    public static final String CREATION_STRING = "CREATE TABLE " + TABLE_NAME + " ("
            + CategorySQL._id.second + " INTEGER PRIMARY KEY, "
            + CategorySQL.name.second + " TEXT,"
            + CategorySQL.deleted.second + " INTEGER )";

    private static void insert(SQLiteDatabase db, Category category) {

        ContentValues cv = new ContentValues();
        cv.put(_id.second, category.id);
        cv.put(name.second,category.name);
        cv.put(deleted.second, category.deleted);
        db.insert(TABLE_NAME, _id.second, cv);
    }

    private static boolean update(SQLiteDatabase db, Category category) {

        ContentValues cv = new ContentValues();
        cv.put(name.second,category.name);
        cv.put(deleted.second, category.deleted);

        db.update(TABLE_NAME, cv, _id.second + " = ?", new String[]{"" + category.id});

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
            JSONObject categoryObj = array.getJSONObject(i);
            Category category = new Category(categoryObj);
            Category oldCategory=get(category.id);
            if (oldCategory!=null)
                update(db, category);
            else
                insert(db, category);

            if(categoryObj.has("UpdatedOn") && categoryObj.getString("UpdatedOn").compareTo(lastUpdated)>0)
                lastUpdated=categoryObj.getString("UpdatedOn");
        }

        return lastUpdated;
    }

    public static void updateIncoming(JSONObject jsonObj) throws JSONException {

        SQLiteDatabase db=DatabaseHelper.getInstance().getWritableDatabase();
        Category category = new Category(jsonObj);
        Category oldCategory=get(category.id);

        if(oldCategory!=null)
            update(db, category);
        else
            insert(db, category);

    }

    public static ArrayList<Category> getAll() {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+deleted.second + "=0";
        Cursor modelCursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(query, null);

        ArrayList<Category> list = new ArrayList();

        if (modelCursor.moveToFirst()) {
            do {
                Category object = new Category(modelCursor);
                list.add(object);
            }
            while (modelCursor.moveToNext());
        }
        modelCursor.close();

        return list;
    }

    public static Category get(int id) {

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CategorySQL._id.second + "=" + id;
        Cursor modelCursor = DatabaseHelper.getInstance().getReadableDatabase().rawQuery(query, null);

        Category object = null;

        if (modelCursor.moveToFirst()) {
            object = new Category(modelCursor);
        }
        modelCursor.close();


        return object;
    }
}

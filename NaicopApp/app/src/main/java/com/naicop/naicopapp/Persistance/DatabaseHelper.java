package com.naicop.naicopapp.Persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.naicop.naicopapp.Entitites.Category;

import java.util.ArrayList;


/**
 * Created by pazos on 10/16/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME ="naicop.db";
    public static final int DATABASE_VERSION=1;

    private static DatabaseHelper myInstance;
    private static Context myContext;

    public static void setContext(Context contextParam){
        myContext=contextParam;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null,DATABASE_VERSION);
        myContext=context;
    }

    public static DatabaseHelper getInstance(){
        if(myInstance==null && myContext!=null)
            myInstance=new DatabaseHelper(myContext);

        return myInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }

    private void createDatabase(SQLiteDatabase db){

        db.execSQL(CategorySQL.CREATION_STRING);
        db.execSQL(EventSQL.CREATION_STRING);
        db.execSQL(TicketSQL.CREATION_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newerVersion) {

        switch (olderVersion){
        }
    }

    public void DropDatabaseForLogout(){
        dropDatabase(this.getWritableDatabase());
        createDatabase(this.getWritableDatabase());
    }

    private void dropDatabase(SQLiteDatabase db){
        db.execSQL("DROP TABLE IF EXITS "+TicketSQL.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXITS "+EventSQL.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXITS "+CategorySQL.TABLE_NAME);
    }




}

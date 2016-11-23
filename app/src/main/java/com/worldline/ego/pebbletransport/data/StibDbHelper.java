package com.worldline.ego.pebbletransport.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * Created by a143210 on 23/11/2016.
 */
public class StibDbHelper extends SQLiteOpenHelper{
    private static final String CREATE_TABLE="create table "+
            Constants.TABLE_NAME+" ("+
            Constants.KEY_ID+" integer primary key autoincrement, "+
            Constants.LINE_ID+" text not null, "+
            Constants.MODE+" text not null, "+
            Constants.FROM_DESTINATION+" text not null, "+
            Constants.FROM_DESTINATION_ID+" integer, "+
            Constants.TO_DESTINATION+" text not null, "+
            Constants.TO_DESTINATION_ID+" integer, "+
            Constants.BG_COLOR+" text not null, "+
            Constants.FG_COLOR+" text not null);";

    public StibDbHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v("MyDBhelper onCreate","Creating all the tables");
        try {
            db.execSQL(CREATE_TABLE);
        } catch(SQLiteException ex) {
            Log.v("Create table exception", ex.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version "+oldVersion
                +" to "+newVersion
                +", which will destroy all old data");
        db.execSQL("drop table if exists "+Constants.TABLE_NAME);
        onCreate(db);
    }
}

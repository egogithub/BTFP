package com.worldline.ego.pebbletransport.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;


/**
 * Created by a143210 on 23/11/2016.
 */

public class StibDB {
    private SQLiteDatabase mDb;
    private final Context mContext;
    private final StibDbHelper mDbHelper;

    public StibDB(Context context) {
        this.mContext=context;
        mDbHelper = new StibDbHelper(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    public void close(){
        mDb.close();
    }

    public void open() throws SQLiteException {
        try {
            mDb = mDbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            Log.v("Open database exception", ex.getMessage());
            mDb = mDbHelper.getReadableDatabase();
        }
    }

    public long insertRecord ( String lineid,
                               String mode,
                               String fromdestination,
            int fromdestinationid,
            String todestination,
            int todestinationid,
            String bgcolor,
            String fgcolor) {
        try{
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put(Constants.LINE_ID, lineid);
            newTaskValue.put(Constants.MODE, mode);
            newTaskValue.put(Constants.FROM_DESTINATION, fromdestination);
            newTaskValue.put(Constants.FROM_DESTINATION_ID, fromdestinationid);
            newTaskValue.put(Constants.TO_DESTINATION, todestination);
            newTaskValue.put(Constants.TO_DESTINATION_ID, todestinationid);
            newTaskValue.put(Constants.BG_COLOR, bgcolor);
            newTaskValue.put(Constants.FG_COLOR, fgcolor);

            return mDb.insert(Constants.TABLE_NAME, null, newTaskValue);
        } catch(SQLiteException ex) {
            Log.v("DB insert exception",
                    ex.getMessage());
            return -1;
        }
    }


    public Cursor getdiaries()
    {
        Cursor c = mDb.query(Constants.TABLE_NAME, null, null,
                null, null, null, null);
        return c;
    }
}


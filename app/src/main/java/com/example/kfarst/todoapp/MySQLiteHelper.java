package com.example.kfarst.todoapp;

/**
 * Created by kfarst on 6/7/16.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_LIST_ITEMS = "list_items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_POSITION = "pos";
    public static final String COLUMN_DUE_DATE = "due_date";

    private static final String DATABASE_NAME = "list_items.db";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_LIST_ITEMS + "(" + COLUMN_ID
            + " integer primary key autoincrement, "
            + COLUMN_POSITION + " integer not null,"
            + COLUMN_DUE_DATE + " datetime not null,"
            + COLUMN_TEXT + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIST_ITEMS);
        onCreate(db);
    }

}

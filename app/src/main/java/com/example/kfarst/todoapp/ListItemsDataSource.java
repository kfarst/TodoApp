package com.example.kfarst.todoapp;

/**
 * Created by kfarst on 6/7/16.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ListItemsDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TEXT,
            MySQLiteHelper.COLUMN_POSITION };

    public ListItemsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ListItem createListItem(String text, int pos) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TEXT, text);
        values.put(MySQLiteHelper.COLUMN_POSITION, pos);
        long insertId = database.insert(MySQLiteHelper.TABLE_LIST_ITEMS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_LIST_ITEMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ListItem newListItem = cursorToListItem(cursor);
        cursor.close();
        return newListItem;
    }

    public ListItem updateListItem(ListItem item) {
        long id = item.getId();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_TEXT, item.getText());
        values.put(MySQLiteHelper.COLUMN_POSITION, item.getPos());
        int editedID = database.update(MySQLiteHelper.TABLE_LIST_ITEMS, values, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        System.out.println("ListItem id that was edit : " + editedID);
        return item;

    }

    public ListItem deleteListItem(ListItem item) {
        long id = item.getId();
        System.out.println("ListItem deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_LIST_ITEMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
        return item;
    }

    public List<ListItem> getAllListItems() {
        List<ListItem> listItems = new ArrayList<ListItem>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LIST_ITEMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ListItem comment = cursorToListItem(cursor);
            listItems.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listItems;
    }

    private ListItem cursorToListItem(Cursor cursor) {
        ListItem item = new ListItem();
        item.setId(cursor.getLong(0));
        item.setText(cursor.getString(1));
        item.setPos(cursor.getInt(2));
        return item;
    }
}


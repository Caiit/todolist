package com.example.cait.lagrand_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

class DBhelper extends SQLiteOpenHelper{

    // Set fields of database schemas
    private static final String DATABASE_NAME = "toDoDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "toDoList";

    // Set db item names
    private static final String todo_id = "todo";
    private static final String date_id = "date";
    private static final String checked_id = "checked";

    // Constructor
    DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // OnCreate
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create the table
        String CREATE_TABLE = "CREATE TABLE " + TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
                                              + todo_id + " TEXT, " + date_id + " TEXT, "
                                              + checked_id + " INTEGER)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    // OnUpgrade
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }

    // Create
    void create(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todo_id, task.task);
        values.put(date_id, task.date);
        values.put(checked_id, task.checked);
        db.insert(TABLE, null, values);
        db.close();
    }

    // Read
    ArrayList<HashMap<String, String>> read() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id , " + todo_id + " , " + date_id + " , " + checked_id + " FROM " + TABLE;
        ArrayList<HashMap<String, String>> todos = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> task = new HashMap<>();
                task.put("id", cursor.getString(cursor.getColumnIndex("_id")));
                task.put("task", cursor.getString(cursor.getColumnIndex(todo_id)));
                task.put("date", cursor.getString(cursor.getColumnIndex(date_id)));
                task.put("checked", cursor.getString(cursor.getColumnIndex(checked_id)));
                todos.add(task);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return todos;
    }

    // Update
    void update(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(todo_id, task.task);
        values.put(date_id, task.date);
        values.put(checked_id, task.checked);
        db.update(TABLE, values, "_id = ? ", new String[] {String.valueOf(task.id)});
        db.close();
    }

    // Delete
    void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE, " _id = ? ", new String[] {String.valueOf(id)});
        db.close();
    }

    // Get item by id
    HashMap<String, String> getItemByID(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT _id , " + todo_id + " , " + date_id + " , " + checked_id +
                       " FROM " + TABLE + " WHERE _id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        HashMap<String, String> task = null;
        if (cursor.moveToFirst()) {
            task = new HashMap<>();
            task.put("id", cursor.getString(cursor.getColumnIndex("_id")));
            task.put("task", cursor.getString(cursor.getColumnIndex(todo_id)));
            task.put("date", cursor.getString(cursor.getColumnIndex(date_id)));
            task.put("checked", cursor.getString(cursor.getColumnIndex(checked_id)));

        }
        cursor.close();
        db.close();
        return task;
    }

}

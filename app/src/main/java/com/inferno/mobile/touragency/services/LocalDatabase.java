package com.inferno.mobile.touragency.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;

public class LocalDatabase extends SQLiteOpenHelper {
    private final static String TAG = LocalDatabase.class.getName();
    private final static String TABLE_NAME = "tours";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_TOUR_ID = "tour_id";
    private final static String COLUMN_ADDED_IN_TIME = "added_in_time";
    private final static String COLUMN_CHECKED_OUT = "checked_out";

    public LocalDatabase(Context context) {
        super(context, "TourAgency2.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TOUR_ID + " INTEGER," +
                COLUMN_CHECKED_OUT + " INTEGER," +
                COLUMN_ADDED_IN_TIME + " TEXT" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long addToCart(String token, int tourId) {
        Calendar calendar = Calendar.getInstance();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TOUR_ID, tourId);
        values.put(COLUMN_ADDED_IN_TIME, String.valueOf(calendar.getTimeInMillis()));
        values.put(COLUMN_CHECKED_OUT, 0);
        long insertId = db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "addToCart: " + insertId);
        return insertId;
    }

    public boolean checkIfInCart(int tourId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_ID};
        String selection = COLUMN_TOUR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(tourId)};
        Cursor cursor = db.query(TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
        boolean isInCart = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isInCart;
    }

    public boolean removeFromCart(int tourId) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_TOUR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(tourId)};
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
        Log.d(TAG, "removeFromCart: " + deletedRows);
        return deletedRows > 0;
    }

    public Integer findTourInCart(int tourId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_ID};
        String selection = COLUMN_TOUR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(tourId)};
        Cursor cursor = db.query(TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
        int id = -1;
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(COLUMN_ID);
            id = cursor.getInt(index);
        }
        cursor.close();
        db.close();
        Log.d(TAG, "findTourInCart: " + id);
        return id;
    }

    public ArrayList<Pair<Integer,Boolean>> getAllFromCart() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_TOUR_ID,COLUMN_CHECKED_OUT};
        Cursor cursor = db.query(TABLE_NAME, projection,
                null, null,
                null, null, null);
        ArrayList<Pair<Integer,Boolean>> tours = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(COLUMN_TOUR_ID);
            int checkedOut = cursor.getColumnIndex(COLUMN_CHECKED_OUT);
            do {
                tours.add(new Pair<>(cursor.getInt(index), cursor.getInt(checkedOut) == 1));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "getAllFromCart: " + tours);
        return tours;
    }

    public int checkIfCheckedOut(int tourId) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_CHECKED_OUT};
        String selection = COLUMN_TOUR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(tourId)};
        Cursor cursor = db.query(TABLE_NAME, projection, selection,
                selectionArgs, null, null, null);
        int checkedOut = -1;
        if (cursor.moveToFirst()) {
            int index = cursor.getColumnIndex(COLUMN_CHECKED_OUT);
            checkedOut = cursor.getInt(index);
        }
        cursor.close();
        db.close();
        Log.d(TAG, "checkIfCheckedOut: " + checkedOut);
        return checkedOut;
    }

    public boolean checkOut(int tourId) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_TOUR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(tourId)};
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHECKED_OUT, 1);
        int updatedRows = db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
        Log.d(TAG, "checkOut: " + updatedRows);
        return updatedRows > 0;
    }
}

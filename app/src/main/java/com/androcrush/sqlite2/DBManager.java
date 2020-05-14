package com.androcrush.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;




    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name, String desc, String category, int amount) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        contentValue.put(DatabaseHelper.DESC, desc);
        contentValue.put(DatabaseHelper.CAT, category);
        contentValue.put(DatabaseHelper.AMT, amount);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public long getProfilesCount() {
        String countQuery = "SELECT  * FROM " + DatabaseHelper.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //public int getcatsum(String cat ) {
//        String query = "SELECT SUM(amount) FROM COUNTRIES WHERE category =" + cat;
////        SQLiteDatabase db = dbHelper.getReadableDatabase();
////        Cursor cursor = db.rawQuery(query, null);
//////        mCount = db.rawQuery(query, null);
//////        mCount.moveToFirst();
    public int getSumValue(String cat){
        int sum=0;
        //SQLiteDatabase database=dbHelper.getReadableDatabase();
        String sumQuery=String.format("SELECT SUM(%s) as Total FROM %s where category= %s","amount","COUNTRIES","cat");
        Cursor cursor=database.rawQuery(sumQuery,null);
        if(cursor.moveToFirst())
            sum= cursor.getInt(cursor.getColumnIndex("Total"));
        return sum;
//        int count = cursor.getInt(0);
//        return count;
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT, DatabaseHelper.DESC,DatabaseHelper.CAT,DatabaseHelper.AMT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int update(long _id, String name, String desc, String category, int amount) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        contentValues.put(DatabaseHelper.DESC, desc);
        contentValues.put(DatabaseHelper.CAT, category);
        contentValues.put(DatabaseHelper.AMT, amount);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id, null);
    }

}
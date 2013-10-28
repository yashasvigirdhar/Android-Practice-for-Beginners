package com.example.wonders;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SqlHandler {
	public static final String DATABASE_NAME = "MY_DATABASE";
	 public static final int DATABASE_VERSION = 1;
	 Context context;
	 SQLiteDatabase sqlDatabase;
	 SqlDbHelper dbHelper;
	 
	 public SqlHandler(Context context) {
	 
	  dbHelper = new SqlDbHelper(context, DATABASE_NAME, null,
	    DATABASE_VERSION);
	  sqlDatabase = dbHelper.getWritableDatabase();
	 }
	 
	 public void executeQuery(String query) {
	  try {
	 
	   if (sqlDatabase.isOpen()) {
	    sqlDatabase.close();
	   }
	 
	   sqlDatabase = dbHelper.getWritableDatabase();
	   sqlDatabase.execSQL(query);
	 
	  } catch (Exception e) {
	 
	   System.out.println("DATABASE ERROR " + e);
	  }
	 
	 }
	 
	 public Cursor selectQuery(String query) {
	  Cursor c1 = null;
	  try {
	 
	   if (sqlDatabase.isOpen()) {
	    sqlDatabase.close();
	 
	   }
	   sqlDatabase = dbHelper.getWritableDatabase();
	   c1 = sqlDatabase.rawQuery(query, null);
	 
	  } catch (Exception e) {
	 
	   System.out.println("DATABASE ERROR " + e);
	 
	  }
	  return c1;
	 
	 }
}


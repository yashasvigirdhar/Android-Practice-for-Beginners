package com.example.wonders;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 

public class SqlDbHelper extends SQLiteOpenHelper {

	public static final String DATABASE_TABLE = "PHONE_CONTACTS";
	 
	 public static final String COLUMN1 = "slno";
	 public static final String COLUMN2 = "wondername";
	 public static final String COLUMN3 = "wonderimage";
	 public static final String COLUMN4 = "wonderdesc";
	 private static final String SCRIPT_CREATE_DATABASE = "create table "
	   + DATABASE_TABLE + " (" + COLUMN1
	   + " integer primary key autoincrement, " + COLUMN2
	   + " text not null, " + COLUMN3 + " text not null, " + COLUMN4 + " text not null);";
	 
	 public SqlDbHelper(Context context, String name, CursorFactory factory,
	   int version) {
	  super(context, name, factory, version);
	  // TODO Auto-generated constructor stub
	 
	 }
	 
	 @Override
	 public void onCreate(SQLiteDatabase db) {
	  // TODO Auto-generated method stub
	  db.execSQL(SCRIPT_CREATE_DATABASE);
	 
	 }
	 
	 @Override
	 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  // TODO Auto-generated method stub
	  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	  onCreate(db);
	 }

}
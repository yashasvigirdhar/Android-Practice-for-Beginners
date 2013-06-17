package com.example.electionkhabar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StoredPreference {

	public static final String KEY_ROWID = "_id";
	public static final String KEY_TYPE = "Type";
	public static final String KEY_VALUE = "Value";

	@SuppressWarnings("unused")
	private static final String DATABASE_NAME = "Preference";
	private static final String DATABASE_TABLE = "PrefrenceFilters";
	private static final int DATABASE_VERSION = 1;

	private DbHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;

	private static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL("CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ROWID
					+ " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TYPE
					+ " TEXT NOT NULL, " + KEY_VALUE + " TEXT NOT NULL);");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			onCreate(db);
		}
	}

	public StoredPreference(Context c) {
		ourContext = c;
	}

	public StoredPreference open() {
		ourHelper = new DbHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;

	}

	public void close() {
		ourHelper.close();
	}

	public long creatEntry(String name, String hotness) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();
		cv.put(KEY_TYPE, name);
		cv.put(KEY_VALUE, hotness);
		return ourDatabase.insert(DATABASE_TABLE, null, cv);
	}

	public String getdata() {
		// TODO Auto-generated method stub
		String[] columns = new String[] { KEY_ROWID, KEY_TYPE, KEY_VALUE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";

		int iRow = c.getColumnIndex(KEY_ROWID);
		int iName = c.getColumnIndex(KEY_TYPE);
		int iHotness = c.getColumnIndex(KEY_VALUE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iRow) + "             " + c.getString(iName)
					+ "             " + c.getString(iHotness) + "\n";
		}
		return result;
	}
	public String getfilter() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_VALUE };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";

		int iValue = c.getColumnIndex(KEY_VALUE);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iValue) + "\n";
		}
		return result;
	}
	public String getnumbers() {
		// TODO Auto-generated method stub
		String[] columns = new String[] {KEY_ROWID };
		Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null,
				null, null);
		String result = "";

		int iValue = c.getColumnIndex(KEY_ROWID);

		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			result = result + c.getString(iValue) + "\n";
		}
		return result;
	}
	public  void deletebyid(String id) {
		ourDatabase.delete(DATABASE_TABLE, KEY_ROWID+"="+id,null);
	}
}

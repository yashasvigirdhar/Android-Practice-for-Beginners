package com.thenode.helloandroid;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SQLiteExample extends Activity implements OnClickListener {

	Button sqlUpdate, sqlView;
	EditText sqlname, sqlhot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqliteexample);
		sqlUpdate = (Button) findViewById(R.id.bSqlupdate);
		sqlView = (Button) findViewById(R.id.bSqlopenview);
		sqlname = (EditText) findViewById(R.id.etSqlname);
		sqlhot = (EditText) findViewById(R.id.etSqlhot);
		sqlUpdate.setOnClickListener(this);
		sqlView.setOnClickListener(this);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.bSqlopenview:
			Intent i = new Intent("com.thenode.helloandroid.SQLVIEW");
			startActivity(i);
			break;
		case R.id.bSqlupdate:

			boolean worked = true;
			try {
				String name = sqlname.getText().toString();
				String hotness = sqlhot.getText().toString();
				HotOrNot entry = new HotOrNot(SQLiteExample.this);
				entry.open();
				entry.creatEntry(name, hotness);
				entry.close();
			} catch (Exception e) {
				// TODO: handle exception
				worked = false;
				String error = e.toString();
				Dialog d = new Dialog(this);
				d.setTitle("o fuck !!");
				TextView tv = new TextView(this);
				tv.setText(error);
				d.setContentView(tv);
				d.show();
			} finally {
				if (worked) {
					Dialog d = new Dialog(this);
					d.setTitle("o yeah !!");
					TextView tv = new TextView(this);
					tv.setText("success");
					d.setContentView(tv);
					d.show();
				}
			}
			break;
		}
	}
}

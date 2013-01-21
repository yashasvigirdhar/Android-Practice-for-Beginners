package com.thenode.helloandroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SQLView extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sqlview);
		TextView  tv= (TextView) findViewById(R.id.tvSqlinfo);
		HotOrNot info=new HotOrNot(this);
		info.open();
		String data =info.getdata();
		info.close();
		tv.setText(data);
	}
}

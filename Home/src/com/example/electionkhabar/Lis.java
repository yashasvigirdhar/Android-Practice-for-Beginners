package com.example.electionkhabar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Lis extends Activity{

	String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
			  "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
			  "Linux", "OS/2" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);
		ListView lv=(ListView) findViewById(R.id.mylist);
		lv.setAdapter(new ArrayAdapter<String>(Lis.this, android.R.layout.simple_list_item_1, values));
	}
	

}

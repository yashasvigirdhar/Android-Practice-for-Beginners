package com.itsdark.myprojects.bindtoservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Second extends Activity implements OnClickListener{

	Button start, first;
	Intent i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		start = (Button)findViewById(R.id.bstart);
		start.setOnClickListener(this);
		first = (Button)findViewById(R.id.bfirst);
		first.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bfirst:
			Intent intent = new Intent(this, First.class);
			startActivity(intent);
			break;
			
		case R.id.bstart:
			
			startService(new Intent(this, LocalService.class));;
			//startActivity(i);
			break;
		}
		
	}
}

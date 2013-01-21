package com.example.electionkhabar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.Loader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity implements OnClickListener, OnTouchListener {

	TextView topnews, parties, politicians, mypaper, newspapers,discussions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		topnews = (TextView) findViewById(R.id.tvTopNews);
		parties = (TextView) findViewById(R.id.tvParties);
		politicians = (TextView) findViewById(R.id.tvPoliticians);
		mypaper = (TextView) findViewById(R.id.tvMyPaper);
		newspapers = (TextView) findViewById(R.id.tvPapers);
		discussions=(TextView) findViewById(R.id.tvDiscussions);

		// parties.setOnClickListener(this);
		// politicians.setOnClickListener(this);
		// mypaper.setOnClickListener(this);
		// newspapers.setOnClickListener(this);

		topnews.setOnTouchListener(this);
		discussions.setOnTouchListener(this);
		politicians.setOnTouchListener(this);
		parties.setOnTouchListener(this);
		mypaper.setOnTouchListener(this);
		newspapers.setOnTouchListener(this);
		topnews.setOnTouchListener(this);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tvPoliticians:
			Intent i = new Intent(Home.this, Neta.class);
			i.putExtra("activity", "home");
			startActivityForResult(i, 0);
			break;
		case R.id.tvParties:
			Intent i1 = new Intent(Home.this, Party.class);
			startActivityForResult(i1, 1);
			break;
		case R.id.tvPapers:
			Intent i2 = new Intent(Home.this, Newspaper.class);
			startActivityForResult(i2, 1);
			break;
		case R.id.tvMyPaper:
			Intent i3 = new Intent(Home.this, MyPaper.class);
			startActivityForResult(i3, 0);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			overridePendingTransition(R.anim.slideleft, R.anim.slideleft2);
		} else {
			overridePendingTransition(R.anim.slideright, R.anim.slideright2);
		}
	}

	@SuppressWarnings("deprecation")
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tvMyPaper:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) v).setTextColor(0xffffffff);
				((TextView) v).setBackgroundColor(0xff101010);
				break;
			case MotionEvent.ACTION_UP:
				((TextView) v).setTextColor(0xFF000006);
				Drawable background = getResources().getDrawable(
						R.drawable.style);
				((TextView) v).setBackgroundDrawable(background);
				Intent i3 = new Intent(Home.this, MyPaper.class);
				startActivityForResult(i3, 0);
				break;
			}
			break;

		case R.id.tvPapers:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) v).setTextColor(0xffffffff);
				((TextView) v).setBackgroundColor(0xff101010);
				break;
			case MotionEvent.ACTION_UP:
				((TextView) v).setTextColor(0xFF000006);
				Drawable background = getResources().getDrawable(
						R.drawable.style);
				((TextView) v).setBackgroundDrawable(background);
				Intent i2 = new Intent(Home.this, Newspaper.class);
				startActivityForResult(i2, 1);
				break;
			}
			break;

		case R.id.tvPoliticians:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) v).setTextColor(0xffffffff);
				((TextView) v).setBackgroundColor(0xff101010);
				break;
			case MotionEvent.ACTION_UP:
				((TextView) v).setTextColor(0xFF000006);
				Drawable background = getResources().getDrawable(
						R.drawable.style);
				((TextView) v).setBackgroundDrawable(background);
				Intent i = new Intent(Home.this, Neta.class);
				//i.putExtra("activity", "home");
				startActivity(i);
				break;
			}
			break;

		case R.id.tvParties:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) v).setTextColor(0xffffffff);
				((TextView) v).setBackgroundColor(0xff101010);
				break;
			case MotionEvent.ACTION_UP:
				((TextView) v).setTextColor(0xFF000006);
				Drawable background = getResources().getDrawable(
						R.drawable.style);
				((TextView) v).setBackgroundDrawable(background);
				Intent i1 = new Intent(Home.this, Party.class);
				startActivityForResult(i1, 1);
				break;
			}
			break;
			
		case R.id.tvTopNews:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) v).setTextColor(0xffffffff);
				((TextView) v).setBackgroundColor(0xff101010);
				break;
			case MotionEvent.ACTION_UP:
				((TextView) v).setTextColor(0xFF000006);
				Drawable background = getResources().getDrawable(
						R.drawable.style);
				((TextView) v).setBackgroundDrawable(background);
				//Intent i3 = new Intent(Home.this, MyPaper.class);
				//startActivityForResult(i3, 0);
				break;
			}
			break;
			
		case R.id.tvDiscussions:
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				((TextView) v).setTextColor(0xffffffff);
				((TextView) v).setBackgroundColor(0xff101010);
				break;
			case MotionEvent.ACTION_UP:
				((TextView) v).setTextColor(0xFF000006);
				Drawable background = getResources().getDrawable(
						R.drawable.style);
				((TextView) v).setBackgroundDrawable(background);
			//	Intent i3 = new Intent(Home.this, MyPaper.class);
				//startActivityForResult(i3, 0);
				break;
			}
			break;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
	}

}

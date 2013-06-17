package com.example.electionkhabar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Newspaper extends Activity {

	int count = 0;

	String papers[] = { "1", "3", "4", "5", "7", "6", "2" };
	int ht[] = { 180, 90, 155, 130, 75, 165, 120 };
	String newslinks[]={"http://www.bbc.co.uk/news/world/asia/india/","http://timesofindia.indiatimes.com/india",
			"http://www.deccanchronicle.com/news/politics","http://www.ndtv.com/elections",
			"http://www.hindustantimes.com/news-feed/india-news/sid112.aspx","http://www.thehindu.com/news/national/",
			"http://www.indianexpress.com/supplement/India/798/"};
	PaperAdapter p_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slideleft, R.anim.slideleft2);
		setContentView(R.layout.newspaper);

		ListView lv = (ListView) findViewById(R.id.lvNewspaper);
		p_adapter = new PaperAdapter(Newspaper.this, papers, ht);
		lv.setAdapter(p_adapter);
		lv.setDividerHeight(4);

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
		        String url=newslinks[position];
				Bundle basket=new Bundle();
				basket.putString("keynews",url);
				Intent a = new Intent(Newspaper.this, Load.class);
				a.putExtras(basket);
				startActivity(a);

			}

		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		if (count != 0)
			overridePendingTransition(R.anim.slideright, R.anim.slideright2);
		count++;
		super.onResume();
	}
}

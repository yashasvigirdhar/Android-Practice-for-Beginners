package com.example.electionkhabar;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.Toast;

public class Party extends ListActivity {
	

	int count=0;
	
	static final String[] Parties = new String[] { "Political Parties",
			"Congress", "Bhartiya Janta Party", "Samajwadi Party",
			"Bahujan Samaj Party", "CPI(Marxist)", "Trinamool Congress", "DMK",
			"AIADMK", "Janata Dal(United)", "Shiv Sena" };

	static final String[] Logos = new String[] { "parties", "congress", "bjp",
			"sp", "bsp", "cpi", "tmc", "dmk", "aiadmk", "jdu", "shivsena" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//String act = getIntent().getExtras().getString("activity");
		
		//if (act.equalsIgnoreCase("home")) {
			overridePendingTransition(R.anim.slideleft, R.anim.slideleft2);
		//}

		//final ListView l = getListView();
		setListAdapter(new MobileArrayAdapter(this, Parties, Logos));

		// l.setBackgroundResource(R.drawable.partyhome);
		// String bgicon="drawable/partyhome";
		// int ID =getResources().getIdentifier(bgicon,null,getPackageName());
		// Drawable bgimage = getResources().getDrawable(ID);
		// l.setBackgroundDrawable(bgimage);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// get selected items

		if (position != 0) {
			String selectedValue = Logos[position];
			String name = Parties[position];
			Bundle basket = new Bundle();
			basket.putString("party", selectedValue);
			basket.putString("title", name);
			basket.putString("index", Integer.toString(position));
			Intent a = new Intent(Party.this, PartyNews.class);
			a.putExtras(basket);
			// Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
			startActivity(a);
		}

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
		
		if(count!=0)
		overridePendingTransition(R.anim.slideright, R.anim.slideright2);
		count++;
		super.onResume();
	}
	
    
}
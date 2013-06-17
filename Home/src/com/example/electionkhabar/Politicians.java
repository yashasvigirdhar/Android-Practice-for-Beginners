package com.example.electionkhabar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author yash
 *
 */
public class Politicians extends ListActivity {

	static final String[] Politicians = new String[] { "Politicains", "sonia",
			"modi", "mayawati" };

	int count=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(R.anim.slideright,R.anim.slideright2);
		
		//Toast.makeText(getApplicationContext(), "mujhe vote do",
			//	Toast.LENGTH_LONG).show();
		//final ListView l = getListView();
		
		setListAdapter(new MobileArrayAdapterPol(this, Politicians));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		if (position != 0) {
			String name = Politicians[position];
			String index = Integer.toString(position);
			Intent i = new Intent(getApplicationContext(), PoliticianNews.class);
			Bundle basket=new Bundle();
			basket.putString("name", name);
			basket.putString("index", index);
			i.putExtras(basket);
			startActivity(i);
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent returnIntent = new Intent();
		returnIntent.putExtra("activity","politicians");
		setResult(RESULT_OK,returnIntent);
		this.finish();
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		if(count!=0)
		overridePendingTransition(R.anim.slideleft, R.anim.slideleft2);
		count++;
		super.onResume();
	}
	
}

package com.example.wonders;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class LinkListActivity extends Activity implements ChangeLinkListener {

	ActionBar actionbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getApplicationContext().deleteDatabase("MY_DATABASE");
		setContentView(R.layout.activity_layout);
		// SqlHandler sqlHandler = new SqlHandler(this);
		// Model.LoadModel(sqlHandler);
		Log.d("In LinkListActivity", "On Create");
		/*
		 * if ((findViewById(R.id.fragPage1) != null) &&
		 * (findViewById(R.id.fragPage2) != null)) { modify_actionbar(); }
		 */
	}

	private void modify_actionbar() {
		// TODO Auto-generated method stub
		actionbar = getActionBar();
		actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);
		actionbar.setCustomView(R.layout.actionbar_layout);
		actionbar.setTitle("Wonders");
		actionbar.setSubtitle("developed by Yash");
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// TODO Auto-generated method stub
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog_layout);
		Button done = (Button) dialog.findViewById(R.id.bDone);
		switch (item.getItemId()) {
		case R.id.action_add:
			dialog.setTitle("Add Image");
			break;
		case R.id.action_delete:

			break;
		case R.id.action_edit:
			dialog.setTitle("Edit image");
			break;

		}
		/*done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.action_add:

					break;
				case R.id.action_edit:

					break;
				}
			}
		});*/
		dialog.show();
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLinkChange(String name) {
		System.out.println("Listener");
		// Here we detect if there's dual fragment
		if ((findViewById(R.id.fragPage1) != null)
				&& (findViewById(R.id.fragPage2) != null)) {
			WonderImageFragment wvf1 = (WonderImageFragment) getFragmentManager()
					.findFragmentById(R.id.fragPage1);
			WonderDescFragment wvf2 = (WonderDescFragment) getFragmentManager()
					.findFragmentById(R.id.fragPage2);

			if (wvf1 == null) {
				System.out.println("Dual fragment - 1");
				wvf1 = new WonderImageFragment();
				wvf1.init(name);
				// We are in dual fragment (Tablet and so on)
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();

				// wvf.updateUrl(link);
				ft.replace(R.id.fragPage1, wvf1);
				ft.commit();

			} else {
				Log.d("ListFragment", "Dual Fragment update");
				wvf1.updateName(name);
			}

			if (wvf2 == null) {
				System.out.println("Dual fragment - 1");
				wvf2 = new WonderDescFragment();
				wvf2.init(name);
				// We are in dual fragment (Tablet and so on)
				FragmentManager fm = getFragmentManager();
				FragmentTransaction ft = fm.beginTransaction();

				// wvf.updateUrl(link);
				ft.replace(R.id.fragPage2, wvf2);
				ft.commit();

			} else {
				Log.d("ListFragment", "Dual Fragment update");
				wvf2.updateName(name);
			}

		}

		else {
			Log.d("helo", "Start Activity");
			Intent i = new Intent(this, WebViewActivity.class);
			i.putExtra("name", name);
			System.out.println("YES!!!!");
			startActivity(i);
		}

	}
}

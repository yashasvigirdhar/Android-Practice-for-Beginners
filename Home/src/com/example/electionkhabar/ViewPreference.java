package com.example.electionkhabar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPreference extends Activity {

	private final Context context = this;
	String[] numbers;
	Spinner spinner;
	Button delsubs;
	String selectedsubs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpreferences);

		populatenumbers();
		getdata();
		populatespinner();
		configuredel();

	}

	private void configuredel() {
		// TODO Auto-generated method stub
		delsubs = (Button) findViewById(R.id.bDelSubs);
		delsubs.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("Are you sure?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						StoredPreference info = new StoredPreference(context);
						info.open();
						info.deletebyid(selectedsubs);
						info.close();
						getdata();
						populatenumbers();
						populatespinner();
					}
				});
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					dialog.dismiss();	
					}
				});
				AlertDialog alert = builder.create();
				alert.show();

				
			}
		});
	}

	private void getdata() {
		// TODO Auto-generated method stub
		TextView tv = (TextView) findViewById(R.id.tvSqlinfo);
		StoredPreference info = new StoredPreference(this);
		info.open();
		String data = info.getdata();
		info.close();
		tv.setText(data);
	}

	private void populatespinner() {
		// TODO Auto-generated method stub

		spinner = (Spinner) findViewById(R.id.deletespinner1);
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				ViewPreference.this, android.R.layout.simple_spinner_item,
				numbers);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				// TODO Auto-generated method stub
				selectedsubs = parent.getItemAtPosition(pos).toString();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void populatenumbers() {
		// TODO Auto-generated method stub
		StoredPreference info = new StoredPreference(this);
		info.open();
		String data = info.getnumbers();
		numbers = data.split(System.getProperty("line.separator"));
		info.close();
		// Toast.makeText(MyPaper.this, filter[0]+" "+filter[1]+" "+filter[2],
		// Toast.LENGTH_LONG).show();
	}
}

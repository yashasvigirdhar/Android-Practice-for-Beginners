package com.example.electionkhabar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MyPaperInstruct extends Activity implements OnClickListener {

	private Spinner spinner1, spinner2;
	private Button btnAdd, btnView,myPaper;
	String selectedType, selectedValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypaperinstruct);

		initialize();
		addListenerOnSpinnerItemSelection();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		btnAdd = (Button) findViewById(R.id.baddsubs);
		btnView = (Button) findViewById(R.id.bviewsubs);
		myPaper = (Button) findViewById(R.id.bMyPaper);
		btnAdd.setOnClickListener(this);
		btnView.setOnClickListener(this);
		myPaper.setOnClickListener(this);
	}

	private void addListenerOnSpinnerItemSelection() {
		// TODO Auto-generated method stub
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	public class CustomOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			
			switch (parent.getId()) {
			case R.id.spinner1:
				String[] list;

				if (parent.getItemAtPosition(pos).toString()
						.equalsIgnoreCase("Parties")) {
					list = getResources().getStringArray(R.array.Parties);
				} else {
					list = getResources().getStringArray(R.array.Politicians);
				}

				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
						parent.getContext(),
						android.R.layout.simple_spinner_item, list);
				dataAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner2.setAdapter(dataAdapter);
				selectedType = parent.getItemAtPosition(pos).toString();
				break;
			
			case R.id.spinner2:
				selectedValue = parent.getItemAtPosition(pos).toString();
				break;
			}
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.baddsubs:
			StoredPreference entry = new StoredPreference(MyPaperInstruct.this);
			entry.open();
			entry.creatEntry(selectedType, selectedValue);
			entry.close();
			break;
		case R.id.bviewsubs:
			Intent i=new Intent(MyPaperInstruct.this, ViewPreference.class);
			startActivity(i);
			break;
		case R.id.bMyPaper:
			Intent i2=new Intent(MyPaperInstruct.this, MyPaper.class);
			startActivity(i2);
			break;
		}
	}
}

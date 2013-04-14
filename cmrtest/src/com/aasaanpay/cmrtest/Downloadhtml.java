package com.aasaanpay.cmrtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Downloadhtml extends Activity {

	LinearLayout layout;
	LinearLayout.LayoutParams rootparams;
	String[] separated;
	String str = "";
	TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_downloadhtml);
		text = (TextView) findViewById(R.id.tvset);
		DownloadFile downloadFile = new DownloadFile();
		downloadFile.execute("http://175.41.157.126/12345_98765.txt");

	}

	@SuppressWarnings("deprecation")
	private void createlayout() {
		// TODO Auto-generated method stub
		layout = new LinearLayout(this); // root layout
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setWeightSum(10.0f);

		rootparams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(rootparams);

	}

	private void parsestr() {
		// TODO Auto-generated method stub
		Log.d("in parse str", str);
		separated = str.split("#");
		System.out.println(Arrays.toString(separated));
		if(separated.length<=0)
			return;
		for (int i = 0; i < separated.length; i++) {
			System.out.println(separated[i]);
			String[] values = separated[i].split("~");
			System.out.println(values[0]);
			Globals.param_title= new String[5];
			Globals.param_type = new int[5];
			
			// till here we get the key and value type
			if (values[0].equalsIgnoreCase("mid")) {
				break;
			}
			
			//copy to globals
			Globals.param_title[i] = values[0];
			
			LinearLayout layout1st = new LinearLayout(this); // child linear
																// layout
			layout.setOrientation(LinearLayout.VERTICAL);
			layout1st.setWeightSum(2f);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.weight = 2.0f;
			layout1st.setLayoutParams(params);

			TextView first = new TextView(this);

			first.setText(values[0]);
			LinearLayout.LayoutParams tvp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			tvp.weight = 1.0f;
			tvp.gravity = Gravity.CENTER;
			first.setLayoutParams(tvp);

			EditText second = new EditText(this);
			second.setEms(10);
			if (values[1].equalsIgnoreCase("Text")) {
				second.setInputType(InputType.TYPE_CLASS_TEXT);
				Globals.param_type[i] = 1;
			} else if (values[1].equalsIgnoreCase("Email")) {
				second.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
				Globals.param_type[i] = 2;
			} else if (values[1].equalsIgnoreCase("Multiline text")) {
				second.setInputType(InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
				Globals.param_type[i] = 3;
			} else if (values[1].equalsIgnoreCase("Phone number")) {
				second.setInputType(InputType.TYPE_CLASS_PHONE);
				Globals.param_type[i] = 4;
			} else if (values[1].equalsIgnoreCase("Number")) {
				second.setInputType(InputType.TYPE_CLASS_NUMBER);
				Globals.param_type[i] = 5;
			}

			LinearLayout.LayoutParams tvp2 = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.MATCH_PARENT);

			tvp2.weight = 1.0f;
			tvp2.gravity = Gravity.CENTER;
			second.setLayoutParams(tvp2);

			layout1st.addView(first);
			layout1st.addView(second);
			layout.addView(layout1st);
		}

		setContentView(layout, rootparams);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_downloadhtml, menu);
		return true;
	}

	// usually, subclasses of AsyncTask are declared inside the activity class.
	// that way, you can easily modify the UI thread from here
	private class DownloadFile extends AsyncTask<String, Integer, String> {

		String s;
		Boolean flag = false;

		@Override
		protected String doInBackground(String... sUrl) {
			// TODO Auto-generated method stub
			try {
				URL url = new URL(sUrl[0]);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						url.openStream()));

				while ((s = in.readLine()) != null) {
					str = str + s;
					System.out.println(str);
					Log.d("str", str);
					// str is one line of text; readLine() strips the newline
					// character(s)

				}
				in.close();
			} catch (Exception e) {
				flag = true;

				e.printStackTrace();
			}
			return str;
		}

		protected void onPostExecute(String result) {
			if (flag == false) {
				Log.d("in post execute", str);
				createlayout();
				parsestr();
			} else {
				Toast.makeText(Downloadhtml.this, "internet problem",
						Toast.LENGTH_LONG).show();
			}
		}

	}
	
	

}

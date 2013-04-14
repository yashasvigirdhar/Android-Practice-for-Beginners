package com.aasaanpay.cmrtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class getGPS extends Activity implements OnClickListener {

	static TextView coords;
	Button startgps, stopgps;
	Intent service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		coords = (TextView) findViewById(R.id.tvcoords);
		startgps = (Button) findViewById(R.id.bstartgps);
		startgps.setOnClickListener(this);
		stopgps = (Button) findViewById(R.id.bstopgps);
		stopgps.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bstartgps:
			service = new Intent(this, LocationService.class);
			startService(service);
			break;
		case R.id.bstopgps:
			stopService(service);
			break;
		}
	}

}
package com.example.samplegps;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service implements LocationListener {

	private static final int gpsMinTime = 1;
	private static final int gpsMinDistance = 1;

	private LocationManager manager = null;

	private String UPDATE_URL = "http://bsr.sicanet.net/ws/update.php";

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {

		Log.d(Globals.APP_LOG_TAG, "service started");
		startLoggingService();
		Globals.isServiceRunning = true;
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(Globals.APP_LOG_TAG, "service started");
		startLoggingService();
		Globals.isServiceRunning = true;

		return Service.START_REDELIVER_INTENT;// Service needs to be running
												// background all the time.
	}

	@Override
	public void onDestroy() {
		Globals.isServiceRunning = false;
		super.onDestroy();
	}

	private void startLoggingService() {
		if (manager == null) {
			manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		}
		final Criteria criteria = new Criteria();

		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		final String bestProvider = manager.getBestProvider(criteria, true);

		if (bestProvider != null && bestProvider.length() > 0) {
			manager.requestLocationUpdates(bestProvider, gpsMinTime,
					gpsMinDistance, this);
			Log.d("best Provider", bestProvider.toString());
		} else {
			final List<String> providers = manager.getProviders(true);
			Log.d("no bet provider", "no bet provider");
			for (final String provider : providers) {
				manager.requestLocationUpdates(provider, gpsMinTime,
						gpsMinDistance, this);
			}
		}
	}

	private void stopLoggingService() {
		manager.removeUpdates(LocationService.this);
		stopSelf();
	}

	public void onLocationChanged(Location location) {

		Log.d("on location changed", "on location changed");
		Globals.latitude = location.getLatitude();
		Globals.longitude = location.getLongitude();
		Globals.altitude = location.getAltitude();
		Log.d(Globals.APP_LOG_TAG, "new location @ " + Globals.longitude + " "
				+ Globals.latitude + " " + Globals.altitude);
		getGPS.coords.setText(String.valueOf(Globals.latitude));
		getGPS.coords.append("  " + String.valueOf(Globals.longitude) + "  " + String.valueOf(Globals.altitude));
		
	}

	public void onProviderDisabled(String provider) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}

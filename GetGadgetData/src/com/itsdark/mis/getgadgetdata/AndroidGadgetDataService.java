package com.itsdark.mis.getgadgetdata;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class AndroidGadgetDataService extends Service {

	private String LOG_TAG = "Service";

	NotificationManager mNM;
	ArrayList<Messenger> mClients = new ArrayList<Messenger>();
	static final int MSG_REGISTER_CLIENT = 1;
	static final int MSG_UNREGISTER_CLIENT = 2;
	static final int MSG_GET_VALUE = 3;
	final Messenger mMessenger = new Messenger(new IncomingHandler());

	private ArrayList<GadgetData> list = new ArrayList<GadgetData>();
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		showNotification("service binded");
		return mMessenger.getBinder();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mNM.cancel(1);
		Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
		super.onDestroy();

	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Log.i(LOG_TAG, "on create");
	}
	
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Log.i(LOG_TAG, "message received0");
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				populatedata();
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				break;
			case MSG_GET_VALUE:
				Log.i(LOG_TAG, "message received3");
				for (int i = mClients.size() - 1; i >= 0; i--) {
					Toast.makeText(getBaseContext(), "extracting information",
							Toast.LENGTH_SHORT).show();
					try {
						Message ms = Message.obtain(null, MSG_GET_VALUE, 1, 0);
						Bundle b = new Bundle();
						GadgetData temp = getSpecs(msg.getData().getInt("pos"));
						b.putParcelable("data", temp);
						ms.setData(b);
						mClients.get(i).send(ms);
						showNotification("data extracted");
						Log.i(LOG_TAG, "data sent");

					} catch (RemoteException e) {
						mClients.remove(i);
					}
				}
				break;
			default:
				super.handleMessage(msg);
			}
		}

	}

	private void populatedata() {
		// TODO Auto-generated method stub
		GadgetData newmodel1 = new GadgetData("Xperia M",
				"62.0mm x 124.0mm x 9.3mm", "115 g", "Android 4.1",
				"Bluetooth 4.0", "1GHz  dual-core", "5.0 megapixels",
				"microSD card", "1");
		list.add(newmodel1);

		GadgetData newmodel2 = new GadgetData("Nexus 4",
				"68.7mm x 133.9mm x 9.1mm", "Android 4.1", "139 g",
				"Bluetooth 4.0", "1500 MHz Quad core", "8.0 megapixels",
				"None", "2");
		list.add(newmodel2);

		GadgetData newmodel3 = new GadgetData("Samsung Galaxy Ace",
				"59.9mm x 112.4mm x 11.5mm", "113 g", "Android 2.2",
				"Bluetooth 2.1", "800 MHz Single core", "5.0 megapixels",
				"microSD card (2GB included)", "3");
		list.add(newmodel3);

		GadgetData newmodel4 = new GadgetData("Samsung Galaxy S III",
				"5.38mm x 2.78mm x 0.34mm", "133g", "Android 4.0",
				"Bluetooth 4.0", "1400 MHz Quad core", "8.0 megapixels",
				"None (16GB included)", "4");
		list.add(newmodel4);

		GadgetData newmodel5 = new GadgetData("Samsung Galaxy S4 Active",
				"139.7mm x 71.3mm x 9.1mm", "151 g", "Android 4.2.2",
				"Bluetooth 4.0", "1900 MHz Quad core", "8.0 megapixels",
				"microSDXC up to 64 GB", "5");
		list.add(newmodel5);
	}

	@SuppressWarnings("deprecation")
	private void showNotification(String text) {
		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		// CharSequence text = "Service binded";

		// Set the icon, scrolling text and timestamp

		Notification notification = new Notification(R.drawable.ic_launcher,
				text, System.currentTimeMillis());
		notification.setLatestEventInfo(this, text, text, null);
		mNM.notify(1, notification);

	}

	public GadgetData getSpecs(int id) {
		return list.get(id);
	}
}

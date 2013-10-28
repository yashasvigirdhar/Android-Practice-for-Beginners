package com.itsdark.mis.crawlhttppage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

@SuppressLint("NewApi")
public class SearchImagesandLinksService extends Service {

	NotificationManager mNM;
	int done = 0;

	private String urlPath = "http://www.thehindu.com/";
	ArrayList<String> hyperLinks = new ArrayList<String>();
	ArrayList<String> imageLinks = new ArrayList<String>();
	private String LOG_TAG = "Service";

	ArrayList<Messenger> mClients = new ArrayList<Messenger>();
	static final int MSG_REGISTER_CLIENT = 1;
	static final int MSG_UNREGISTER_CLIENT = 2;
	static final int MSG_SET_VALUE = 3;
	static final int MSG_DATA = 4;
	static final int MSG_SET_URL = 5;
	int mvalue = 0;

	final Messenger mMessenger = new Messenger(new IncomingHandler());

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		startcrawl();
		Log.i(LOG_TAG, "on create");
		return Service.START_STICKY;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Log.i(LOG_TAG, "on create");
	}

	public void startcrawl() {
		// TODO Auto-generated method stub
		Log.i(LOG_TAG, "on crawl");
		hyperLinks.clear();
		imageLinks.clear();
		Thread downloadThread = new Thread() {
			@SuppressWarnings("unused")
			public void run() {
				try {
					Log.i(LOG_TAG, "thread run " + urlPath);
					Document doc;
					doc = Jsoup.connect(urlPath).get();
					final String title = doc.title();
					final Elements hLinks = doc.select("a[href]");
					final Elements iLinks = doc.select("img");
					for (Element link : hLinks) {
						String l1 = String.format("%s", link.text());
						String l2 = String.format("%s", link.attr("abs:href"));
						// Log.i(LOG_TAG, "*html* "+link.toString());
						if (l1.length() >= 10) {

							// link_titles.add(l1);
							hyperLinks.add(l2);
							// Log.i(LOG_TAG,"html " + l2);
						}
						// hyperLinks.add(l1);
					}
					for (Element link : iLinks) {
						/*
						 * String l1=String.format("%s",image.text()); String
						 * l2=String.format("%s",image.attr("abs:src"));
						 * img_texts.add(l1); img_urls.add(l2);
						 */
						String l2 = String.format("%s", link.attr("abs:src"));
						// Log.i(LOG_TAG, "img "+l2);
						imageLinks.add(l2);
					}
					done = 1;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		downloadThread.start();

	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i(LOG_TAG, "on bind");
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

	/**
	 * Handler of incoming messages from clients.
	 */
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Log.i(LOG_TAG, "message received0");
			switch (msg.what) {
			case MSG_REGISTER_CLIENT:
				mClients.add(msg.replyTo);
				break;
			case MSG_UNREGISTER_CLIENT:
				mClients.remove(msg.replyTo);
				break;
			case MSG_SET_URL:
				urlPath = msg.getData().getString("url");
				break;
			case MSG_SET_VALUE:
				done=0;
				Log.i(LOG_TAG, "message received3");
				mvalue = msg.arg1;
				for (int i = mClients.size() - 1; i >= 0; i--) {
					startcrawl();
					while (done == 0) {
						Log.i(LOG_TAG, "while loop");
					}

					showNotification("links downloaded");
					try {
						Message ms = Message.obtain(null, MSG_DATA, 1, 0);
						Bundle b = new Bundle();
						b.putStringArrayList("links", hyperLinks);
						b.putStringArrayList("images", imageLinks);
						ms.setData(b);
						mClients.get(i).send(ms);
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

	/**
	 * Show a notification while this service is running.
	 */
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
}

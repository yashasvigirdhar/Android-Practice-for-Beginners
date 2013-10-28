package com.itsdark.mis.crawlhttppage;

import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
//import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HTMLImagesAndHyperLInksActivity extends Activity implements
		OnClickListener {

	Context mcontext = this;
	private String LOG_TAG = "Activity";

	ArrayList<String> LinksList;
	ArrayAdapter<String> Ladapter;
	ArrayList<String> ImagesList;
	ArrayAdapter<String> Iadapter;

	ListView links, images;
	Button gocrawl;
	EditText url;

	
	Messenger mService = null;
	boolean mIsBound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_htmlimages_and_hyper_links);
		Log.i(LOG_TAG, "on create");
		intialize();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(LOG_TAG, "on resume");
		bindService(new Intent(this, SearchImagesandLinksService.class),
				mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;

	}

	private void intialize() {
		// TODO Auto-generated method stub
		Log.i(LOG_TAG, "on intialize");
		LinksList = new ArrayList<String>();
		Ladapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				LinksList);
		links = (ListView) findViewById(R.id.lvHyperLinks);
		links.setAdapter(Ladapter);

		ImagesList = new ArrayList<String>();
		Iadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				ImagesList);
		images = (ListView) findViewById(R.id.lvImagesLinks);
		images.setAdapter(Iadapter);

		gocrawl = (Button) findViewById(R.id.bCrawl);
		gocrawl.setOnClickListener(this);

		url = (EditText) findViewById(R.id.etUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.htmlimages_and_hyper_links, menu);
		return true;
	}

	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SearchImagesandLinksService.MSG_DATA:
				Log.i(LOG_TAG, "message received");
				LinksList.clear();
				LinksList.addAll(msg.getData().getStringArrayList("links"));
				Ladapter.notifyDataSetChanged();
				ImagesList.clear();
				ImagesList.addAll(msg.getData().getStringArrayList("images"));
				Iadapter.notifyDataSetChanged();
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}

	/**
	 * Target we publish for clients to send messages to IncomingHandler.
	 */
	final Messenger mMessenger = new Messenger(new IncomingHandler());

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			Toast.makeText(mcontext, "Connected", Toast.LENGTH_SHORT).show();
			mService = new Messenger(binder);
			try {
				Message msg = Message.obtain(null,
						SearchImagesandLinksService.MSG_REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);

				msg = Message.obtain(null,
						SearchImagesandLinksService.MSG_SET_VALUE,
						this.hashCode(), 0);
				mService.send(msg);
				Log.i(LOG_TAG, "message sent");
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
			 Toast.makeText(mcontext, "disconnected",
		                Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		try {
			Message msg = Message
					.obtain(null, SearchImagesandLinksService.MSG_SET_URL,
							this.hashCode(), 0);
			Bundle b = new Bundle();
			b.putString("url", url.getText().toString());
			msg.setData(b);
			mService.send(msg);

			msg = Message.obtain(null,
					SearchImagesandLinksService.MSG_SET_VALUE, this.hashCode(),
					0);

			mService.send(msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(LOG_TAG, "message sent");
	}

	@Override
	protected void onPause() {
		super.onPause();
		doUnbindService();
		unbindService(mConnection);
		mIsBound = false;
	}

	void doUnbindService() {
		if (mIsBound) {
			// If we have received the service, and hence registered with
			// it, then now is the time to unregister.
			if (mService != null) {
				try {
					Message msg = Message.obtain(null,
							SearchImagesandLinksService.MSG_UNREGISTER_CLIENT);
					msg.replyTo = mMessenger;
					mService.send(msg);
				} catch (RemoteException e) {
					// There is nothing special we need to do if the service
					// has crashed.
				}
			}

		}
	}

}

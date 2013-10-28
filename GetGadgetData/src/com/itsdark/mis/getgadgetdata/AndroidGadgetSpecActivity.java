package com.itsdark.mis.getgadgetdata;

import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AndroidGadgetSpecActivity extends ListActivity implements
		OnItemClickListener {

	private Context mcontext = this;
	private String LOG_TAG = "Activity";
	Messenger mService = null;
	boolean mIsBound;
	static final String[] models = new String[] { "Xperia", "Nexus 4",
			"Samsung Galaxy Ace", "Samsung Galaxy S III",
			"Samsung Galaxy S4 Active" };
	ListView listView;
	GadgetData selectedmodel = null;

	final Messenger mMessenger = new Messenger(new IncomingHandler());

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_android_gadget_spec_activity);
		intialize();
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.i(LOG_TAG, "on resume");
		bindService(new Intent(this, AndroidGadgetDataService.class),
				mConnection, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	private void intialize() {
		// TODO Auto-generated method stub
		setListAdapter(new ArrayAdapter<String>(this, R.layout.listtextview,
				models));
		listView = getListView();
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_gadget_data, menu);
		return true;
	}

	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case AndroidGadgetDataService.MSG_GET_VALUE:
				Log.i(LOG_TAG, "message received");
				msg.getData().setClassLoader(GadgetData.class.getClassLoader());
				selectedmodel = (GadgetData)msg.getData().getParcelable("data");
				Intent i = new Intent(AndroidGadgetSpecActivity.this,
						DetailedView.class);
				i.putExtra("name", selectedmodel.name);
				i.putExtra("size", selectedmodel.size);
				i.putExtra("wt", selectedmodel.wt);
				i.putExtra("OS", selectedmodel.OS);
				i.putExtra("connectivity", selectedmodel.connectivity);
				i.putExtra("processor", selectedmodel.processor);
				i.putExtra("camera", selectedmodel.camera);
				i.putExtra("sd_storage", selectedmodel.sd_storage);
				i.putExtra("img", selectedmodel.img);
				startActivity(i);
				break;
			default:
				int a;
				super.handleMessage(msg);
			}
		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName className, IBinder binder) {
			Toast.makeText(mcontext, "Connected", Toast.LENGTH_SHORT).show();
			mService = new Messenger(binder);
			try {
				Message msg = Message.obtain(null,
						AndroidGadgetDataService.MSG_REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);

				/*
				 * msg = Message.obtain(null,
				 * AndroidGadgetDataService.MSG_GET_VALUE, this.hashCode(), 0);
				 * Bundle b = new Bundle(); b.putInt("pos", 1); msg.setData(b);
				 * mService.send(msg);
				 */
				Log.i(LOG_TAG, "message sent");
			} catch (RemoteException e) {
				e.printStackTrace();
			}

		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
			Toast.makeText(mcontext, "disconnected", Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		// TODO Auto-generated method stub
		Message msg = Message.obtain(null,
				AndroidGadgetDataService.MSG_GET_VALUE, this.hashCode(), 0);
		Bundle b = new Bundle();
		b.putInt("pos", pos);
		msg.setData(b);
		try {
			mService.send(msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

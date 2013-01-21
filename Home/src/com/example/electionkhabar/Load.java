package com.example.electionkhabar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Load extends Activity {

	String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load);
		Bundle gotBasket=getIntent().getExtras();
		url=gotBasket.getString("keynews");
		final TextView loading = (TextView) findViewById(R.id.tvloading);
		/*
		 * Resources res = getResources(); Drawable background =
		 * res.getDrawable(R.drawable.newsload);
		 * 
		 * LinearLayout layout = ((LinearLayout) findViewById(R.id.lnews)); //
		 * Now that we have the layout and the background, we ajust the opacity
		 * // of the background, and sets it as the background for the layout
		 * //background.setAlpha(100); layout.setBackgroundDrawable(background);
		 */

		final Handler cambiarHandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				TextView texto = (TextView) findViewById(R.id.tvloading);
				texto.setText("Loading . .");

			}
		};

		new Thread(new Runnable() {
			public void run() {

				try {

					Thread.sleep(1000);
					cambiarHandler.sendEmptyMessage(0);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();

		final Handler cambiarHandler1 = new Handler() {
			public void handleMessage(android.os.Message msg) {
				TextView texto = (TextView) findViewById(R.id.tvloading);
				texto.setText("Loading . . .");

			}
		};

		new Thread(new Runnable() {
			public void run() {

				try {

					Thread.sleep(2000);
					cambiarHandler1.sendEmptyMessage(0);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(3000);

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
			       
					Bundle basket=new Bundle();
					basket.putString("keynews",url);
					Intent openpoint = new Intent(Load.this, News.class);
					openpoint.putExtras(basket);
					startActivity(openpoint);

				}

			}

		};
		timer.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}

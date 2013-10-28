package com.itsdark.mis.getgadgetdata;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailedview);

		TextView name = (TextView) findViewById(R.id.tvname);
		TextView size = (TextView) findViewById(R.id.tvsize);
		TextView wt = (TextView) findViewById(R.id.tvwt);
		TextView processor = (TextView) findViewById(R.id.tvprocessor);
		TextView OS = (TextView) findViewById(R.id.tvOS);
		TextView connectivity = (TextView) findViewById(R.id.tvconnectivity);
		TextView camera = (TextView) findViewById(R.id.tvcamera);
		TextView storage = (TextView) findViewById(R.id.tvstorage);
		ImageView mobileimage = (ImageView) findViewById(R.id.ivmobile);

		Intent i = this.getIntent();
		String mobilename = i.getExtras().getString("name");
		name.setText(mobilename);
		String mobilesize = i.getExtras().getString("size");
		size.setText(mobilesize);
		String mobilewt = i.getExtras().getString("wt");
		wt.setText(mobilewt);
		String mobileOS = i.getExtras().getString("OS");
		OS.setText(mobileOS);
		String mobileprocessor = i.getExtras().getString("processor");
		processor.setText(mobileprocessor);
		String mobileconnectivity = i.getExtras().getString("connectivity");
		connectivity.setText(mobileconnectivity);
		String mobilecamera = i.getExtras().getString("camera");
		camera.setText(mobilecamera);
		String mobilestorage = i.getExtras().getString("sd_storage");
		storage.setText(mobilestorage);

		String imagename = i.getExtras().getString("img");
		Resources res = getResources();
		int resourceId = res.getIdentifier(imagename, "drawable",
				getPackageName());
		mobileimage.setImageResource(resourceId);

	}

}

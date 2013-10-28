package com.example.wonders;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class WebViewActivity extends Activity {
	TextView wondername;
	TextView wonderdesc;
	ImageView wonderimage;
	
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.potrait_layout);
		wondername=(TextView)findViewById(R.id.tv_wondername);
		wonderdesc=(TextView)findViewById(R.id.tv_wonderdesc);
		wonderimage=(ImageView)findViewById(R.id.iv_wonder);

		Intent i = this.getIntent();
		String name = i.getExtras().getString("name");
		LinkData item=Model.GetbyId(name);
		String desc=item.getDesc();
		String imagename=item.getImageName();

		Resources res = getResources();
		int resourceId = res.getIdentifier(
		   imagename, "drawable", getPackageName() );
        
        Toast.makeText(getApplicationContext(), (String)imagename, Toast.LENGTH_LONG).show();
        
		wondername.setText(name);
		wonderdesc.setText(desc);
		wonderimage.setImageResource(resourceId);


	}


}
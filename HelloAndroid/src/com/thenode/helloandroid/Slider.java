package com.thenode.helloandroid;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

public class Slider extends Activity {

	SlidingDrawer sd;
/*	Button h1 = (Button) findViewById(R.id.handle1);
	Button h2 = (Button) findViewById(R.id.handle2);
	Button h3 = (Button) findViewById(R.id.handle3);
	Button h4 = (Button) findViewById(R.id.handle4);
	CheckBox checkbox = (CheckBox) findViewById(R.id.cbSlide);
*/	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sliding);
		/*h1.setOnClickListener(this);
		h2.setOnClickListener(this);
		h3.setOnClickListener(this);
		h4.setOnClickListener(this);
		checkbox.setOnCheckedChangeListener(this);
		sd = (SlidingDrawer) findViewById(R.id.slidingD);
		sd.setOnDrawerOpenListener(this);*/
	}
	/*public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.handle1:
			sd.open();
			break;
		case R.id.handle2:
			
			break;
		case R.id.handle3:
			sd.toggle();
			break;
		case R.id.handle4:
			sd.close();
			break;
		}
	}
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if(buttonView.isChecked()){
			sd.lock();
		}
		else{
			sd.unlock();
		}
	}
	public void onDrawerOpened() {
		// TODO Auto-generated method stub
		MediaPlayer mp = MediaPlayer.create(this, R.raw.sound);
		mp.start();
	}*/
	
}

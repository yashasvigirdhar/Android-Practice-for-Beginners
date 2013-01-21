package com.thenode.helloandroid;

import java.io.IOException;
import java.io.InputStream;



import android.app.Activity;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ChangeWallpaper extends Activity implements View.OnClickListener{
	ImageButton ib;
	ImageView iv;
	Button b;
	Intent i;
	final static int cameraData=0;
	Bitmap bmp;
	Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo);
		initialize();
		InputStream is = getResources().openRawResource(R.drawable.back);
		bmp = BitmapFactory.decodeStream(is);
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		ib = (ImageButton) findViewById(R.id.ibTakePic);
		iv = (ImageView) findViewById(R.id.ivReturnedPic);
		b = (Button) findViewById(R.id.bSetWall);
		b.setOnClickListener(this);
		ib.setOnClickListener(this);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			Bundle extras=data.getExtras();
			bmp = (Bitmap) extras.get("data");
			iv.setImageBitmap(bmp);
		}
	}

	@SuppressWarnings("deprecation")
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		
		case R.id.bSetWall:
			try {
				getApplicationContext().setWallpaper(bmp);
				String msg="wallpaper";
				NotificationManager nm =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
				@SuppressWarnings("deprecation")
				Notification notify=new Notification(android.R.drawable.alert_dark_frame, msg, System.currentTimeMillis());
				Intent intent1= new Intent(context, ChangeWallpaper.class);
				Bundle basket = new Bundle();
				basket.putString("msg", msg);
				intent1.putExtras(basket);
				PendingIntent pending= PendingIntent.getActivity(context, 0, intent1, 0);
				notify.setLatestEventInfo(context, msg, "your wallpaper has been changed", pending);
				
				Notification noti = new Notification.Builder(context)
		        .setContentTitle("your wallpaper has been changed")
		        .setContentText(msg)
		        .setSmallIcon(android.R.drawable.alert_dark_frame)
		        .build();
				
				nm.notify(0, notify);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case R.id.ibTakePic:
			i=new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i,cameraData);
				break;
		}
	}
}

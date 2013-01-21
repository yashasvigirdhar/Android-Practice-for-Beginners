package com.example.electionkhabar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class Neta extends Activity{

	static final String[] Politicians = new String[] {"mamta","sonia","modi", "manmohan","rahul"};
	private PicAdapter imgAdapt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.neta);
		Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/BubblegumSans-Regular.otf");
		TextView title=(TextView)findViewById(R.id.neta);
		title.setTypeface(tf);
		
		Gallery picGallery = (Gallery) findViewById(R.id.gallery);
		imgAdapt = new PicAdapter(this);
		picGallery.setAdapter(imgAdapt);
		
		picGallery.setOnItemClickListener(new OnItemClickListener() {
		    //handle clicks
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        //set the larger image view to display the chosen bitmap calling method of adapter class
		        //picView.setImageBitmap(imgAdapt.getPic(position));
				String name = Politicians[position];
				String index = Integer.toString(position);
				Intent i = new Intent(getApplicationContext(), PoliticianNews.class);
				Bundle basket=new Bundle();
				basket.putString("name", name);
				basket.putString("index", index);
				i.putExtras(basket);
				startActivity(i);
		    }

		});
		
	}
	
	public class PicAdapter extends BaseAdapter {

		
		int defaultItemBackground;
		private Context galleryContext;
		private Bitmap[] imageBitmaps;
		Bitmap[] placeholder;
		
		public PicAdapter(Context c) {
		    galleryContext = c;
		    imageBitmaps = new Bitmap[5];
		    placeholder = new Bitmap[5];
		    placeholder[0] = BitmapFactory.decodeResource(getResources(), R.drawable.mamta1);
		    placeholder[1] = BitmapFactory.decodeResource(getResources(), R.drawable.sonia1);
		    placeholder[2] = BitmapFactory.decodeResource(getResources(), R.drawable.modi1);
		    placeholder[3] = BitmapFactory.decodeResource(getResources(), R.drawable.manmohan1);
		    placeholder[4] = BitmapFactory.decodeResource(getResources(), R.drawable.rahul1);
		    for(int i=0; i<imageBitmaps.length; i++)
		        imageBitmaps[i]=placeholder[i];
		    TypedArray styleAttrs = galleryContext.obtainStyledAttributes(R.styleable.PicGallery);
		    defaultItemBackground = styleAttrs.getResourceId(
		        R.styleable.PicGallery_android_galleryItemBackground, 0);
		    styleAttrs.recycle();
		}
		
		public int getCount() {
			return imageBitmaps.length;

		}

		public Object getItem(int arg0) {
				return arg0;
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
		    ImageView imageView = new ImageView(galleryContext);
		    imageView.setImageBitmap(imageBitmaps[position]);
		    imageView.setLayoutParams(new Gallery.LayoutParams(370, 470));
		    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		    imageView.setBackgroundResource(defaultItemBackground);
		    //imageView.setBackgroundResource(R.drawable.black);
		    return imageView;
		}
		
		public Bitmap getPic(int posn)
		{
		    return imageBitmaps[posn];
		}

	}
	
	
	
	

}

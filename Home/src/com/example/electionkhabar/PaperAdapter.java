package com.example.electionkhabar;

import java.io.File;
import java.util.Hashtable;
import java.util.List;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
 
public class PaperAdapter extends ArrayAdapter<String> {
	private final Context context;
	private String[] values;
	private int[] ht;
	

	
	public PaperAdapter(Context context, String[] values,int[] ht) {
		super(context, R.layout.parties, values);
		this.context = context;
		this.values = values;
		this.ht=ht;
	}
 

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.paper, parent, false);
		LinearLayout l =(LinearLayout)rowView.findViewById(R.id.bg);
		rowView.getLayoutParams().height=ht[position];
		String icon="drawable/news"+values[position];
		int resID = context.getResources().getIdentifier(icon,null,context.getPackageName());
	    Drawable image = context.getResources().getDrawable(resID);
	    l.setBackgroundDrawable(image);

 
		return rowView;
	}
}
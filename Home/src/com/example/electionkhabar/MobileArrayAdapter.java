package com.example.electionkhabar;

import java.io.File;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class MobileArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final String[] logos;
	
	public MobileArrayAdapter(Context context, String[] values,String [] logos) {
		super(context, R.layout.parties, values);
		this.context = context;
		this.values = values;
		this.logos=logos;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.parties, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.tvlabel);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.ivlogo);
		if(position==0)
		{
			rowView.setMinimumHeight(430);
			String icon="drawable/partyhomepage";
			int resID = context.getResources().getIdentifier(icon,null,context.getPackageName());
		    Drawable image = context.getResources().getDrawable(resID);
		    imageView.setImageDrawable(image);
		    imageView.getLayoutParams().height=400;
		    imageView.getLayoutParams().width=300;
		    MarginLayoutParams mlp = (MarginLayoutParams)imageView.getLayoutParams();
		    mlp.setMargins(85, 10,0,10);//all in pixels
		    imageView.setLayoutParams(mlp);
		    
		}
		else{
		textView.setText(values[position]);
		String icon="drawable/"+logos[position]+"logo";
		int resID = context.getResources().getIdentifier(icon,null,context.getPackageName());
	    Drawable image = context.getResources().getDrawable(resID);
	    imageView.setImageDrawable(image);
	    
		}
	    rowView.setBackgroundResource(R.drawable.customshape);

 
		return rowView;
	}
}
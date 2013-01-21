package com.example.electionkhabar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MobileArrayAdapterPol extends ArrayAdapter<String> {

	private final Context context;
	private final String[] values;

	public MobileArrayAdapterPol(Context context, String[] values) {
		super(context, R.layout.politicians, values);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.politicians, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.tvneta);
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.ivlogo);
		/*if (position == 0) {
			rowView.setMinimumHeight(430);
			String icon = "drawable/partyhomepage";
			int resID = context.getResources().getIdentifier(icon, null,
					context.getPackageName());
			Drawable image = context.getResources().getDrawable(resID);
			imageView.setImageDrawable(image);
			imageView.getLayoutParams().height = 400;
			imageView.getLayoutParams().width = 300;
			MarginLayoutParams mlp = (MarginLayoutParams) imageView
					.getLayoutParams();
			mlp.setMargins(85, 10, 0, 10);// all in pixels
			imageView.setLayoutParams(mlp);

		} else {*/
			
			textView.setText(values[position]);
			rowView.setBackgroundResource(R.drawable.style);
		//}

		return rowView;

	}

}

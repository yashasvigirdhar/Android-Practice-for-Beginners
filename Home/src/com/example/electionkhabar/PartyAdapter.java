package com.example.electionkhabar;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PartyAdapter extends ArrayAdapter<String> {
	private final Context context;
	private List<String> values;

	public PartyAdapter(Context context, List<String> values) {
		super(context, R.layout.party, values);
		this.context = context;
		this.values = values;

	}

	public void add(List<String> values) {
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.party, parent, false);
		final TextView textView = (TextView) rowView.findViewById(R.id.tvPnews);
		textView.setText(values.get(position));
		// rowView.setBackgroundResource(R.drawable.customshape1);

		return rowView;
	}
}

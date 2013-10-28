package com.example.wonders;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WonderDescFragment extends Fragment {

	private String currentname;

	public void init(String name) {
		currentname = name;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {		
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Log.d("SwA", "WVF onCreateView");
		View v = inflater.inflate(R.layout.wonderdesc_fragment_layout, container, false);
		if (currentname != null) {
			Log.d("SwA", "Current URL  1["+currentname+"]");
			
			
			TextView wonderdesc=(TextView)v.findViewById(R.id.tv_frag2);
			LinkData item=Model.GetbyId(currentname);
			String desc=item.getDesc();
			wonderdesc.setText(desc);

		}
		return v;
	}

	
	public void updateName(String name) {
		Log.d("SwA", "Update URL ["+name+"] - View ["+getView()+"]");
		currentname = name;
		
		TextView wonderdesc=(TextView)getView().findViewById(R.id.tv_frag2);
		LinkData item=Model.GetbyId(currentname);
		String desc=item.getDesc();
		wonderdesc.setText(desc);
		
	}




}

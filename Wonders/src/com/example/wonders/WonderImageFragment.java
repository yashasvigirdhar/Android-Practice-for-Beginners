package com.example.wonders;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * Copyright (C) 2012-2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class WonderImageFragment extends Fragment {

	private String currentname=null;

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
		View v = inflater.inflate(R.layout.wonderimage_fragment_layout, container, false);
		if (currentname != null) {
			Log.d("SwA", "Current URL  1["+currentname+"]");
			
			
			TextView wondername=(TextView)v.findViewById(R.id.tv_frag1);
			ImageView wonderimage=(ImageView)v.findViewById(R.id.iv_frag1);
			wondername.setText(currentname);
			LinkData item=Model.GetbyId(currentname);
			String imagename=item.getImageName();
			Resources res = getResources();
			int resourceId = res.getIdentifier(
			   imagename, "drawable",getActivity().getPackageName());
			wonderimage.setImageResource(resourceId);


		}
		return v;
	}

	
	public void updateName(String name) {
		Log.d("SwA", "Update URL ["+name+"] - View ["+getView()+"]");
		currentname = name;

		TextView wondername=(TextView)getView().findViewById(R.id.tv_frag1);
		ImageView wonderimage=(ImageView)getView().findViewById(R.id.iv_frag1);
		wondername.setText(currentname);
		LinkData item=Model.GetbyId(currentname);
		String imagename=item.getImageName();
		Resources res = getResources();
		int resourceId = res.getIdentifier(
		   imagename, "drawable",getActivity().getPackageName());
		wonderimage.setImageResource(resourceId);
	}




}

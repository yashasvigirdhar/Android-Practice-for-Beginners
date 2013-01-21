package com.example.electionkhabar;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ourViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView v,String url){
		v.loadUrl(url);
		return true;
	}

}
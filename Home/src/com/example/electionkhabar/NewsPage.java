package com.example.electionkhabar;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class NewsPage extends Activity{
	WebView OurBrow;
	String url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle gotBasket=getIntent().getExtras();
		setContentView(R.layout.news_page);
		//TextView displayUrl=(TextView)findViewById(R.id.tvGotUrl);
		url=gotBasket.getString("key");
		//displayUrl.setText(url);
		
		OurBrow = (WebView)findViewById(R.id.wvBrow);
		OurBrow.getSettings().setJavaScriptEnabled(true);
		OurBrow.getSettings().setLoadWithOverviewMode(true);
		OurBrow.getSettings().setUseWideViewPort(true);
		
		
		OurBrow.setWebViewClient(new ourViewClient());
		try {
			OurBrow.loadUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}

package com.thenode.helloandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("SetJavaScriptEnabled")
public class Wikipedia extends Activity implements OnClickListener {

	WebView ourBra;
	Button go, back, refresh, forward, clearHistory;
	EditText url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplebrowser);
		ourBra = (WebView) findViewById(R.id.wvBrowser);
		ourBra.getSettings().setJavaScriptEnabled(true);
		ourBra.getSettings().setLoadWithOverviewMode(true);
		ourBra.getSettings().setUseWideViewPort(true);
		ourBra.setWebViewClient(new ourViewClient());
		try {
			ourBra.loadUrl("https://www.google.com/search?q=wikipedia "+url+"&btnI");
		} catch (Exception e) {
			e.printStackTrace();
		}
		go = (Button) findViewById(R.id.bGo);
		back = (Button) findViewById(R.id.bBack);
		refresh = (Button) findViewById(R.id.bRefresh);
		forward = (Button) findViewById(R.id.bForward);
		clearHistory = (Button) findViewById(R.id.bHistory);
		url = (EditText) findViewById(R.id.etUrl);
		go.setOnClickListener(this);
		back.setOnClickListener(this);
		refresh.setOnClickListener(this);
		forward.setOnClickListener(this);
		clearHistory.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bGo:
			String site = url.getText().toString();
			ourBra.loadUrl("https://www.google.com/search?q=wikipedia "+site+"&btnI");
			//hiding the keyboard
			InputMethodManager imm= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
			break;
		case R.id.bBack:
			if (ourBra.canGoBack())
				ourBra.goBack();
			break;
		case R.id.bRefresh:
			ourBra.reload();
			break;
		case R.id.bForward:
			if (ourBra.canGoForward())
				ourBra.goForward();
			break;
		case R.id.bHistory:
			ourBra.clearHistory();
			break;
		}
	}
}

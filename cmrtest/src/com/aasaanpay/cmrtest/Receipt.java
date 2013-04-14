package com.aasaanpay.cmrtest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class Receipt extends Activity {

	String htmlurl = "file:///android_asset/Receipt.html";
	String newhtmlurl = "file://sdcard/Receipt1.html";
	Document doc;
	WebView webView; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receipt);
		webView = (WebView) findViewById(R.id.wvreceipt);
		webView.loadUrl(htmlurl);
		Parsehtml downloadFile = new Parsehtml();
		downloadFile.execute(htmlurl);

	}

	private class Parsehtml extends AsyncTask<String, Integer, String> {

		String title="yash";

		@Override
		protected String doInBackground(String... sUrl) {
			// TODO Auto-generated method stub
						
			InputStream is=null;

			try {
			    is=getAssets().open("Receipt.html");
			    doc = Jsoup.parse(is, "UTF-8", htmlurl);
			    title = doc.title();
			} catch (IOException e) {
			    e.printStackTrace();
			} finally {
			    if(is!=null)
					try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			return title;
		}

		protected void onPostExecute(String result) {
			Toast.makeText(Receipt.this, result, Toast.LENGTH_LONG).show();
			Elements links = doc.select("div");
			links.attr("style", "color:pink;");
			String html = doc.html();
			Log.d("html", html);
			Savehtml save = new Savehtml();
			save.execute(html);
			
		}

	}
	
	class Savehtml extends AsyncTask<String, String, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	      File html=new File(Environment.getExternalStorageDirectory(), "Receipt1.html");

	      if (html.exists()) {
	            html.delete();
	      }

	      try {
	        FileOutputStream fos=new FileOutputStream(html.getPath());

	        PrintWriter pw = new PrintWriter(fos);
	        pw.println(urls[0]);
	        
	        pw.flush();
	        pw.close();
	        fos.close();
	      
	      }
	      catch (java.io.IOException e) {
	        Log.e("savehtml", "Exception in photoCallback", e);
	      }

	      return(null);
	    }
	    protected void onPostExecute(String result) {
	    	final File file = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath(), "Receipt1.html");
	    	String uu = file.getAbsolutePath();
	    	webView.loadUrl(uu);
	    }
	}
}

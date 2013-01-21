package com.example.electionkhabar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class News extends Activity{
	PartyAdapter p_adapter;
	List<String> news_links = new ArrayList<String>();
	List<String> hyperlinks = new ArrayList<String>();
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		Bundle gotBasket=getIntent().getExtras();
		url=gotBasket.getString("keynews");
	    final ListView lv=(ListView) findViewById(R.id.lvnewspapernews);
	    p_adapter=new PartyAdapter(News.this, news_links);
	    lv.setAdapter(p_adapter);
	    
	    
	    
	    lv.setOnItemClickListener(new OnItemClickListener() {    

	        
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {      
	        	String url=hyperlinks.get(position);
				Bundle basket=new Bundle();
				basket.putString("key",url);
				Intent a=new Intent(News.this,NewsPage.class);
				a.putExtras(basket);
				startActivity(a);
	        	
	        }


	     });
	    

		Thread downloadThread = new Thread() {
			@SuppressWarnings("unused")
			public void run() {
				Document doc;
				try {

					doc = Jsoup.connect(url).get();
					final String title = doc.title();
					final Elements links = doc.select("a[href]");

					
					runOnUiThread(new Runnable() {

						public void run() {
							
							for(Element link : links){
								String l1=String.format("%s",link.text());
								String l2=String.format("%s",link.attr("abs:href"));
								
								if(l1.length()>=30)
								{

											news_links.add(l1);
											hyperlinks.add(l2);

								}
								p_adapter.add(news_links);
								lv.setAdapter(p_adapter);	
								}
							
							
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};

		downloadThread.start();
	}
	
	    
	    
	    
	    
	    
	    
}
	
	
	
	



package com.example.electionkhabar;	

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PoliticianNews extends Activity {

	String names[]={"Mamta Bannerjee","Sonia Gandhi","Narendra Modi","Manmohan Singh","Rahul Gandhi"
	};
	String filter[][] = {
			{"mamata","mamta","trinamool","tmc","bengal","bannerjee"},
			{ "congress", "upa", "sonia", "gandhi", "chairperson","rajiv"},
			{ "bjp", "modi", "gujarat","narendra","godhra","keshubai"},
			{ "manmohan","pm","singh","pmo","government","sardar" },
			{ "rahul","gandhi","congress","pm","rahul","rahul" } };

	int index;
	List<String> news_links = new ArrayList<String>();
	List<String> hyperlinks = new ArrayList<String>();

	PoliticianAdapter p_adapter;

	String politicianname;
	ImageView politicianimg;
	TextView politiciantitle;

	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// overridePendingTransition(R.anim.slideright,R.anim.slideright2);
		super.onCreate(savedInstanceState);
		
		overridePendingTransition(R.anim.slideright, R.anim.slideright2);
		
		setContentView(R.layout.activity_news);
		Bundle gotBasket = getIntent().getExtras();
		politicianname = gotBasket.getString("name");
		index = Integer.parseInt(gotBasket.getString("index"));

		initialize();

		String icon = "drawable/" + politicianname;
		int resID = getResources().getIdentifier(icon, null, getPackageName());
		Drawable image = getResources().getDrawable(resID);
		politicianimg.setImageDrawable(image);

		lv = (ListView) findViewById(R.id.listNews);
		p_adapter = new PoliticianAdapter(PoliticianNews.this, news_links);

		Thread downloadThread = new Thread() {
			public void run() {
				Document doc;
				try {

					doc = Jsoup.connect("http://www.rediff.com/news/headlines")
							.get();
					final String title = doc.title();
					final Elements links = doc.select("a[href]");

					runOnUiThread(new Runnable() {

						public void run() {

							for (Element link : links) {
								String l1 = String.format("%s", link.text());
								String l2 = String.format("%s",
										link.attr("abs:href"));

								if (l1.length() >= 30) {
									int i;
									for (i = 0; i < 6; i++) {
										String search = l1.toLowerCase();
										if (search
												.contains(filter[index][i])) {
											news_links.add(l1);
											hyperlinks.add(l2);
										}
									}
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

		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = hyperlinks.get(position);
				Bundle basket = new Bundle();
				basket.putString("key", url);
				Intent a = new Intent(PoliticianNews.this, NewsPage.class);
				a.putExtras(basket);
				startActivity(a);

			}

		});
	}

	private void initialize() {
		// TODO Auto-generated method stub
		politiciantitle = (TextView) findViewById(R.id.tvParty);
		politiciantitle.setText(names[index]);

		politicianimg = (ImageView) findViewById(R.id.imgParty);
	}

}

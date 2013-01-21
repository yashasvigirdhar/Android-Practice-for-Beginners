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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MyPaper extends Activity {

	String filter[];
	String filter1[][] = {
			{ "congress", "upa", "sonia", "gandhi", "rahul", "pm ", "manmohan",
					"chidambaram", "shinde", "pranab" },
			{ "bjp", "modi", "sushma", "jaitley", "gujarat", "advani",
					"vajpayee", "joshi", "nda", "moily" },
			{ "samajwadi", "mulayam", "akhilesh", "yadav", "up ", "kalyan",
					"sp ", "uttar pradesh", "mohan", "sp " },
			{ "bsp", "maya", "mayawati", "dalit", "up ", "uttar pradesh",
					"bahujan", "kanshi", "ambedkar", "statue" } };

	int index;
	List<String> news_links = new ArrayList<String>();
	List<String> hyperlinks = new ArrayList<String>();

	MyPaperAdapter p_adapter;

	ListView lv;
	Button editsubs;

	TextView myname;
	ImageView myimg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.slideright, R.anim.slideright2);

		setContentView(R.layout.mypaper);

		initialize();
		populatefilter();

		lv = (ListView) findViewById(R.id.listNews);
		p_adapter = new MyPaperAdapter(MyPaper.this, news_links);

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

									int i, j;
									boolean match = false;

									for (i = 0; i < 4; i++) {
										//Toast.makeText(MyPaper.this,l1 ,
											//	Toast.LENGTH_LONG).show();
										String search = l1.toLowerCase();

										for (j = 0; j < filter.length; j++)
											if (filter1[i][0]
													.equalsIgnoreCase(filter[j])) {
												//Toast.makeText(MyPaper.this,
													//	"matched",
														//Toast.LENGTH_LONG)
														//.show();
												match = true;
												break;
											}
										if (match) {
											if (l1.length() >= 30) {
												int k;
												for (k = 0; k < 10; k++) {

													if (search
															.contains(filter1[i][k])) {
														news_links.add(l1);
														hyperlinks.add(l2);
													}
												}
											}
										}
									}
									p_adapter.add(news_links);
									lv.setAdapter(p_adapter);
								}
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
				Intent a = new Intent(MyPaper.this, NewsPage.class);
				a.putExtras(basket);
				startActivity(a);
			}

		});
	}

	private void populatefilter() {
		// TODO Auto-generated method stub
		StoredPreference info = new StoredPreference(this);
		info.open();
		String data = info.getfilter();
		filter = data.split(System.getProperty("line.separator"));
		info.close();
		//Toast.makeText(MyPaper.this, filter[0] + " " + filter[1],
				//Toast.LENGTH_LONG).show();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		myname = (TextView) findViewById(R.id.tvParty);
		myimg = (ImageView) findViewById(R.id.imgParty);
		myname.setText("My Paper ! aaah");
		editsubs = (Button) findViewById(R.id.bEditSubs);
		editsubs.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MyPaper.this, MyPaperInstruct.class);
				startActivity(i);
				// getParent().finish();
			}
		});

		int resID = getResources().getIdentifier("drawable/launcher", null,
				getPackageName());
		Drawable image = getResources().getDrawable(resID);
		myimg.setImageDrawable(image);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
	}
}

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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PartyNews extends Activity{

	TextView res,partytitle;
	LinearLayout layout;
	ImageView partyimg;
	List<String> news_links = new ArrayList<String>();
	List<String> hyperlinks = new ArrayList<String>();
	String partyname;
	String title;
	PartyAdapter p_adapter;
	int index;
    
	String filter[][]={{"congress","upa","sonia","gandhi","rahul","pm ","manmohan","chidambaram","shinde","pranab"},
    {"bjp","modi","sushma","jaitley","gujarat","advani","vajpayee","joshi","nda","moily"},
    {"samajwadi","mulayam","akhilesh","yadav","up ","kalyan","sp ","uttar pradesh","mohan","sp "},
    {"bsp","maya","mayawati","dalit","up ","uttar pradesh","bahujan","kanshi","ambedkar","statue"}};
	@SuppressWarnings("deprecation")
	@Override
	
	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slideleft, R.anim.slideleft2);
		setContentView(R.layout.activity_news);	
		Bundle gotBasket = getIntent().getExtras();
		partyname = gotBasket.getString("party");
		title = gotBasket.getString("title");
		index = Integer.parseInt(gotBasket.getString("index"));
		
		
		
		//Toast.makeText(this, partyname, Toast.LENGTH_SHORT).show();
	    res = (TextView) findViewById(R.id.tvNews);
	    layout=(LinearLayout)findViewById(R.id.layoutparty);
	    partytitle=(TextView)findViewById(R.id.tvParty);
	    partyimg=(ImageView)findViewById(R.id.imgParty);
		
	    partytitle.setText(title);
	    
	    String icon="drawable/"+partyname+"image";
		int resID =getResources().getIdentifier(icon,null,getPackageName());
	    Drawable image = getResources().getDrawable(resID);
	    partyimg.setImageDrawable(image);
	     
	    String bgicon="drawable/"+partyname+"bg";
		int ID =getResources().getIdentifier(bgicon,null,getPackageName());
	    Drawable bgimage = getResources().getDrawable(ID);
	    layout.setBackgroundDrawable(bgimage);
	    
	    
	    
	    //res.setText(partyname);
	    final ListView lv=(ListView) findViewById(R.id.listNews);
	    p_adapter=new PartyAdapter(PartyNews.this, news_links);
	    
	  //  LayoutInflater inflater = getLayoutInflater();
	   // ViewGroup header = (ViewGroup)inflater.inflate(R.layout.party, lv, false);
	   // lv.addHeaderView(header);
		lv.setAdapter(p_adapter);
		//lv.setDivider(this.getResources().getDrawable(R.drawable.black));
		//lv.setDividerHeight(1);
		//lv.setBackgroundResource(R.drawable.customshape1);
		
	    lv.setOnItemClickListener(new OnItemClickListener() {    

	        
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {      
	        	String url=hyperlinks.get(position);
				Bundle basket=new Bundle();
				basket.putString("key",url);
				Intent a=new Intent(PartyNews.this,NewsPage.class);
				a.putExtras(basket);
				startActivity(a);
	        	
	        }


	     });

		
		

		
		Thread downloadThread = new Thread() {
			@SuppressWarnings("unused")
			public void run() {
				Document doc;
				try {

					doc = Jsoup.connect("http://www.rediff.com/news/headlines").get();
					final String title = doc.title();
					final Elements links = doc.select("a[href]");

					
					runOnUiThread(new Runnable() {

						public void run() {
							
							for(Element link : links){
								String l1=String.format("%s",link.text());
								String l2=String.format("%s",link.attr("abs:href"));
								
								if(l1.length()>=30)
								{
									int i;
									for(i=0;i<10;i++)
									{
										String search = l1.toLowerCase();
										if(search.contains(filter[index-1][i]))
											{
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
	}
	
	
}
package com.example.wonders;

import java.util.ArrayList;



import android.database.Cursor;
import android.widget.Toast;


import com.example.wonders.SqlHandler;
import com.example.wonders.LinkData;

public class Model {

	public static ArrayList<LinkData> linkDatalist;
	
    public static void LoadModel(SqlHandler handler) {
    	linkDatalist = new ArrayList<LinkData>();
       	loadData(handler);
  
  	  String query1 = "SELECT * FROM PHONE_CONTACTS ";
  	  Cursor c1 = handler.selectQuery(query1);
  	  if (c1 != null && c1.getCount() != 0) {
  	   if (c1.moveToFirst()) {
  	    do {
  	    
  	     String wondername = c1.getString(c1
  	       .getColumnIndex("wondername"));
  	     String wonderimage = c1.getString(c1
  	       .getColumnIndex("wonderimage"));
  	     String wonderdesc = c1.getString(c1
  	  	       .getColumnIndex("wonderdesc"));
  	   LinkData newwonderdata = new LinkData(wondername,wonderimage,wonderdesc);
  	     linkDatalist.add(newwonderdata);
  	 
  	    } while (c1.moveToNext());
  	   }
  	  }
  	  c1.close();

    }

    private static void loadData(SqlHandler handler) {
		// TODO Auto-generated method stub
    	   String query = "INSERT INTO PHONE_CONTACTS(wondername,wonderimage,wonderdesc) values ('Taj Mahal','img_a','The Taj Mahal is a white marble mausoleum located in Agra, Uttar Pradesh, India. It was built by Mughal emperor Shah Jahan in memory of his third wife, Mumtaz Mahal. The Taj Mahal is widely recognized as the jewel of Muslim art in India and one of the universally admired masterpieces of the world heritage.')";
    	   handler.executeQuery(query);
    	   query = "INSERT INTO PHONE_CONTACTS(wondername,wonderimage,wonderdesc) values ('Statue of Liberty','img_b','The Statue of Liberty National Monument officially celebrated her 100th birthday on October 28, 1986. The people of France gave the Statue to the people of the United States over one hundred years ago in recognition of the friendship established during the American Revolution. Over years, the Statue of Liberty symbolism has grown to include freedom and democracy.')";
    	   handler.executeQuery(query);
    	   query = "INSERT INTO PHONE_CONTACTS(wondername,wonderimage,wonderdesc) values ('Great Wall of China','img_c','The Great Wall of China is a series of fortifications made of stone, brick, tamped earth, wood, and other materials, generally built along an east-to-west line across the historical northern borders of China in part to protect the Chinese Empire or its prototypical states against intrusions by various nomadic groups or military incursions by various warlike peoples or forces')";
    	   handler.executeQuery(query);
    	   query = "INSERT INTO PHONE_CONTACTS(wondername,wonderimage,wonderdesc) values ('Machu Picchu','img_d','Machu Picchu was built in the classical Inca style, with polished dry-stone walls. Its three primary structures are the Intihuatana (Hitching post of the Sun), the Temple of the Sun, and the Room of the Three Windows. These are located in what is known by archaeologists as the Sacred District of Machu Picchu.')";
    	   handler.executeQuery(query);
    	   query = "INSERT INTO PHONE_CONTACTS(wondername,wonderimage,wonderdesc) values ('Colosseum','img_e','The Colosseum or Coliseum, also known as the Flavian Amphitheatre (Italian: Anfiteatro Flavio or Colosseo) is an elliptical amphitheatre in the centre of the city of Rome, Italy. Built of concrete and stone, it was the largest amphitheatre of the Roman Empire, and is considered one of the greatest works of Roman architecture and engineering. It is the largest amphitheatre in the world.')";
    	   handler.executeQuery(query);
    	   query = "INSERT INTO PHONE_CONTACTS(wondername,wonderimage,wonderdesc) values ('Petra','img_f','Petra is a historical and archaeological city in the southern Jordanian governorate of Maan, that is famous for its rock-cut architecture and water conduit system. Another name for Petra is the Rose City due to the color of the stone out of which it is carved.')";
    	   handler.executeQuery(query);

	}

	public static LinkData GetbyId(String name){

        for(LinkData item : linkDatalist) {
            if (item.name.equals(name)) {
                return item;
            }
        }
        return null;
    }

}

package com.thenode.helloandroid;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelloAndroid extends Activity {
	int counter;
	Button add,sub;
	TextView display;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_android);
    	counter=0;
    	add=(Button) findViewById(R.id.bAdd);
    	sub=(Button) findViewById(R.id.bSub);
    	display=(TextView) findViewById(R.id.edit_message);
    	
    	add.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter++;
				display.setText("your total is "+counter);
			}
		});
    	sub.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				counter--;
				display.setText("your total is "+counter);
			}
		});
        
     // Set the user interface layout for this Activity
        // The layout file is defined in the project res/layout/main_activity.xml file
       }

   
  
    /** Called when the user clicks the Send button */
   

}

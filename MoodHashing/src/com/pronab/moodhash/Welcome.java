package com.pronab.moodhash;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;


public class Welcome extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MoodHash moodhash=((MoodHash)getApplicationContext());
		
		setContentView(R.layout.welcome);
		String quaddata ="{U:'" + moodhash.get_user()+"',pwd:'" + moodhash.getPwd()  + "'}";
	 
		QuadInvoke inv = new QuadInvoke();
					String rejson = (String)inv.QuadMake(quaddata,"QuickListMoods",getApplicationContext());

			 try {
				 
				System.out.println("startsorento moon");
				JSONObject js1 = new JSONObject(rejson);
				JSONArray WA1= js1.getJSONArray("WA");
				//JSONArray WA2 = WA1.getJSONArray(0);
				System.out.println("startsorento 2"+ WA1+"length" +  WA1.length() );
				String [] Moods  = new String[WA1.length()];
				for (int ir = 0 ; ir< WA1.length(); ir ++) {
					JSONObject mar = new JSONObject(( WA1.get(ir)).toString());
					String m = mar.getString("mood");
				Moods[ir] = m;
				}
				//System.out.println("sorento moon ir" + Moods[ir]);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Moods);


				// Assign adapter to ListView
				setListAdapter(adapter); 
				
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("sorrento error:" + e.getMessage());
			}
			 	 ;
	Button createButton = (Button)findViewById(R.id.createmood);
	
	System.out.println("buttona" + createButton.length());
	createButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			System.out.println("create button clicked");
			startActivity(new Intent(Welcome.this, Make.class));				
		}
	});
	
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		super.onListItemClick(l, v, position, id);

		   // ListView Clicked item index
		   int itemPosition     = position;
		   
		   // ListView Clicked item value
		   String  itemvalue    = (String) l.getItemAtPosition(position);
		   MoodHash moodhash=((MoodHash)getApplicationContext());  
		//   content.setText("Click : \n  Position :"+itemPosition+"  \n  ListItem : " +itemValue);
		   moodhash.setCurrentmood(itemvalue);
		   
		   //start activity moodtrail
		// for now go to moodtrail
			Intent it  = new Intent(Welcome.this, MoodTrail.class);
			it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(it);
		   
		   
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.starter, menu);
		return true;
	}

}

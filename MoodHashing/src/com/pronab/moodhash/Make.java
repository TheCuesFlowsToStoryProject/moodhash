package com.pronab.moodhash;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.View.OnKeyListener;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;


import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.KeyEvent;

public class Make extends Activity {
    MoodHash moodhash;
    String U;
    String pwd;
   EditText word1text ;
   EditText word2text;
   EditText word3text;
   EditText word4text;
   EditText word5text  ;
	String stmood ="";
	 private final TextWatcher  mTextEditorWatcher = new TextWatcher() {
	//	 editTextPassword.addTextChangedListener(mTextEditorWatcher);  
         public void beforeTextChanged(CharSequence s, int start, int count, int after)
         {
                    
         }

         public void onTextChanged(CharSequence s, int start, int before, int count)
         {
        	 stringMood();
         }

         public void afterTextChanged(Editable s)
         {
                    
                   
         }
 };
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	    	 return super.onKeyDown(keyCode, event);
	    }
	    if (keyCode == KeyEvent.KEYCODE_MENU ) {
	    	 return super.onKeyDown(keyCode, event);
	    }
	   /* word1text = (EditText) findViewById(R.id.word1Text);
		word2text = (EditText) findViewById(R.id.word2Text);
		word3text = (EditText) findViewById(R.id.word3Text);
		word4text = (EditText) findViewById(R.id.word4Text);
		word5text = (EditText) findViewById(R.id.word5Text); */
		// add a keylistener to keep track user input
		
                           stringMood();
					
					 
					
			 

				return  true;
			}
		 
	public void stringMood() { 
		
	  stmood =((EditText)findViewById(R.id.word1Text)).getText().toString() +"_" +
			  ((EditText)findViewById(R.id.word2Text)).getText().toString() + "_" + 
			  ((EditText)findViewById(R.id.word3Text)).getText().toString()	 +"_" +
  ((EditText)findViewById(R.id.word4Text)).getText().toString() +	"_"  +
	 ((EditText)findViewById(R.id.word5Text)).getText().toString()
			;
	  ((TextView)findViewById(R.id.moodmaking)).setText(stmood);
//	  System.out.println("set text "+stmood );
	  stmood=stmood.trim();
	  moodhash.setCurrentmood(stmood);
	  
	}
	 
	
 	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  moodhash=((MoodHash)getApplicationContext());
		
		setContentView(R.layout.make);
		String quaddata ="{U:'" + moodhash.get_user()+"',pwd:'" + moodhash.getPwd()  + "'}"; 
		   
		 word1text = (EditText) findViewById(R.id.word1Text);
		 word1text.addTextChangedListener(mTextEditorWatcher);
			word2text = (EditText) findViewById(R.id.word2Text);
			word2text.addTextChangedListener(mTextEditorWatcher);	
				
			word3text = (EditText) findViewById(R.id.word3Text);
			word3text.addTextChangedListener(mTextEditorWatcher);
			word4text = (EditText) findViewById(R.id.word4Text);
			word4text.addTextChangedListener(mTextEditorWatcher);
			word5text = (EditText) findViewById(R.id.word5Text);
			word5text.addTextChangedListener(mTextEditorWatcher);
		
	Button createButton = (Button)findViewById(R.id.MakeMyMood);
	
	System.out.println("buttona" + createButton.length());
	createButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			MoodHash moodhash=((MoodHash)getApplicationContext());
			String quaddata ="{U:'" + moodhash.get_user()+"',pwd:'" + moodhash.getPwd()  + "',S:'"+moodhash.getCurrentmood()+ "'}";
			//invoke url to get the list of currently active moods
	//http://localhost:8080/pureQuadroo/quadrooServer/QuickListMoods?UIdata={U:'pronob@gmail.com',pwd:'p1p2p3'}
			   QuadInvoke inv = new QuadInvoke();
						String rejson = (String)inv.QuadMake(quaddata,"QuickMood",getApplicationContext());

			//	   System.out.println("jsonreturned in trail:" + rejson);
					  // 
				 try {
					 
				//	System.out.println("startsorento moon");
					JSONObject js1 = new JSONObject(rejson);
					 
					if ( js1.getString("replystatus").compareTo("success") == 0)
					{ //start the dialog if to proceed with creating the trail 
						// for now go to moodtrail
						Intent it  = new Intent(Make.this, MoodTrail.class);
						it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(new Intent(Make.this, MoodTrail.class));
						
						}
					 
						
						;
					 
					}
					
					
				  catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("sorrento error:" + e.getMessage());
				}
			
			
			
			
		//	startActivity(new Intent(Make.this, MoodTrail));				
		}
	});
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.starter, menu);
		return true;
	}

}

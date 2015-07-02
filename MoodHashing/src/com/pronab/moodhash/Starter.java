package com.pronab.moodhash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.here.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
 

public class Starter extends Activity {

	String user;
	String password;
	MoodHash moodhash;  //moodhas is the global variable that also holds the state of the application

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 moodhash=((MoodHash)getApplicationContext());
		setContentView(R.layout.starter);
	 
	Button signinButton = (Button)findViewById(R.id.buttonSignIn);
	signinButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//.getText().toString()
		   user       =  ((EditText)findViewById(R.id.editTextUserNameToLogin)).getText().toString();
		   password   =  ((EditText)findViewById(R.id.editTextPasswordToLogin)).getText().toString();
		   String quaddata = "{U:'"+ user + "', pwd:'" + password + "'}";
		   moodhash.set_user(user);
		   moodhash.setPwd(password);
		  
		   QuadInvoke inv = new QuadInvoke();
			String rejson = (String)inv.QuadMake(quaddata,"LogIn",getApplicationContext());

		//   System.out.println("jsonreturned:" + rejson);
		  // 
	 
	 	 ;
	 	 try {
			JSONObject retJ = new JSONObject(rejson);
			if (((String)retJ.get("replystatus")).compareTo("ok") == 0) { 
		 		moodhash.set_user( user);
		 		//((EditText)findViewById(R.id.result)).setText("Log in success!");
		 		startActivity(new Intent(Starter.this, Welcome.class));		
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
					
					
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

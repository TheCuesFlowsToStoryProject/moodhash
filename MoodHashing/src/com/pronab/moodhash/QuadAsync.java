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

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

public class QuadAsync extends AsyncTask<Object, Integer, Object>{ 
	
	@Override
	protected Object doInBackground(Object... params) {
		//param for data
		String url1 = (String)params[0];
		String verb = (String)params[1];
		String quadroodata = (String)params[2];
		boolean mock = (Boolean) params[3];
			//replies
		String replyString = null; 
		JSONObject replyJson;
		
		if(mock){
			Log.d("STRING","mock true");
			replyString="{AllWords:[{'id':'c201','name':'Pronob'},{'id':'c202','name':'Ismail'}]}";
			try {
				replyJson = new JSONObject(replyString);
				return replyJson;
			} catch (JSONException e) {
				//if returned data is not json then 
			}	
		}
		
		if (!mock){
			Log.d("STRING","mock false");
			String postURL = 	url1 + verb;
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(postURL);
		
		 try {
		      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		      nameValuePairs.add(new BasicNameValuePair("UIdata",quadroodata));
		      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		      HttpResponse response = client.execute(post);
		      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		      String line = "";
		      while ((line = rd.readLine()) != null) {
		        try {
		        	replyString = line;
					Log.d("SR", line);
		        	JSONObject jsonReceived = new JSONObject(line);
					JSONObject Qjson = (JSONObject) jsonReceived.get("Qreply");
					replyString =  (String)Qjson.get("reply");
		 
					replyJson = new JSONObject(replyString);
					return replyJson; 
		        } catch (JSONException e) {
					return replyString;
				}
		      }
	            
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		}		
		return null;
	}		  
} 


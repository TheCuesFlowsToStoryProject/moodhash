package com.pronab.moodhash;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.util.Log;

public class QuadClientFileSenderloopold { 
	JSONObject replyJson = null;
	 String replyString  = null;
	 MoodHash moodhash;
	public QuadClientFileSenderloopold (String url,String verb ,String inputfile, Context con ) {
		moodhash=((MoodHash) con.getApplicationContext());
	String postURL = 	url + verb ;
	String quadroodata = null;
	  HttpClient client = new DefaultHttpClient();
	   HttpPost post = new HttpPost(postURL);
	 	// we make the quadroodata from reading a file
	   byte [] mybytearray  = new byte [90000];
	   // OutputStream iso = sock.getOutputStream();
	    FileInputStream ifos = null;
		try {
			ifos = new FileInputStream(inputfile);
		
	    BufferedInputStream bos = new BufferedInputStream(ifos);
	    int    bytesRead  = 0; int fileno = 10;
	   do  {
	
		     bytesRead = ifos.read(mybytearray,0,mybytearray.length);
		     if( bytesRead > 0 ){
		    	 
		 byte[] finalbytearray = new byte[bytesRead];
		    System.arraycopy(mybytearray, 0,finalbytearray, 0, bytesRead);
		
		    String objString = new String(Base64.encodeBase64(finalbytearray));
		    
		    System.out.println("object in String" + objString);
		 // your string
	    //    String yourString = new String(Hex.encodeHex(finalbytearray));
		    
	//	    System.out.println("object in String" + objString);
		    String  fn = "andUser$"+ fileno;
		   quadroodata = "{W:'" +objString+  "',S:'" + fn + "'}"; 
		
		    fileno = fileno + 1;
		
	 
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      //?UIdata={W:a.b.c.aword}
	   //   nameValuePairs.add(new BasicNameValuePair("registrationid",
	    //          "123456789"));
	      nameValuePairs.add(new BasicNameValuePair("UIdata",
	         quadroodata));
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    //   System.out.println("post:"+post.getParams().getParameter("UIdata"));
	      HttpResponse response = client.execute(post);
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	      String line  = rd.readLine();
	      System.out.println("Client Received"+line); }
	      
	      
	    } 
	   while(bytesRead > -1);
		}
            
	     catch (IOException e) {
	      e.printStackTrace();
	    }
	  //thn we send another request to concatenate the files who name are in thee WA array
	  //all good , ready to concatenate files
			postURL = 	url + "ConcatFiles" ;
			quadroodata = null;
		    client = new DefaultHttpClient();
		    post = new HttpPost(postURL);
		   quadroodata = "{W:' ' }"; 
			
		     
		
	 
	      List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	      //?UIdata={W:a.b.c.aword}
	   //   nameValuePairs.add(new BasicNameValuePair("registrationid",
	    //          "123456789"));
	      nameValuePairs.add(new BasicNameValuePair("UIdata",
	         quadroodata));
	      try {
			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		
	    //   System.out.println("post:"+post.getParams().getParameter("UIdata"));
	      HttpResponse response = client.execute(post);
	      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	      String line = "";
	      while ((line = rd.readLine()) != null) {
	        try {
	        	replyString = line;
				Log.d("SR", line);
	        //	JSONObject jsonReceived = new JSONObject(line);
			// 	JSONObject Qjson = (JSONObject) jsonReceived.get("Qreply");
			//	replyString =  (String)Qjson.get("reply");
	 
			//	replyJson = new JSONObject(replyString);
				//return replyJson; 
	        } catch ( Exception e) { System.out.println("concat error:"  + e.getMessage());
				//return replyString;
			}
	      }
	      
	      
	      
	      
	      
	      } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	} 
	
	public JSONObject getJsonReply() { return this.replyJson; }
	public String getStringReply(){ return this.replyString ;}
 
  }
  
  
 


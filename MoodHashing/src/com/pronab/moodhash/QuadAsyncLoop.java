package com.pronab.moodhash;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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

public class QuadAsyncLoop extends AsyncTask<Object, Integer, Object>{ 
	
	
	public void  QuadClientFileSenderloop (String url, String inputfile,String pickey, Context con ) {
	MoodHash	moodhash=((MoodHash) con.getApplicationContext());
	String U = moodhash.get_user();
	//String S = inputfile;
 	String S = moodhash.getCurrentmood() + pickey;
	String pwd = moodhash.getPwd();
 	String postURL = 	url + "WriteBytes";
	String quadroodata = null;
	  HttpClient client = new DefaultHttpClient();
	   HttpPost post = new HttpPost(postURL);
	 	// we make the quadroodata from reading a file
	//   byte [] mybytearray  = new byte [10000000];
	   ByteBuffer buffer = ByteBuffer.allocate(1000000);
	   byte [] mybytearray  = buffer.array();
	 //  buffer.
	   // OutputStream iso = sock.getOutputStream();
	    FileInputStream ifos = null;
		try {
			ifos = new FileInputStream(inputfile);
		
	    BufferedInputStream bos = new BufferedInputStream(ifos);
	    int    bytesRead  = 0; int fileno = 10;
	   do  {
	        System.out.println("array length :" + bytesRead);
		    bytesRead = ifos.read(mybytearray,0,mybytearray.length);
		    System.out.println("bytes read:" + bytesRead + "array length;" + mybytearray.length);
		     if( bytesRead > 0 ){
		    	 
		 byte[] finalbytearray = new byte[bytesRead];
		    System.arraycopy(mybytearray, 0,finalbytearray, 0, bytesRead);
		
		    String objString = new String(Base64.encodeBase64(finalbytearray));
		    
		//    System.out.println("object in String" + objString);
		 // your string
	    //    String yourString = new String(Hex.encodeHex(finalbytearray));
		    
	    
		    String  fn = moodhash.getCurrentmood() + "$"+ fileno;
		   quadroodata = "{W:'" +objString+"',S:'" + S + "',F:'" + fn + "', U:'" + U +  "',pwd='"+pwd +"' }"; 
		
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
	   //   System.out.println("Client Received"+line);
	      }
	      
	      
	    } 
	   while(bytesRead > -1);
		}
            
	     catch (IOException e) {
	      e.printStackTrace();
	    }
	  //thn we send another request to concatenate the files who name are in thee WA array
	  //all good , ready to concatenate files
		//buffer can be released now;
				buffer = null;
			postURL = 	url + "ConcatFiles" ;
			quadroodata = null;
		    client = new DefaultHttpClient();
		    post = new HttpPost(postURL);
		    //S is having dir  name which is the mood and entry no concat
		    System.out.println("concat files under" +S);
		   quadroodata = "{S:'" + S + "', U:'" + U +  "',pwd='"+pwd +"' }"; 
			
		     
		
	 
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
	      String line  = rd.readLine();
	      System.out.println("file write response"+line);
	      } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	} 
	
	
	
	
	
	@Override
	protected Object doInBackground(Object... params) {
		
		
		
		
		//param for data
		String url1 = (String)params[0];
		String inputfile = (String)params[1];
		String pickey = (String) params[2];
		Context con  = (Context)params[3];
		
	////	public QuadClientFileSenderloop (String url,String inputfile, Context con )
		QuadClientFileSenderloop(url1, inputfile, pickey,con);
		
		
			 		
		return null;
	}		  
} 


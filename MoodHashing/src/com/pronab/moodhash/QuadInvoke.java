package com.pronab.moodhash;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.content.Context;

public class QuadInvoke {

	public Context context;
	static InputStream is = null;
	static JSONObject jObj = null;
	//static String json = "";
	String url = "http://192.168.0.2:8131/pureQuadroo/quadrooServer/" ;
	//String verb = "LogIn";
	boolean mock = false;
	// constructor
	public QuadInvoke(){ } 
	public Object QuadMake(final String quadroodata,final String verb, Context context) {
		
		this.context = context;
		Object jo = null;
        System.out.println("sending from quadmake;" + quadroodata);
		//basics for QuadClient
		//		
		QuadAsync  qdC = new QuadAsync();
		qdC.execute(url,verb,quadroodata,mock);
		
		try {
			if(qdC.get().getClass().getSimpleName().toString().equals("String"))
			{
				jo = qdC.get().toString();
			}
			else if (qdC.get().getClass().getSimpleName().toString().equals("JSONObject"))
			{
				jo = (JSONObject)qdC.get();
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return jo;
	}
}
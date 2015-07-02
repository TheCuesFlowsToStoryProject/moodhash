package com.pronab.moodhash;

import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;

import android.content.Context;

public class QuadLoopInvoke {

	public Context context;
	static InputStream is = null;
	static JSONObject jObj = null;
	//static String json = "";
	String url = "http://192.168.0.2:8131/pureQuadroo/quadrooServer/" ;
	//String verb = "LogIn";
	boolean mock = false;
	// constructor
	public QuadLoopInvoke(){ } 
	public Object QuadMake( String inputfile, String pickey, Context con) {
		
		this.context = context;
		Object jo = "-999";
  //      System.out.println("sending from quadmake;" + quadroodata);
		//basics for QuadClient
		//		
		QuadAsyncLoop  qdC = new QuadAsyncLoop();
		//String url, String inputfile, Context con 
		
	 	qdC.execute(url,inputfile,pickey,con);
	 	jo =" 0";
	 	/*
		
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
		} */
		return jo;
	}
}
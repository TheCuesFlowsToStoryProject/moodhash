package com.pronab.moodhash;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.io.File;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
 

//import com.example.here.JSONParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
 

public class MoodTrail  extends Activity {
  private static final int IMAGE_MAX_SIZE = 0;
	//  int entry = 01;
	String user;
	String password;
	MoodHash moodhash;
	String currentmood ;
	final int TAKE_PICTURE = 1;
   String picfile ;
    File photo ;
    private Uri imageUri ;
    String inputfile ; String pickey ;
	private ImageView addImageView(Uri selectedImage  ){
		getContentResolver().notifyChange(selectedImage, null);
		LinearLayout ln = ((LinearLayout)findViewById(R.id.contentholder));
	//	ImageView inv = ((ImageView)findViewById(R.id.imageholder));
		 
	//ImageView imgv = 	addImageView(ln );
	ImageView imageView = new ImageView(this);
	LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
     
       imageView.setLayoutParams(lpView);
       imageView.setMinimumHeight(30);
       imageView.setMaxHeight(30);
     
    ln.addView(imageView);
//  ImageView imageView = (ImageView) findViewById(R.id.IMAGE);
    ContentResolver cr = getContentResolver();
    Bitmap bitmap;
    try {
         bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
//    System.out.println(" orig row bytes" +   bitmap.getRowBytes());
       imageView.setImageBitmap(bitmap);
       scaleImage(imageView ,80);
    return imageView;
	     
    
    } catch (Exception e) {
        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                .show();
        Log.e("Camera", e.toString());
        return null;
    }
	}
	private void addWebView(LinearLayout layout  ){
	     WebView engine = new WebView(this);
	    // WebView engine = (WebView) findViewById(R.id.web_engine);
	     engine.getSettings().setJavaScriptEnabled(true);
	     
	     /*    String data = "<html>" +
	                 "<body><img src='" +  url + "'/></body>" +
	                 "</html>";  

	         engine.loadData(data, "text/html", "UTF-8");  */
	     
	     
	     //.setImageResource(R.drawable.ic_launcher);
	     layout.addView(engine);
	    }
	private void addTextView(LinearLayout layout, String t){
	     TextView textView = new TextView(this);
	     LayoutParams lpView = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	      
	       textView.setLayoutParams(lpView) ;
	    //  textView.setSingleLine();
	      textView.setText(t);
	      textView.setBackgroundColor(16711681);
	    //  leftMarginParams.leftMargin = 50;
	     layout.addView(textView);
	    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 moodhash=((MoodHash)getApplicationContext());
		 currentmood = moodhash.getCurrentmood();
	//	   String picfile = moodhash.photostore +currentmood+ entry+ ".png"; 
		setContentView(R.layout.makemoodtrail);
	//	 photo = new File(Environment.getExternalStorageDirectory(),  picfile);
	//	 imageUri = Uri.fromFile(photo);
		String quaddata ="{U:'" + moodhash.get_user()+"',pwd:'" + moodhash.getPwd()  + "',S:'" + currentmood + "'}";
		((TextView)findViewById(R.id.currentmood)).setText(currentmood);
		
		//invoke url to get the list of currently active moods
//http://localhost:8080/pureQuadroo/quadrooServer/QuickListMoods?UIdata={U:'pronob@gmail.com',pwd:'p1p2p3'}
		
	//	System.out.println("trail quaddata sent:"+ quaddata);
		   QuadInvoke inv = new QuadInvoke();
					String rejson = (String)inv.QuadMake(quaddata,"QuickListTrail",getApplicationContext());

			 try {
				 
			//	System.out.println("trailsorento moon trail"+ rejson);
			    JSONObject js1 = new JSONObject(rejson);
				JSONArray WA1= js1.getJSONArray("WA");
				//JSONArray WA2 = WA1.getJSONArray(0);
			//	System.out.println("trail 2"+ WA1+"length" +  WA1.length() );
				String [] Moodtrail = new String[WA1.length()];
				
				LinearLayout ln = ((LinearLayout)findViewById(R.id.contentholder));
				
				for (int ir = 0 ; ir< WA1.length(); ir ++) {
					JSONObject mtrar = new JSONObject(( WA1.get(ir)).toString());
					//first time no content !!
					String m = mtrar.getString("content");
					String t = mtrar.getString("type");
					String o = mtrar.getString("ky");
					
				Moodtrail[ir] = m;
				 //add a text vie for each note:
				System.out.println ("t received:" + t);
				if (t.compareTo("note") == 0 )
				 {addTextView(ln, m);}
				else {
					//JSONObject jo = new JSONObject(o);
					//jo.getString("time");
				//	System.out.println(" in trail image file objid string:"+ o);
					 
					
				//	m =Environment.getExternalStorageDirectory() + moodhash.photostore + moodhash.getCurrentmood() +o+".png";
					 photo = new File(Environment.getExternalStorageDirectory(),   moodhash.photostore + moodhash.getCurrentmood() +o+".png");
		      	 	 imageUri = Uri.fromFile(photo);
					
					
					
					addImageView(imageUri);}
						
				}
			//	 System.out.println("sorento end on create " + Moodtrail.toString());
		//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Moodtrail);


				// Assign adapter to ListView
		//		setListAdapter(adapter); 
				 
				
				 
				 
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("sorrento error:" + e.getMessage());
			}
		
		
		
	 
	 
	Button notebutton = (Button)findViewById(R.id.notebutton);
	notebutton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 moodhash=((MoodHash)getApplicationContext());
			 String user = moodhash.get_user();
			 String pwd = moodhash.getPwd();
			 String S = moodhash.getCurrentmood();
			 
			 String notes       =  ((EditText)findViewById(R.id.noteput)).getText().toString();
			  
			   String quaddata = "{ F:'note', U:'"+ user + "', pwd:'" + pwd + "',S:'" + S+ "',WA:['" +notes+ "'] " +  "}";
			//   System.out.println("quaddata from add note button:" + quaddata);
				   QuadInvoke inv = new QuadInvoke();
				 //  String 
			String rejson = (String)inv.QuadMake(quaddata,"QuickNote",getApplicationContext());

	 //  System.out.println("json add note returned:" + rejson);
		  // 
	 
	 	 ;
	 	 try {
			JSONObject retJ = new JSONObject(rejson);
			if (((String)retJ.get("replystatus")).compareTo("success") == 0) { 
		 		moodhash.set_user( user);
		 		//((EditText)findViewById(R.id.result)).setText("Log in success!");
		 		startActivity(new Intent(MoodTrail.this, MoodTrail.class));
		 	//	Intent intent = getIntent();
		 	//	finish();
		 	//	startActivity(intent);
		 		
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 /*	if (rejson.replystatus.compareTo("ok") == 0) { 
	 		moodhash.set_user( user);
	 		((EditText)findViewById(R.id.result)).setText("Log in success!");
	 		startActivity(new Intent(Starter.this, Welcome.class));		
	 		
	 	}
	 	else {
	 		((TextView)findViewById(R.id.result)).setText("Log in failed!");
	 		
	 	} 
	  */
					
					
		}
	}	);
	
	 
	Button picbutton = (Button)findViewById(R.id.picbutton);
	picbutton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			 moodhash=((MoodHash)getApplicationContext());
			 String user = moodhash.get_user();
			 String pwd = moodhash.getPwd();
			 String S = moodhash.getCurrentmood();
			// first we place a image marker in the db that also returns the objectid to be used as the imagef ile name 
		 	   
			   String quaddata = "{ F:'image',U:'"+ user + "', pwd:'" + pwd + "',S:'" + S+ "',WA:[''] " +  "}";
			//   System.out.println("quaddata from add image:" +quaddata);
				   QuadInvoke inv = new QuadInvoke();
				 //  String 
			String rejson = (String)inv.QuadMake(quaddata,"QuickNote",getApplicationContext());
			

	  // System.out.println("json add note returned:" + rejson); 
	  JSONObject jp ;
	   ////
	   JSONObject js1;
	try {
		js1 = new JSONObject(rejson);
	
		pickey = js1.getString("rW");
		//JSONArray WA2 = WA1.getJSONArray(0);
	//	System.out.println("trail click"+ WA1+"length" +  WA1.length() );
	//	String [] Moodtrail = new String[WA1.length()];
		
	//	LinearLayout ln = ((LinearLayout)findViewById(R.id.contentholder));
		
		 
	//		JSONObject mtrar = new JSONObject(( WA1.get(0)).toString());
			 
	 
		//	.println("identifier of new image:" +o);
	   //////
	   
	   
	    
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				pickey ="temp";
			}
             // ready to take picture
       	   String picfile = moodhash.photostore +currentmood+ pickey + ".png"; 
      		 
      	  photo = new File(Environment.getExternalStorageDirectory(),  picfile);
      	 	 imageUri = Uri.fromFile(photo);
			 Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		        
		         inputfile = photo.getAbsolutePath();
		         System.out.println("Camera photo path:" + inputfile);
		        intent.putExtra(MediaStore.EXTRA_OUTPUT,
		                Uri.fromFile(photo));
		        
		        startActivityForResult(intent, TAKE_PICTURE);
			 
			 
					
					
		}
	}	);
						
	
	
	
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	    super.onActivityResult(requestCode, resultCode, data);
	    moodhash=((MoodHash)getApplicationContext());
		 String user = moodhash.get_user();
		 String pwd = moodhash.getPwd();
		 String S = moodhash.getCurrentmood();
		 String F = "image";
	    switch (requestCode) {
	    case TAKE_PICTURE:
	        if (resultCode == Activity.RESULT_OK) {
	            //Uri imageUri;
	        	System.out.println("camera activity:"+ resultCode );
	            Uri selectedImage = imageUri;
	        //    getContentResolver().notifyChange(selectedImage, null);
	            addImageView(imageUri);
   
                Toast.makeText(this, "This bm file: "+selectedImage.toString(),
                        Toast.LENGTH_LONG).show();
            
	            

	            QuadLoopInvoke inv = new QuadLoopInvoke();
	            String rejson = (String)inv.QuadMake(inputfile,pickey,getApplicationContext());
				

	     	//   System.out.println("json add note returned:" + rejson); 
	     	//   String pic ;JSONObject jp ; 
	        //    QuadClientFileSenderloop qc = new QuadClientFileSenderloop("http://192.168.0.5:8080/pureQuadroo/quadrooServer/", "WriteBytes",inputfile,this);
                
              
	            
	        }
	        
	        
	        
	    }
	}
	private void scaleImage(ImageView view, int boundBoxInDp)
	{
	    // Get the ImageView and its bitmap
	    Drawable drawing = view.getDrawable();
	    Bitmap bitmap = ((BitmapDrawable)drawing).getBitmap();

	    // Get current dimensions
	    int width = bitmap.getWidth();
	    int height = bitmap.getHeight();

	    // Determine how much to scale: the dimension requiring less scaling is
	    // closer to the its side. This way the image always stays inside your
	    // bounding box AND either x/y axis touches it.
	    float xScale = ((float) boundBoxInDp) / width;
	    float yScale = ((float) boundBoxInDp) / height;
	    float scale = (xScale <= yScale) ? xScale : yScale;

	    // Create a matrix for the scaling and add the scaling data
	    Matrix matrix = new Matrix();
	    matrix.postScale(scale, scale);

	    // Create a new bitmap and convert it to a format understood by the ImageView
	    Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
	    BitmapDrawable result = new BitmapDrawable(scaledBitmap);
	    width = scaledBitmap.getWidth();
	    height = scaledBitmap.getHeight();

	    // Apply the scaled bitmap
	    view.setImageDrawable(result);

	    // Now change ImageView's dimensions to match the scaled image
	    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
	    params.width = width;
	    params.height = height;
	    view.setLayoutParams(params);
	}

	private int dpToPx(int dp)
	{
	    float density = getApplicationContext().getResources().getDisplayMetrics().density;
	    return Math.round((float)dp * density);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.starter, menu);
		return true;
	}
	

	private Bitmap decodeFile(File f){
	    Bitmap b = null;

	        //Decode image size
	    BitmapFactory.Options o = new BitmapFactory.Options();
	    o.inJustDecodeBounds = true;

	    FileInputStream fis;
		try {
			fis = new FileInputStream(f);
		
	    BitmapFactory.decodeStream(fis, null, o);
	    fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    int scale = 1;
	    if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
	        scale = (int)Math.pow(2, (int) Math.round(Math.log(IMAGE_MAX_SIZE / 
	           (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
	    }

	    //Decode with inSampleSize
	    BitmapFactory.Options o2 = new BitmapFactory.Options();
	    o2.inSampleSize = scale;
	    try {
			fis = new FileInputStream(f);
		
	    b = BitmapFactory.decodeStream(fis, null, o2);
	   
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return b;
	    
	    
	    
	    
	}

	
	
}

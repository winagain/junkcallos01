package com.hkjunkcall.autofilter;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public  class  Common {
	public static void Logit(String lc_mess) {
		Log.v("BBBB", lc_mess ) ; 
/*		 File gpxfile = new File("/mnt/sdcard/1.txt");
		 FileWriter writer = new FileWriter(gpxfile);
		 writer.append(lc_mess);
		 writer.flush();
		 writer.close();
*/	}
	public static String getPath(String lc_file) {
		String result = "" ; 
		String paths[] = lc_file.split("/") ;
		int count = paths.length - 1 ; 
		for(int i=0;i<count;i++){
		    result = result + paths[i] + "/" ;  
		} 
		return result ; 
	}
	public static String str_extr_betw(String lc_data , String lc_start_tag , String lc_end_tag) {
		String result = "" ;
		int startLoc = lc_data.indexOf(lc_start_tag) + lc_start_tag.length() ;
		int endLoc = lc_data.indexOf(lc_end_tag) ; 
		
		// Common.Logit("startLoc=" + startLoc + " endloc=" + endLoc) ; 
		
		result = lc_data.substring(startLoc, endLoc) ; 
		return result ; 
	}
	
	public  static void toast_mess(Context ctx  , String message) {
		Toast.makeText(ctx, message , Toast.LENGTH_LONG).show();
	}
	public static void ShowAlertDialog(Context ctx, String title , String message) {
		 Builder MyAlertDialog = new AlertDialog.Builder(ctx);
		 MyAlertDialog.setTitle(title);
		 MyAlertDialog.setMessage(message);
		 MyAlertDialog.show();
	}	
	
	public static String ReadWholeTxtFile( String lc_file) { 
		String result = "" ;
		StringBuilder sb = new StringBuilder("") ; 
		BufferedReader reader;
		try {
		    // final InputStream file = getAssets().open("JunkPhoneList.csv");
		    // final FileInputStream file = openFileInput( lc_file) ;
		    FileInputStream file = new FileInputStream (new File(lc_file)); 
		    reader = new BufferedReader(new InputStreamReader(file));
		    String line = reader.readLine();
		    int ii = 0 ; 
		    while(line != null){
		    	// result = line ;
/*		    	ii++ ; 
		    	Common.Logit(ii + " " + line) ; 		    	
*/		    	sb.append(line) ; 
		    	line = reader.readLine();			        
		    }
		    file.close() ; 
		    reader.close() ; 
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			result = ex.toString() ;
			sb.append("FAIL - " + ex.toString()) ; 
			// Common.ShowAlertDialog(getApplicationContext(), "bbb", result ) ; 
		}
		// return result ; 			
		return sb.toString() ; 
	}    

	public static String xxReadWholeTxtFile( String lc_file) { 
		String result = "" ;
		StringBuilder sb = new StringBuilder("") ; 
		BufferedReader reader;
		try {
		    // final InputStream file = getAssets().open("JunkPhoneList.csv");
		    // final FileInputStream file = openFileInput( lc_file) ;
		    FileInputStream file = new FileInputStream (new File(lc_file)); 
		    reader = new BufferedReader(new InputStreamReader(file));
		    String line = reader.readLine();
		    while(line != null){
		    	// result = line ; 
		    	sb.append(line) ; 
		    	line = reader.readLine();			        
		    }
		    file.close() ; 
		    reader.close() ; 
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			result = ex.toString() ;
			sb.append("FAIL - " + ex.toString()) ; 
			// Common.ShowAlertDialog(getApplicationContext(), "bbb", result ) ; 
		}
		// return result ; 			
		return sb.toString() ; 
	}	
	
	public static String DownloadFiles(String lc_url , String lc_local_file){
		String result = "" ;
		InputStream is  ;
		FileOutputStream fos ; 
        try {
	            // URL u = new URL("http://www.qwikisoft.com/demo/ashade/20001.kml");
	        	URL u = new URL(lc_url);
	            is = u.openStream();
	
	            DataInputStream dis = new DataInputStream(is);
	
	            byte[] buffer = new byte[1024];
	            int length;
	
	            // FileOutputStream fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory() + "/" + "data/test.kml"));
            	fos = new FileOutputStream(new File(lc_local_file));            
	            while ((length = dis.read(buffer))>0) {
	              fos.write(buffer, 0, length);
	            }
	            fos.close() ; 
	            is.close() ; 
        	} catch (Exception ex) {
        	   result = "FAIL - " + ex.toString() ; 
       } 
         return result ; 
	}
	
	
	public static String unpackZip(String zipname , String path)
	{       
	     InputStream is;
	     ZipInputStream zis;
	     try 
	     {
	         is = new FileInputStream(zipname);
	         zis = new ZipInputStream(new BufferedInputStream(is));          
	         ZipEntry ze;

	         while((ze = zis.getNextEntry()) != null) 
	         {
	             ByteArrayOutputStream baos = new ByteArrayOutputStream();
	             byte[] buffer = new byte[1024];
	             int count;

	             String filename = ze.getName();
	             FileOutputStream fout = new FileOutputStream(path + filename);

	             // reading and writing
	             while((count = zis.read(buffer)) != -1) 
	             {
	                 baos.write(buffer, 0, count);
	                 byte[] bytes = baos.toByteArray();
	                 fout.write(bytes);             
	                 baos.reset();
	             }

	             fout.close();               
	             zis.closeEntry();
	         }

	         zis.close();
	     } 
	     catch(IOException e)
	     {
	         e.printStackTrace();
	         return "FAIL";
	     }

	    return "OK";
	}

	public static Date getFileDate(String lc_cmd , String lc_file) {
		Date lastModDate = null ;
		File file = new File(lc_file);		
		if(file.exists()) {		
			lastModDate = new Date(file.lastModified());
			// Log.i("File last modified @ : "+ lastModDate.toString());
		}
		return lastModDate  ; 		
	}
	
	
	public static void delay(int seconds) {
		try{
		    // delay 1 second
		    Thread.sleep(seconds * 1000);		       
		} catch(InterruptedException e){
		    e.printStackTrace();
		        
		}
	}	
	
	
}


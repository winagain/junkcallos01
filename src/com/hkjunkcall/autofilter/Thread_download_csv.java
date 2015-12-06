package com.hkjunkcall.autofilter;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public class Thread_download_csv extends Thread  {
	private Context ctx ;
	private String csv_file ;
	private View v ; 
	
	
	// public void setup(Context in_ctx , String in_csv_file) {
	public void setup(View in_v , String in_csv_file) {
		v = in_v ; 
		ctx = in_v.getContext() ; 
		csv_file = in_csv_file ; 
		String tmp1x = v.getParent().getClass().toString() + " " + v.getClass().toString() ;   
		Common.Logit(tmp1x) ; 
	}
	
    @Override
    public void run() {
        // TODO Auto-generated method stub    	
        super.run();	    
        try {
						
			String tmp1x = "" , lc_url; 
			// Common.ShowAlertDialog(v.getContext(), "Title " , lc_local_file ) ;
			// Common.toast_mess(, "HELLO") ;
			
			// lc_url = "http://toimy.blogspot.hk/2010/08/android-alertdialog.html" ; 
			 

			// to avoid junkcall01's admin change their csv , change to using my own appspot 
			lc_url = "http://junkcall01.appspot.com/junkcallos01_web" ;
			// lc_url = "http://hkjunkcall.com/Download/download.asp?DownloadID=6" ;
			
			tmp1x = Common.DownloadFiles(lc_url , csv_file) ;


			Common.Logit(lc_url) ; 
			
			String lc_data = Common.ReadWholeTxtFile(csv_file) ;
			String lc_app_dir  = Common.getPath(csv_file) ;
/*			lc_data.replaceAll("[\n]", "") ;  
			lc_data.replaceAll("[\r]", "") ;
*/			/*<a href='http://dl2.hkjunkcall.com/Download/files/20151203113919/Short/JunkPhoneList.zip' target='main'>*/
			Common.Logit(lc_data) ; 
			lc_url = Common.str_extr_betw(lc_data , "<a href='" , "' target='main'>") ;
			
			if (lc_url.contains(".zip")) { 
				String lc_zip_file = lc_app_dir + "tmp.zip" ;
				
				Common.Logit(lc_url) ;
				Common.Logit(lc_app_dir) ;
				Common.Logit(lc_zip_file) ;
				
				tmp1x = Common.DownloadFiles(lc_url , lc_zip_file) ;
				Common.unpackZip(lc_zip_file, lc_app_dir) ;
			} else {
				tmp1x = Common.DownloadFiles(lc_url , csv_file) ;
			}

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}

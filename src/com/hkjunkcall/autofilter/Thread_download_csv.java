package com.hkjunkcall.autofilter;

import android.content.Context;
import android.util.Log;

public class Thread_download_csv extends Thread  {
	private Context ctx ;
	private String csv_file ; 
	
	public void setup(Context in_ctx , String in_csv_file) {
		ctx = in_ctx ; 
		csv_file = in_csv_file ; 
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
			lc_url = "http://hkjunkcall.com/Download/download.asp?DownloadID=6" ; 
			tmp1x = Common.DownloadFiles(lc_url , csv_file) ;

			Common.Logit(lc_url) ; 
			
			String lc_data = Common.ReadWholeTxtFile(csv_file) ;
			String lc_app_dir  = Common.getPath(csv_file) ;
/*			lc_data.replaceAll("[\n]", "") ;  
			lc_data.replaceAll("[\r]", "") ;
*/			/*<a href='http://dl2.hkjunkcall.com/Download/files/20151203113919/Short/JunkPhoneList.zip' target='main'>*/
			Common.Logit(lc_data) ; 
			lc_url = Common.str_extr_betw(lc_data , "<a href='" , "' target='main'>") ;
			
			String lc_zip_file = lc_app_dir + "tmp.zip" ;
			
			Common.Logit(lc_url) ;
			Common.Logit(lc_app_dir) ;
			Common.Logit(lc_zip_file) ;
			
			tmp1x = Common.DownloadFiles(lc_url , lc_zip_file) ;
			Common.unpackZip(lc_zip_file, lc_app_dir) ; 
//			Common.toast_mess(ctx, lc_url + lc_zip_file) ;   
//			Common.ShowAlertDialog(ctx, "Title " , lc_zip_file) ;			
        	//Your code goes here
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}

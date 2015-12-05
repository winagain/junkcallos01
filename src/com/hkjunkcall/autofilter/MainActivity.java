package com.hkjunkcall.autofilter;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Main activity, with button to toggle phone calls detection on and off.
 * @author Moskvichev Andrey V.
 *
 */
public class MainActivity extends Activity {

	private boolean detectEnabled;
	
	private TextView textViewDetectState;
	private Button buttonToggleDetect;
	private Button buttonExit , buttonDownload;
	
	private String csv_file ; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        csv_file = CallHelper.get_csv_file(this) ; 
        
        textViewDetectState = (TextView) findViewById(R.id.textViewDetectState);
        
        buttonToggleDetect = (Button) findViewById(R.id.buttonDetectToggle);
        buttonToggleDetect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDetectEnabled(!detectEnabled);
				// Common.toast_mess(v.getContext(), csv_file) ; 
			}
			
		});
        
        buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// setDetectEnabled(false);
/*				String lc_html = "<a href='http://dl2.hkjunkcall.com/Download/files/20151203113919/Short/JunkPhoneList.zip' target='main'>" ; 						
				String tmp1x ;  
*/				// tmp1x = Common.str_extr_betw(lc_html , "<a href='" , "' target='main'>") ;
/*				tmp1x = Common.getPath(csv_file) ;
				Common.toast_mess(v.getContext(), tmp1x) ;
*/				
				// Common.toast_mess(v.getContext(), CallHelper.get_csv_file(v.getContext()) ) ; 
				// String lc_data = Common.ReadWholeTxtFile(csv_file) ;
				// Common.toast_mess(v.getContext(), csv_file + lc_data) ; 				
				MainActivity.this.finish();					
				
			}
		});
        

        buttonDownload = (Button) findViewById(R.id.buttonDownload);
        buttonDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// setDetectEnabled(false);							
				Thread_download_csv download_csv = new Thread_download_csv() ; 
				download_csv.setup(v.getContext() , csv_file ) ; 
				download_csv.start() ;  
			}
		});

        
        Date last_csv_date = Common.getFileDate("" ,csv_file ) ; 
        if (last_csv_date != null) {
        	buttonDownload.setText("Download JunkCall Data , Lastest : " + last_csv_date) ;
        }
    }


    
    
    private void download_csv() {
		new Thread() {
		    @Override
		    public void run() {
		        try {
					String lc_app_dir = getApplicationInfo().dataDir ;
					String lc_local_file = lc_app_dir + "/junkphonelist.csv"  ;					
					String tmp1x = "" , lc_url; 
					// Common.ShowAlertDialog(v.getContext(), "Title " , lc_local_file ) ;
					// Common.toast_mess(, "HELLO") ;
					
					// lc_url = "http://toimy.blogspot.hk/2010/08/android-alertdialog.html" ; 
					lc_url = "http://hkjunkcall.com/Download/download.asp?DownloadID=6" ; 
					tmp1x = Common.DownloadFiles(lc_url , lc_local_file) ;
					// Common.ShowAlertDialog(v.getContext(), "Title " , tmp1x) ;

		        	//Your code goes here
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }
		}.start();						    
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    private void setDetectEnabled(boolean enable) {
    	detectEnabled = enable;
    	
        Intent intent = new Intent(this, CallDetectService.class);
    	if (enable) {
    		 // start detect service 
            startService(intent);
            
            buttonToggleDetect.setText("Turn off");
    		textViewDetectState.setText("Detecting");
    	}
    	else {
    		// stop detect service
    		stopService(intent);
    		
    		buttonToggleDetect.setText("Turn on");
    		textViewDetectState.setText("Not detecting");
    	}
    }
    
    

}

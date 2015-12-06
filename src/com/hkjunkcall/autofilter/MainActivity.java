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
	
	private TextView textViewStatus;
	private Button buttonToggleDetect;
	private Button buttonExit , buttonDownload;
	
	private String csv_file ; 
	Thread_download_csv download_csv ; 	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        csv_file = CallHelper.get_csv_file(this) ; 
        
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        
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
				download_csv = new Thread_download_csv() ; 
				// download_csv.setup(v.getContext() , csv_file ) ; 
				download_csv.setup(v , csv_file ) ;
				download_csv.start() ;
				
				// Common.ShowAlertDialog(v.getContext(), "" , "Downloading....") ;
				Common.delay(5) ;
				
/*				String lc_progress = "Downloading...." ; 
				for (int ii=0 ; ii<10;ii++) {					
					lc_progress = lc_progress + "." ; 
					refresh_status(lc_progress) ;
					Common.delay(1) ;
				}
*/				
				
				refresh_status("") ;
				Common.toast_mess(v.getContext(), "Completed.") ; 
			}		
		});        
        refresh_status("") ; 
    }


    public void refresh_status(String lc_mess) {
    	if (lc_mess.length() ==  0) { 
	        Date last_csv_date = Common.getFileDate("" ,csv_file ) ;
	        lc_mess = "Please Download JunkCall Data"  ;
	        if (last_csv_date != null) {
	        	lc_mess = last_csv_date.toLocaleString() ; 
	        }
    	}
    	lc_mess = "JunkCall Data : " + lc_mess ;  
    	textViewStatus.setText(lc_mess) ;        
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
            textViewStatus.setText("Detecting");
    	}
    	else {
    		// stop detect service
    		stopService(intent);
    		
    		buttonToggleDetect.setText("Turn on");
    		textViewStatus.setText("Not detecting");
    	}
    }
    
    

}

package com.hkjunkcall.autofilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Helper class to detect incoming and outgoing calls.
 * @author Moskvichev Andrey V.
 *
 */
public class CallHelper {

	/**
	 * Listener to detect incoming calls. 
	 */
	public static String get_csv_file(Context ctx) {
		String lc_app_dir = ctx.getApplicationInfo().dataDir ;
		return lc_app_dir.trim() + "/JunkPhoneList.csv"  ;
	}
	
	private class CallStateListener extends PhoneStateListener {
		@Override				
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				String message = "" ;
				// called when someone is ringing to this phone
/*				
				message = "Incoming: "  ;
				if (incomingNumber.equals("93564246")) {
					message = message + " Fion " ; 
				}
				message =  message + incomingNumber ;
*/				
				 
				
/*				Toast.makeText(ctx, 
						message , 
						Toast.LENGTH_LONG).show();*/
				
				if (incomingNumber != null && incomingNumber.length() > 4) {
					message = search_from_database(ctx , incomingNumber) ;
					 ((CallDetectService) ctx).showDialog("JunkCallOS" , message) ;
				}
				
				break;
			}
		}
		
		public String search_from_database(Context ctx , String lc_telno) {			
			String result = "" , tmp1x ; 
			result = lc_telno ; 
/*			String lc_app_dir = ctx.getApplicationInfo().dataDir ;
			csv_file = lc_app_dir.trim() + "/JunkPhoneList.csv"  ;
*/			
			BufferedReader reader;
			try {
			    // final InputStream file = ctx.getAssets().open("JunkPhoneList.csv");
				// final InputStream file = ctx.getAssets().open("JunkPhoneList.csv");

			    FileInputStream file = new FileInputStream (new File( get_csv_file(ctx) )); 
								
			    reader = new BufferedReader(new InputStreamReader(file));
			    String line = reader.readLine();
			    while(line != null){
			        // Log.d("StackOverflow", line);
			    	if (line.contains(lc_telno)) {
			    		tmp1x = Common.str_extr_betw(line, "<cn>", "</cn>") ; 
			    		result = result + tmp1x ; 
			    		break ; 
			    	}
			        line = reader.readLine();			        
			    }
			    file.close() ; 
			    reader.close() ; 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result ; 
		}
		
	}
	

/*    private void showDialog(Context ctx , String lc_title , String lc_mess) {
        AlertDialog dialog = new AlertDialog.Builder(ctx).setTitle(lc_title)
                .setMessage(lc_mess)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .create();
        dialog.show();
    }
*/	


	
	/**
	 * Broadcast receiver to detect the outgoing calls.
	 */
	public class OutgoingReceiver extends BroadcastReceiver {
	    public OutgoingReceiver() {
	    }

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
	        
	        Toast.makeText(ctx, 
	        		"Outgoing: "+number, 
	        		Toast.LENGTH_LONG).show();
	    }
  
	}

	private Context ctx;
	private TelephonyManager tm;
	private CallStateListener callStateListener;
	
	private OutgoingReceiver outgoingReceiver;

	public CallHelper(Context ctx) {
		this.ctx = ctx;
		
		callStateListener = new CallStateListener();
		outgoingReceiver = new OutgoingReceiver();		
	}
	
	/**
	 * Start calls detection.
	 */
	public void start() {
		tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		
		IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		ctx.registerReceiver(outgoingReceiver, intentFilter);
	}
	
	/**
	 * Stop calls detection.
	 */
	public void stop() {
		tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
		ctx.unregisterReceiver(outgoingReceiver);
	}

}

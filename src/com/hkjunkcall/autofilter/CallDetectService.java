package com.hkjunkcall.autofilter;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;

/**
 * Call detect service. 
 * This service is needed, because MainActivity can lost it's focus,
 * and calls will not be detected.
 * 
 * @author Moskvichev Andrey V.
 *
 */

public class CallDetectService extends Service {
	private CallHelper callHelper;
 
    public CallDetectService() {
    }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		callHelper = new CallHelper(this);
		
		int res = super.onStartCommand(intent, flags, startId);
		callHelper.start();
		return res;
	}
	
    @Override
	public void onDestroy() {
		super.onDestroy();
		
		callHelper.stop();
	}

	@Override
    public IBinder onBind(Intent intent) {
		// not supporting binding
    	return null;
    }
	
	public void showDialog(String title , String message) {
	    Log.i("service","show dialog function");
	    // TextView errmsg = (TextView) layout.findViewById(R.id.errmsg);
	    Log.i("service", "dialog error msg:"+message);  
	    // errmsg.setText(Html.fromHtml(message));
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setTitle(title);
	    builder.setMessage(message); 
	    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int whichButton) {
	            dialog.dismiss();
	        }
	    });
	    AlertDialog alert = builder.create();
	    alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//設定提示框為系統提示框
	    alert.show();
	}

		
}

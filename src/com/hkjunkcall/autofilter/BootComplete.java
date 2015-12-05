package com.hkjunkcall.autofilter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/*public class ServiceController {

}
*/

public class BootComplete   extends BroadcastReceiver {
	 @Override
	 public void onReceive(Context context, Intent intent) {

	  if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
	   Intent serviceIntent = new Intent(context, CallDetectService.class);
	   context.startService(serviceIntent);
	  }
	 }
}

package com.wacom.musicservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class BootBroadcastReceiver extends BroadcastReceiver {
	public BootBroadcastReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences settings = context.getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
	       boolean startOnBoot = settings.getBoolean(MainActivity.PREFS_START_ON_BOOT, false);
	       if(startOnBoot){
	    	   context.startService(new Intent(context, MusicService.class));
	       }
	}
}

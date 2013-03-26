package com.wacom.musicservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BootBroadcastReceiver extends BroadcastReceiver {
	public BootBroadcastReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		boolean startOnBoot = settings.getBoolean(SettingsActivity.KEY_PREF_RUN_ON_STARTUP, false);
		if(startOnBoot){
			context.startService(new Intent(context, MusicService.class));
		}
	}
}

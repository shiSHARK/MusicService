package com.wacom.musicservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;

public class HeadphonesBroadcastReceiver extends BroadcastReceiver {
	
	private static final String TAG = HeadphonesBroadcastReceiver.class.getSimpleName();
	private boolean ignoreFirstMessage = false;
	
	public HeadphonesBroadcastReceiver() {
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if(ignoreFirstMessage){
			ignoreFirstMessage = false;
		}else {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			boolean play = settings.getBoolean(SettingsActivity.KEY_PREF_PLAY_ON_HEADPHONES_CONNECTED, false);
			boolean run = settings.getBoolean(SettingsActivity.KEY_PREF_RUN_ON_STARTUP, false);
			
			Log.d(TAG, "onReceive play " + play);
			Log.d(TAG, "onReceive run " + run);
			
			
			if(intent.getAction().equals(Intent.ACTION_HEADSET_PLUG) && intent.getIntExtra("state", 5)==1 && play){
				// headset plug in event
//				Intent playIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
////				KeyEvent hookEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
//				long time = SystemClock.uptimeMillis();
//				KeyEvent hookEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_NEXT);
////				KeyEvent hookEvent = new KeyEvent(time, time+100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0, 0, 2, 226, 0x8, 101);
//				playIntent.putExtra(Intent.EXTRA_KEY_EVENT, hookEvent);
//				context.sendOrderedBroadcast(playIntent, null);
//				
//				hookEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT);
////				hookEvent = new KeyEvent(time, time+100, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0, 0, 2, 226, 0x8, 101);
//				playIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//				playIntent.putExtra(Intent.EXTRA_KEY_EVENT, hookEvent);
//				context.sendOrderedBroadcast(playIntent, null);
				Intent intentStop;
	    	    intentStop = new Intent("com.android.music.musicservicecommand");
	    	    AudioManager manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
	    	    if(!manager.isMusicActive()){
		    	    intentStop.putExtra("command", "play");
		    	    context.sendBroadcast(intentStop);
	    	    }
			}
			
			
			Log.d(TAG, "onReceive() action: " + intent.getAction());
			if(intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)){
				KeyEvent event = (KeyEvent) intent
						.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
				int keycode = event.getKeyCode();
				int action = event.getAction();
				Log.d(TAG, "onReceive() action: " + action + " | keycode: " + keycode);
				Log.d(TAG, "onReceive() event: " + event);
			}
		}
		
	}

	public boolean isIgnoreFirstMessage() {
		return ignoreFirstMessage;
	}

	public void setIgnoreFirstMessage(boolean ignoreFirstMessage) {
		this.ignoreFirstMessage = ignoreFirstMessage;
	}
}

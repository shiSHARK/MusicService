package com.wacom.musicservice;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private boolean ignoreFirst = false;
	
	private AudioManager mAudioManager;
    private ComponentName mRemoteControlResponder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		receiver = new NoisyAudioStreamReceiver();
		startService(new Intent(this, MusicService.class));
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		startPlayback();
//		mediaFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//		registerReceiver(headsetReceiver, mediaFilter);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
//		stopPlayback();
//		unregisterReceiver(headsetReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private NoisyAudioStreamReceiver receiver;
	
	private class NoisyAudioStreamReceiver extends BroadcastReceiver {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if(ignoreFirst){
	    		ignoreFirst = false;
	    	}else {
	    		int state = intent.getIntExtra("state", 5);
		    	String name = intent.getStringExtra("name");
		    	int microphone = intent.getIntExtra("microphone", 5);
		    	
		    	if(state == 1){
//		    		Intent playDownIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//		    		KeyEvent keyDownEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY);
//		    		playDownIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyDownEvent);
//		    		sendOrderedBroadcast(playDownIntent, null);
//		    		
//		    		Intent playUpIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//		    		KeyEvent keyUpEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY);
//		    		playUpIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyUpEvent);
//		    		sendOrderedBroadcast(playUpIntent, null);
		    		Intent intentStop;
		    	    intentStop = new Intent("com.android.music.musicservicecommand");
		    	    AudioManager manager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		    	    intentStop.putExtra("command", "play");
		    	    sendBroadcast(intentStop);
		    	}
		    	
		    	Log.d(TAG, "onReceive() state: " + state + " | name: " + name + " | microphone: " + microphone);
			}
	    }
	}

	private IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
	private IntentFilter mediaFilter = new IntentFilter(Intent.ACTION_MEDIA_BUTTON);

	private void startPlayback() {
		ignoreFirst = true;
	    registerReceiver(receiver, intentFilter);
	}

	private void stopPlayback() {
	    unregisterReceiver(receiver);
	}
	
	private final BroadcastReceiver headsetReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String intentAction = intent.getAction();
			if (!Intent.ACTION_MEDIA_BUTTON.equals(intentAction))
				return;
			KeyEvent event = (KeyEvent) intent
					.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
			int keycode = event.getKeyCode();
			int action = event.getAction();
			Log.i(TAG, "keycode" + String.valueOf(keycode));
			Log.i(TAG, "action" + String.valueOf(action));
		                 //onKeyDown(keyCode, event)
//			if (keycode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
//					|| keycode == KeyEvent.KEYCODE_HEADSETHOOK)
//					if (action == KeyEvent.ACTION_DOWN)
//						playButton.performClick();
//			if (keycode == KeyEvent.KEYCODE_MEDIA_NEXT)
//					if (action == KeyEvent.ACTION_DOWN)
//						skipButton.performClick();
//			if (keycode == KeyEvent.KEYCODE_MEDIA_PREVIOUS)
//					if (action == KeyEvent.ACTION_DOWN)
//						stopButton.performClick();
//
				}

		};
		
	private boolean isMyServiceRunning() {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
	        if (MusicService.class.getName().equals(service.service.getClassName())) {
	            return true;
	        }
	    }
	    return false;
	}

}

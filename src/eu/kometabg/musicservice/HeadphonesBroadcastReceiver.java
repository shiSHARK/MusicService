package eu.kometabg.musicservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;

public class HeadphonesBroadcastReceiver extends BroadcastReceiver {
	
	private static final String TAG = HeadphonesBroadcastReceiver.class.getSimpleName();
	private static final String GOOGLE_MUSIC_PACKAGE = "com.google.android.music";
	private static final String MUSIC_SERVICE_CMD_PACKAGE = "com.android.music.musicservicecommand";
	private static final String COMMAND = "command";
	private static final String CMD_PALY = "play";
	private static final int DELAY_FOR_GOOGLE_MUSIC = 1500; //in ms
	
	private boolean ignoreFirstMessage = false;
	
	public HeadphonesBroadcastReceiver() {
	}

	@Override
	public void onReceive(final Context context, Intent intent) {
		if(ignoreFirstMessage){
			ignoreFirstMessage = false;
		}else {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
			boolean playMusic = settings.getBoolean(SettingsActivity.KEY_PREF_PLAY_ON_HEADPHONES_CONNECTED, false);
			boolean startGoogleMusic = settings.getBoolean(SettingsActivity.KEY_PREF_START_GOOGLE_MUSIC, false);
			
			if(intent.getAction().equals(Intent.ACTION_HEADSET_PLUG) && intent.getIntExtra("state", 5)==1 && playMusic){
				// headset plug in event
				AudioManager manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
				
//				if(!manager.isMusicActive()){
//					Intent playIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//	//				KeyEvent hookEvent = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_HEADSETHOOK);
//	//				long time = SystemClock.uptimeMillis();
//					int keyCode = KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE;
//					KeyEvent hookEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
//	//				KeyEvent hookEvent = new KeyEvent(time, time+100, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0, 0, 2, 226, 0x8, 101);
//					playIntent.putExtra(Intent.EXTRA_KEY_EVENT, hookEvent);
//					context.sendOrderedBroadcast(playIntent, null);
//					
//					hookEvent = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
//	//				hookEvent = new KeyEvent(time, time+100, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, 0, 0, 2, 226, 0x8, 101);
//					playIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
//					playIntent.putExtra(Intent.EXTRA_KEY_EVENT, hookEvent);
//					context.sendOrderedBroadcast(playIntent, null);
//				}
				
				final Intent playIntent;
	    	    playIntent = new Intent(MUSIC_SERVICE_CMD_PACKAGE);
	    	    playIntent.putExtra(COMMAND, CMD_PALY);
	    	    
	    	    if(startGoogleMusic){
	    	    	Intent googleMusicIntent = context.getPackageManager().getLaunchIntentForPackage(GOOGLE_MUSIC_PACKAGE);
	    	    	context.startActivity(googleMusicIntent);
	    	    	
	    	    	Handler handler = new Handler();
	    	    	handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							context.sendBroadcast(playIntent);
						}
					}, DELAY_FOR_GOOGLE_MUSIC);
	    	    	
	    	    }else if(!manager.isMusicActive()){
		    	    context.sendBroadcast(playIntent);
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

package eu.kometabg.musicservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;

public class MusicService extends Service {
	private static final int TIMEOUT = 3000;
	
	HeadphonesBroadcastReceiver broadcastReceiver = new HeadphonesBroadcastReceiver();
	ComponentName mRemoteControlResponder;
	AudioManager mAudioManager;
	
	public MusicService() {
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
//		filter.addAction(Intent.ACTION_MEDIA_BUTTON);
		//When broadcast is registered, recieves Headset Plug intent
		broadcastReceiver.setIgnoreFirstMessage(true);
		registerReceiver(broadcastReceiver, filter);
		
		//This fixes bug. When android boots first time when 
		//headphones are connected nothing happens
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() { 
			
			@Override
			public void run() {
				broadcastReceiver.setIgnoreFirstMessage(false);
			}
		}, TIMEOUT);
//		mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//		mRemoteControlResponder = new ComponentName(getPackageName(),
//                HeadphonesBroadcastReceiver.class.getName());
//		mAudioManager.registerMediaButtonEventReceiver(
//                mRemoteControlResponder);
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		mAudioManager.unregisterMediaButtonEventReceiver(
//                mRemoteControlResponder);
		unregisterReceiver(broadcastReceiver);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
}

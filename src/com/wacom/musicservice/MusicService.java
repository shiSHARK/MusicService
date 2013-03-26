package com.wacom.musicservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.IBinder;

public class MusicService extends Service {
	
	HeadphonesBroadcastReceiver broadcastReceiver = new HeadphonesBroadcastReceiver();
	ComponentName mRemoteControlResponder;
	AudioManager mAudioManager;
	
	public MusicService() {
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_HEADSET_PLUG);
//		filter.addAction(Intent.ACTION_MEDIA_BUTTON);
		broadcastReceiver.setIgnoreFirstMessage(true);
		registerReceiver(broadcastReceiver, filter);
		
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

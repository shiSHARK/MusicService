package com.wacom.musicservice;

import android.app.Activity;
import android.os.Bundle;


public class SettingsActivity extends Activity {
	
	public static final String KEY_PREF_RUN_ON_STARTUP = "pref_run_on_startup";
	public static final String KEY_PREF_PLAY_ON_HEADPHONES_CONNECTED = "pref_play_on_headphones_coneced";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
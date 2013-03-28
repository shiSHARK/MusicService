package eu.kometabg.musicservice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;


public class SettingsActivity extends Activity {
	
	public static final String KEY_PREF_RUN_ON_STARTUP = "pref_run_on_startup";
	public static final String KEY_PREF_PLAY_ON_HEADPHONES_CONNECTED = "pref_play_on_headphones_coneced";
	public static final String KEY_PREF_START_GOOGLE_MUSIC = "pref_start_google_play_music";
	
	private SettingsFragment settingsFragment;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsFragment = new SettingsFragment();
        
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, settingsFragment)
                .commit();
    }
    
    private OnSharedPreferenceChangeListener preferenceListener = new OnSharedPreferenceChangeListener() {
		
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			if (key.equals(KEY_PREF_PLAY_ON_HEADPHONES_CONNECTED)) {
				updateGoogleMusicPrefStatus();
			}
		}
	};
    
    @Override
    protected void onResume() {
    	super.onResume();
    	settingsFragment.getPreferenceScreen().getSharedPreferences()
        .registerOnSharedPreferenceChangeListener(preferenceListener);
    	updateGoogleMusicPrefStatus();
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	settingsFragment.getPreferenceScreen().getSharedPreferences()
        .unregisterOnSharedPreferenceChangeListener(preferenceListener);
    }
    
    private void updateGoogleMusicPrefStatus(){
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		boolean play = settings.getBoolean(KEY_PREF_PLAY_ON_HEADPHONES_CONNECTED, false);
		Preference googleMusicPref = settingsFragment.findPreference(KEY_PREF_START_GOOGLE_MUSIC);
		googleMusicPref.setEnabled(play);
    }
}
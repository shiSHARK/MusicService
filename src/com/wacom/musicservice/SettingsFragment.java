package com.wacom.musicservice;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragment {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        PreferenceManager.setDefaultValues(getActivity(), R.xml.pref_general, false);
        addPreferencesFromResource(R.xml.pref_general);
    }
}

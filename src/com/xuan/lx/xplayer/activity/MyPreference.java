package com.xuan.lx.xplayer.activity;

import com.xuan.lx.xplayer.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.widget.Toast;

public class MyPreference extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs_fragment);
	}
	
	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		SharedPreferences sf = preference.getSharedPreferences();
		if(preference.getKey().equals("fling")){
			Constants.isListenerSensor = sf.getBoolean("fling", false);
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}

}

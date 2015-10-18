package com.qlfsoft.app.livewallpaper;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class LiveWallpaperSetting  extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getPreferenceManager().setSharedPreferencesName(LiveWallPaper.SHARED_PREFS_NAME);
		addPreferencesFromResource(R.xml.setting);
		getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onSharedPreferenceChanged(
			SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub
		if(key!= null)
		{
			if(key.equals("internal"))
			{
				int internal = Integer.parseInt(sharedPreferences.getString("internal", "15"));
				SharedPreferences sp = this.getSharedPreferences("wallpaper_setting", MODE_PRIVATE);
				SharedPreferences.Editor ed = sp.edit();
				ed.putInt("internal", internal * 1000);
				ed.commit();
				
			}
		}
	}
	
	@Override
	protected void onDestroy(){
		getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

}

package com.qlfsoft.app.livewallpaper.home;

import com.qlfsoft.app.livewallpaper.R;
import com.viewpagerindicator.TabPageIndicator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class HomeActivity extends FragmentActivity {

	private String[] indicators;
	private ViewPager vg_pager;
	private TabPageIndicator tpi_indicator;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		indicators = getResources().getStringArray(R.array.home_indicator_items);
		vg_pager = (ViewPager) findViewById(R.id.activity_home_pager);
		vg_pager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
		tpi_indicator = (TabPageIndicator) findViewById(R.id.activity_home_indicator);
		tpi_indicator.setViewPager(vg_pager);
	}

	class PagerAdapter extends FragmentPagerAdapter{

		public PagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			 return new RecommandFragment();
		}

		@Override
		public int getCount() {
			return indicators.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return indicators[position];
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}

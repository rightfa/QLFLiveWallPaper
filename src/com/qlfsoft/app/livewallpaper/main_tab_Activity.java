package com.qlfsoft.app.livewallpaper;

import com.qlfsoft.app.common.BaseActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class main_tab_Activity extends BaseActivity {

	private TabHost tabhost;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab);
		initView();
	}
	
	private void initView()
	{
		tabhost = (TabHost) findViewById(android.R.id.tabhost);
		tabhost.setup();
		int[] tabImages = new int[]{R.drawable.main_tab_1,R.drawable.main_tab_2,R.drawable.main_tab_3,R.drawable.main_tab_4};
		String[] tabNames = getResources().getStringArray(R.array.main_tabItem);
		String[] tabIds = new String[]{"tab1","tab2","tab3","tab4"};
		Intent intent = new Intent();
		for(int i = 0; i < 4;i++)
		{
			switch(i)
			{
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
			tabhost.addTab(tabhost.newTabSpec(tabIds[i]).setIndicator(getTabItemView(tabImages[i],tabNames[i])).setContent(R.id.tab2));
		}
	}
	
	private View getTabItemView(int imageId, String textName)
	{
		View view =	LayoutInflater.from(this).inflate(R.layout.main_tabitem, null);
		ImageView img = (ImageView) view.findViewById(R.id.main_tabItem_ivIcon);
		TextView tv = (TextView) view.findViewById(R.id.main_tabItem_tvName);
		img.setBackgroundResource(imageId);
		tv.setText(textName);
		return view;
	}
}

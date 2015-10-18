package com.qlfsoft.app.livewallpaper;

import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.appoffers.OffersManager;
import com.baidu.mobads.appoffers.PointsChangeListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class MainActivity
		extends Activity {

	private String[] png_names;
	private TextView tv_name;
	private int cur_selected;
	private boolean IsLocked;
	InterstitialAd interAd;//广告窗口
	private boolean hasShowAd;//是否已经显示过广告，显示过直接退出，没有显示过显示
	@Override
	protected void onCreate(
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//广告
		interAd=new InterstitialAd(this);
		interAd.loadAd();
		hasShowAd = false;
		//积分墙广告监听
		OffersManager.setPointsChangeListener(new PointsChangeListener(){

			@Override
			public void onPointsChanged(int arg0) {
				SharedPreferences sp = getSharedPreferences("wallpaper_setting", MODE_PRIVATE);
				SharedPreferences.Editor editor = sp.edit();
				editor.putBoolean("Locked", false);
				editor.commit();
			}
			
		});
		
		png_names = this.getResources().getStringArray(R.array.png_names);
		Gallery gallery = (Gallery)findViewById(R.id.gallery1);
		tv_name = (TextView)findViewById(R.id.tv_name);
		cur_selected= 0;
		SharedPreferences sp = getSharedPreferences("wallpaper_setting", MODE_PRIVATE);
		IsLocked = sp.getBoolean("Locked", true);
		
		gallery.setAdapter(new ImageAdapter(this));
		gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   
			@Override  
			public void onItemSelected(AdapterView<?> parent, View v,int position, long id) {  
				Log.v("qlfsoft_MainActivity.onItemSelected", String.valueOf(position));
				cur_selected = position;			
				Message msg = new Message();
				msg.what = 0;
				msg.arg1 = position;
				mHandler.sendMessage(msg);
			}   

			@Override  
			public void onNothingSelected(AdapterView<?> arg0) {   
			  //这里不做响应   
			}   
		}); 
		
		//启用动态壁纸按钮
		Button btn_1 = (Button)findViewById(R.id.btn_1);
		btn_1.setOnClickListener( new OnClickListener(){
			public void onClick(View v)
			{
				SharedPreferences sp = getSharedPreferences("wallpaper_setting", MODE_PRIVATE);
				IsLocked = sp.getBoolean("Locked", true);
				if(IsLocked && cur_selected != 0)
				{
					Toast toast = Toast.makeText(MainActivity.this, R.string.lock_msg, Toast.LENGTH_LONG);
					toast.show();
					return;
				}
				SharedPreferences.Editor editor = sp.edit();
				editor.putInt("selected", cur_selected);
				editor.commit();
				Intent intent = new Intent();
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
				startActivity(intent);
			}
		});
		
		//设置按钮
		Button btn_3 = (Button)findViewById(R.id.btn_3);
		btn_3.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, LiveWallpaperSetting.class);
				startActivity(intent);
			}
		});
		
		//一键解锁按钮
		Button btn_2 = (Button)findViewById(R.id.btn_2);
		if(IsLocked)
		{
			btn_2.setText(R.string.btn_value2);
		}else
		{
			btn_2.setText(R.string.more_app);
		}
		btn_2.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Toast toast = Toast.makeText(MainActivity.this, R.string.unlock_msg,Toast.LENGTH_LONG);
				toast.show();
				OffersManager.showOffers(MainActivity.this);
			}
		});
				
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case 0:
				tv_name.setText(png_names[msg.arg1]);
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(
			Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater()
				.inflate(
						R.menu.main,
						menu);
		return true;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if(!hasShowAd)
			{
				interAd.showAd(MainActivity.this);
				Toast toast = Toast.makeText(MainActivity.this, R.string.double_back, Toast.LENGTH_LONG);
				toast.show();
				hasShowAd = true;
			}else{
				finish();
			}
			return true;
		}else
		{
			return super.onKeyDown(keyCode, event);
		}
	}
	
}

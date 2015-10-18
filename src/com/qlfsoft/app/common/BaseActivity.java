package com.qlfsoft.app.common;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class BaseActivity extends Activity {

	private long mExitTime;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			if((System.currentTimeMillis() - mExitTime) > 2000)
			{
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();
			}else
			{
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}

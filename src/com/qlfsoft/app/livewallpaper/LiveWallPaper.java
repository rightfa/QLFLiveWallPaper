package com.qlfsoft.app.livewallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class LiveWallPaper extends WallpaperService {
	
	public static final String SHARED_PREFS_NAME = "com.qlfsoft.app.livewallpaper";
	class PaperEngine extends Engine 
	{
		private int[][] mps;//存放播放的图片的ID
		private int internal;//每两张图片的间隔时间
		private int cur_selected;//当前选中的图片组序号
		private Bitmap image;//当前显示的图片
		private int width;//屏幕宽度
		private int height;//屏幕高度
		private Resources mR;
		private DisplayMetrics dm;
		private int index;//当前组中图片的显示序号
		private boolean mVisible;
		private final Handler mHandler = new Handler();
		
		private final Runnable mRun = new Runnable(){
			public void run(){
				onNext();
			}
		};
		
		private void onNext()
		{
			SharedPreferences sp = getSharedPreferences("wallpaper_setting", MODE_PRIVATE);
			cur_selected = sp.getInt("selected", 0);
			internal = sp.getInt("internal", 1000*15);
			SurfaceHolder holder = getSurfaceHolder();
			Canvas c = holder.lockCanvas();
			Log.v("qlfsoft_LiveWallPaper.onNext", String.valueOf(index));
			//获取下一副图片
			index++;
			if(index >= mps[cur_selected].length)
				index = 0;
			
			image = BitmapFactory.decodeResource(mR, mps[cur_selected][index]);
			image = Bitmap.createScaledBitmap(image, width, height, false);
			c.drawBitmap(image, 0, 0,null);
			holder.unlockCanvasAndPost(c);
			mHandler.removeCallbacks(mRun);
			if(mVisible)
			mHandler.postDelayed(mRun, internal);
		}
		
		public PaperEngine(Resources r)
		{
			init();
			mR = r;
			index = 0;
			mVisible = false;
			WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
			image = BitmapFactory.decodeResource(r, mps[0][0]);
			dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			width = dm.widthPixels;
			height = dm.heightPixels;
		}
		
		public void onCreate(SurfaceHolder surfaceHolder)
		{
			super.onCreate(surfaceHolder);
		}
		
		@Override
		public void onDestroy()
		{
			mHandler.removeCallbacks(mRun);
		}
		
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width,int height)
		{
			mRun.run();
			super.onSurfaceChanged(holder, format, width, height);
		}
		
		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder)
		{
			mVisible = false;
			mHandler.removeCallbacks(mRun);
			super.onSurfaceDestroyed(holder);
		}
		
		@Override
		public void onVisibilityChanged(boolean visible)
		{
			mVisible = visible;
			if(visible)
			{
				mRun.run();
			}else
			{
				mHandler.removeCallbacks(mRun);
			}
			super.onVisibilityChanged(visible);
		}
		
		private void init()
		{
			mps = new int[9][];
			mps[0] = new int[]{R.drawable.a01,R.drawable.a02,R.drawable.a03,R.drawable.a04,R.drawable.a05,R.drawable.a06,R.drawable.a07,
					R.drawable.a08,R.drawable.a09,R.drawable.a10};
			mps[1] = new int[]{R.drawable.b01,R.drawable.b02,R.drawable.b03,R.drawable.b04,R.drawable.b05,R.drawable.b06,R.drawable.b07,
					R.drawable.b08,R.drawable.b09,R.drawable.b10,R.drawable.b11};
			mps[2] = new int[]{R.drawable.c01,R.drawable.c02,R.drawable.c03,R.drawable.c04,R.drawable.c05};
			mps[3] = new int[]{R.drawable.d01,R.drawable.d02,R.drawable.d03,R.drawable.d04,R.drawable.d05,R.drawable.d06,R.drawable.d07,
					R.drawable.d08,R.drawable.d09,R.drawable.d10,R.drawable.d11,R.drawable.d12};
			mps[4] = new int[]{R.drawable.e01,R.drawable.e02,R.drawable.e03,R.drawable.e04,R.drawable.e05,R.drawable.e06,R.drawable.e07,R.drawable.e08};
			mps[5] = new int[]{R.drawable.f01,R.drawable.f02,R.drawable.f03,R.drawable.f04,R.drawable.f05,R.drawable.f06,R.drawable.f07,
					R.drawable.f08,R.drawable.f09,R.drawable.f10,R.drawable.f11,R.drawable.f12,R.drawable.f13,R.drawable.f14,R.drawable.f15};
			mps[6] = new int[]{R.drawable.g01,R.drawable.g02,R.drawable.g03,R.drawable.g04,R.drawable.g05,R.drawable.g06,R.drawable.g07,
					R.drawable.g08,R.drawable.g09,R.drawable.g10,R.drawable.g11};
			mps[7] = new int[]{R.drawable.h01,R.drawable.h02,R.drawable.h03,R.drawable.h04,R.drawable.h05,R.drawable.h06,R.drawable.h07,
					R.drawable.h08,R.drawable.h09,R.drawable.h10,R.drawable.h11,R.drawable.h12};
			mps[8] = new int[]{R.drawable.i01,R.drawable.i02,R.drawable.i03,R.drawable.i04,R.drawable.i05,R.drawable.i06,R.drawable.i07,
					R.drawable.i08,R.drawable.i09,R.drawable.i10,R.drawable.i11};
		}

	}

	
	@Override
	public Engine onCreateEngine() {
		// TODO Auto-generated method stub
		return new PaperEngine(getResources());
	}

}

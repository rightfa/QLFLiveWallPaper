package com.qlfsoft.app.livewallpaper.home;

import java.io.IOException;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.qlfsoft.app.livewallpaper.R;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ShowImageActivity extends Activity implements OnClickListener {

	private ImageView imgshow_iv;
	private Button imgshow_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.imageshow);
		Intent intent = this.getIntent();
		String filePath = intent.getStringExtra(getString(R.string.IMAGEURL));
		imgshow_iv = (ImageView) findViewById(R.id.imgshow_iv);
		imgshow_btn = (Button) findViewById(R.id.imgshow_btn);
		
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub_long)
		.showImageForEmptyUri(R.drawable.ic_empty_long)
		.showImageOnFail(R.drawable.ic_error_long)
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.build();
		ImageLoader.getInstance().displayImage(filePath, imgshow_iv, options, null,null);
		imgshow_iv.setOnClickListener(this);
		
		imgshow_btn.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.imgshow_iv:
			finish();
			break;
		case R.id.imgshow_btn:
			SetWallPaper();
			break;
		}
		
	}

	private void SetWallPaper()
	{
		WallpaperManager wpm = WallpaperManager.getInstance(this);
		try{
			Drawable img = imgshow_iv.getDrawable();
			Bitmap bmp = drawable2Bitmap(img);
			wpm.setBitmap(bmp);
			Toast.makeText(ShowImageActivity.this, "±⁄÷Ω…Ë÷√≥…π¶£°", Toast.LENGTH_SHORT).show();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	Bitmap drawable2Bitmap(Drawable drawable) {  
	       if (drawable instanceof BitmapDrawable) {  
		            return ((BitmapDrawable) drawable).getBitmap();  
		        } else if (drawable instanceof NinePatchDrawable) {  
		            Bitmap bitmap = Bitmap  
		                    .createBitmap(  
		                            drawable.getIntrinsicWidth(),  
		                            drawable.getIntrinsicHeight(),  
		                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888  
		                                    : Bitmap.Config.RGB_565);  
		            Canvas canvas = new Canvas(bitmap);  
		            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),  
		                    drawable.getIntrinsicHeight());  
		            drawable.draw(canvas);  
		            return bitmap;  
		        } else {  
		            return null;  
		        }  
    }  

}

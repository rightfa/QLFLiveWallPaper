package com.qlfsoft.app.livewallpaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;



public class ImageAdapter extends BaseAdapter {

	//ͼƬ
	private Integer[] mps = {
			R.drawable.a01, R.drawable.b01, R.drawable.c01,R.drawable.d01,
			R.drawable.e01, R.drawable.f01, R.drawable.g01, R.drawable.h01,
			R.drawable.i01
	};
	
	private Context mContext;
	
	public ImageAdapter(Context context)
	{
		mContext = context;
	}
	
	public int getCount(){
		return mps.length;
	}
	
	public Object getItem(int position)
	{
		return position;
	}
	
	public long getItemId(int position)
	{
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ImageView image = new ImageView(mContext);
		image.setImageResource(mps[position]);
		image.setAdjustViewBounds(true);
		image.setLayoutParams(new Gallery.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		return image;
	}
}

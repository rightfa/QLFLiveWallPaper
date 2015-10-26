package com.qlfsoft.app.livewallpaper.home;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.qlfsoft.app.livewallpaper.R;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.util.HttpUtils;
import cn.trinea.android.common.view.DropDownListView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecommandFragment extends Fragment {

	protected static final int RECEIVEXML = 0;
	private DropDownListView recommand_listview;
	private List<ImageEntity> images;
	public static String XMLURL = "http://115.159.26.138/photo/image/images/photoXML.xml";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		images = new ArrayList<ImageEntity>();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.recommand_fragment, null);
		recommand_listview = (DropDownListView) view.findViewById(R.id.recommand_listview);
		new Thread(new HttpImageThread()).start();
		return view;
	}
	
	class HttpImageThread implements Runnable
	{

		@Override
		public void run() {
			String xmlBody = HttpUtils.httpGetString(XMLURL);
			images = parse(xmlBody);
			Message msg = new Message();
			msg.what = RECEIVEXML;
			myHandler.sendMessage(msg);
		}
		
		/**
		 * ½âÎöxml
		 * @param body
		 * @return
		 */
		private List<ImageEntity> parse(String body)
		{
			List<ImageEntity> entitys = null;
			ImageEntity entity = null;
			XmlPullParser parser = Xml.newPullParser();
			ByteArrayInputStream in;
			try {
				in = new ByteArrayInputStream(body.getBytes("utf-8"));
				parser.setInput(in, "utf-8");
				int eventType = parser.getEventType();
				while(eventType != XmlPullParser.END_DOCUMENT)
				{
					switch(eventType)
					{
					case XmlPullParser.START_DOCUMENT:
						entitys = new ArrayList<ImageEntity>();
						break;
					case XmlPullParser.START_TAG:
						if(parser.getName().equals("image"))
						{
							entity = new ImageEntity();
						}else if(parser.getName().equals("path"))
						{
							entity.setImagePath(parser.getText());
						}else if(parser.getName().equals("class"))
						{
							entity.setImageClass(parser.getText());
						}else if(parser.getName().equals("recommand"))
						{
							boolean isRecommand = (Integer.parseInt(parser.getText()) == 1)? true: false;
							entity.setRecommand(isRecommand);
						}
							break;
					case XmlPullParser.END_TAG:
						if(parser.getName().equals("image"))
						{
							entitys.add(entity);
							entity = null;
						}
						break;
					}
					eventType = parser.next();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return entitys;
		}
	}
	
	private Handler myHandler =new Handler(){
		public void handleMessage(Message msg)
		{
			switch(msg.what)
			{
			case RECEIVEXML:
				break;
			}
		}
	};
}

package com.traveltriangle.traveltriangleblog;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.traveltriangle.traveltriangleblog.listener.XMLRequestListener;
import com.traveltriangle.traveltriangleblog.model.Item;
import com.traveltriangle.traveltriangleblog.utilities.XmlController;

public class MainActivity extends ActionBarActivity implements OnClickListener, XMLRequestListener {

	private ViewPager mViewPager;
	private ImageView prevImageView, nextImageView;
	
	private ArrayList<Item> items = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		prevImageView = (ImageView) findViewById(R.id.prev_icon);
		nextImageView = (ImageView) findViewById(R.id.next_icon);
		
		prevImageView.setOnClickListener(this);
		nextImageView.setOnClickListener(this);
		
		XmlController.setRequestListener(this);
		new XmlController().execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void preRequest() {
	}

	@Override
	public Object inRequest(Object request) {
		InputStream inputStream = (InputStream) request;
		Log.d("TAG", "" + (inputStream == null));
		try {
			XmlPullParser mXmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
			mXmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			mXmlPullParser.setInput(inputStream, null);
			
			int event = mXmlPullParser.getEventType();
			while(event != XmlPullParser.END_DOCUMENT) {
				Log.d("TAG", mXmlPullParser.getName());
			}
			
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void postRequest(Object response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

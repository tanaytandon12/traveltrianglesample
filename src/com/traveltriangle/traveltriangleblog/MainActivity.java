package com.traveltriangle.traveltriangleblog;

import java.io.IOException;
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
import com.traveltriangle.traveltriangleblog.utilities.Config;
import com.traveltriangle.traveltriangleblog.utilities.XmlController;

public class MainActivity extends ActionBarActivity implements OnClickListener,
		XMLRequestListener {

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

		Log.d("TAG", "Highway to Hell");
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
		Log.d("TAG", "pre");
	}

	@Override
	public Object inRequest(Object request) {
		Log.d("TAG", "uin");
		InputStream inputStream = (InputStream) request;
		try {
			XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
			xppf.setNamespaceAware(true);
			XmlPullParser mXmlPullParser = xppf.newPullParser();
			mXmlPullParser.setInput(inputStream, null);

			int event = mXmlPullParser.getEventType();
			Item item = null;
			boolean flag = false;
			String eventName = "";
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					eventName = mXmlPullParser.getName();
					if (eventName.equalsIgnoreCase(Config.ITEM)) {
						item = new Item();
						flag = true;
					}
					if (flag) {
						String text = mXmlPullParser.getText();
						switch (eventName) {
						case Config.DATE:
							item.setDate(text);
							break;
						case Config.DESC:
							item.setDescription(text);
						case Config.TITLE:
							item.setTitle(text);
							break;
						case Config.LINK:
							item.setLink(text);
						default:
							break;
						}
					}
					break;

				case XmlPullParser.END_TAG:
					
					if (eventName.equalsIgnoreCase(Config.ITEM)) {
						Log.d("TAG", item.getDescription() + " \n" + item.getTitle());
						items.add(item);
						flag = false;
					}
					break;

				default:
					break; 
				}
				event = mXmlPullParser.next();
			}

		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
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

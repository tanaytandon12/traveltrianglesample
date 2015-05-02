package com.traveltriangle.traveltriangleblog;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
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
		try {
			XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
			xppf.setNamespaceAware(true);
			XmlPullParser mXmlPullParser = xppf.newPullParser();
			mXmlPullParser.setInput(inputStream, null);

			int event = mXmlPullParser.getEventType();
			Item item = null;
			String eventName = "";
			boolean flag = false;
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_TAG:
					eventName = mXmlPullParser.getName();
					if (eventName.equals(Config.ITEM)) {
						item = new Item();
						flag = true;
					}
					break;

				case XmlPullParser.TEXT:
					if (flag) {
						String text = mXmlPullParser.getText();
						switch (eventName) {

						case Config.TITLE:
							if (item.getTitle() == null) {
								item.setTitle(text);
							}
							break;

						case Config.TIME:
							if (item.getTime() == null) {
								item.setTime(text);
							}
							break;

						case Config.DESC:
							if (item.getDescription() == null) {
								item.setDescription(text);
							}
							break;

						case Config.LINK:
							if (item.getLink() == null) {
								item.setLink(text);
							}

						default:
							break;
						}
					}
					break;

				case XmlPullParser.END_TAG:
					eventName = mXmlPullParser.getName();
					if (eventName.equals(Config.ITEM)) {
						items.add(item);
						item = new Item();
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

		ViewPagerAdapter vpa = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(vpa);
		if (items.size() > 1) {
			nextImageView.setVisibility(View.VISIBLE);
		}
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int pos) {
				if(pos == 0 && items.size() > 1) {
					nextImageView.setVisibility(View.VISIBLE);
					prevImageView.setVisibility(View.GONE);
				} else if (pos == items.size() - 1 && pos != 0) {
					prevImageView.setVisibility(View.VISIBLE);
					nextImageView.setVisibility(View.GONE);
				} else {
					nextImageView.setVisibility(View.VISIBLE);
					prevImageView.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		int currentPosition;
		switch (id) {
		case R.id.next_icon:
			currentPosition = mViewPager.getCurrentItem();
			mViewPager.setCurrentItem(currentPosition + 1, true);
			if (currentPosition == 0) {
				prevImageView.setVisibility(View.VISIBLE);
			} else if ((currentPosition + 1) == items.size() - 1) {
				nextImageView.setVisibility(View.GONE);
			}
			break;
		case R.id.prev_icon:
			currentPosition = mViewPager.getCurrentItem();
			mViewPager.setCurrentItem(currentPosition - 1, true);
			if (currentPosition == items.size() - 1) {
				nextImageView.setVisibility(View.VISIBLE);
			} else if (currentPosition == 1) {
				prevImageView.setVisibility(View.GONE);
			}
			break;
		}

	}

	private class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			BlogFragment bf = new BlogFragment();
			bf.setCurrentItem(items.get(pos));
			return bf;
		}

		@Override
		public int getCount() {
			return items.size();
		}

	}
}

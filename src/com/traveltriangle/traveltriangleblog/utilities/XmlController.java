package com.traveltriangle.traveltriangleblog.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import com.traveltriangle.traveltriangleblog.listener.XMLRequestListener;

public class XmlController extends AsyncTask<Void, Void, Object> {

	private static XMLRequestListener mRequestListener;

	public static void setRequestListener(XMLRequestListener mListener) {
		mRequestListener = mListener;
	}

	@Override
	protected void onPreExecute() {
		mRequestListener.preRequest();
	}

	@Override
	protected Object doInBackground(Void... params) {
		HttpURLConnection httpUrlConn = null;
		Object response = null;
		try {
			URL httpUrl = new URL(Config.BASE_URL);
			httpUrlConn = (HttpURLConnection) httpUrl.openConnection();
			httpUrlConn.connect();
			httpUrlConn.setConnectTimeout(5000);
			InputStream inputStream = httpUrlConn.getInputStream();
			Log.d("TAG", "going to respond" + httpUrlConn.getContentType());
			response = mRequestListener.inRequest(inputStream);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			httpUrlConn.disconnect();
		}
		return response;
	}

	@Override
	protected void onPostExecute(Object result) {
		mRequestListener.postRequest(result);
	}
}

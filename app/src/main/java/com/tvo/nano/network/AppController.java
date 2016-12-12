package com.tvo.nano.network;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private static AppController mInstance;
	private static Context mContext;

	@Override
	public void onCreate() {
		super.onCreate();

		mInstance = this;
		mContext = this;
	}

	public static Context getAppContext() {
		return mContext;
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}
		return mRequestQueue;
	}

	// add request with tag
	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		getRequestQueue().getCache().clear(); // 10/31/2014 disable save cache

		getRequestQueue().add(req);
	}

	// add request without tag and using defaut tag
	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	/**
	 * this method to get entry from link to check wheathe have cache or not
	 * 
	 * @param link
	 * @return Entry
	 */
	public Entry getEntryCacheWithVolley(String link) {
		Cache cache = this.getRequestQueue().getCache();
		Entry entry = cache.get(link);
		return entry;
	}

}

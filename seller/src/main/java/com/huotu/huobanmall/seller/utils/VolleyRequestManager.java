package com.huotu.huobanmall.seller.utils;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Manager for the queue
 * 
 * @author Trey Robinson
 *
 */
public class VolleyRequestManager {
	
	/**
	 * the queue :-)
	 */
	private static RequestQueue mRequestQueue;

	private static ImageLoader imageLoader = null;

	/**
	 * Nothing to see here.
	 */
	private VolleyRequestManager() {
	 // no instances
	} 

	/**
	 * @param context
	 * 			application context
	 */
	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(mRequestQueue, LruImageCache.instance() );
	}

	/**
	 * @return
	 * 		instance of the queue
	 * @throws
	 * 		IllegalStatException if init has not yet been called
	 */
	public static RequestQueue getRequestQueue() {
	    if (mRequestQueue != null) {
	        return mRequestQueue;
	    } else {
	        throw new IllegalStateException("Not initialized");
	    }
	}

	public static ImageLoader getImageLoader(Context context) {
		if (null == imageLoader)
		{
			init(context);
		}
		return imageLoader;
	}
}

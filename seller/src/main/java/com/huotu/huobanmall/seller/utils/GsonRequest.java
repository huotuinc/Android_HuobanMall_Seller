package com.huotu.huobanmall.seller.utils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.methods.HttpHead;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for Volley requests to facilitate parsing of json responses. 
 * 
 * @param <T>
 */
public class GsonRequest<T> extends Request<T> {

	/**
	 * Gson parser 
	 */
	private final Gson mGson;
	
	/**
	 * Class type for the response
	 */
	private final Class<T> mClass;

	/**
	 *
	 */
	private final TypeToken<T> mTypeToken;//提供和解析自定义的复杂JSON数据支持,这点与Jackson使用TypeReference不同，但原理是大同小异的
	
	
	/**
	 * Callback for response delivery 
	 */
	private final Listener<T> mListener;

	private static Map<String,String > mHeader = new HashMap<>();
	
	/**
	 * @param method
	 * 		Request type.. Method.GET etc
	 * @param url
	 * 		path for the requests
	 * @param objectClass
	 * 		expected class type for the response. Used by gson for serialization.
	 * @param listener
	 * 		handler for the response
	 * @param errorListener
	 * 		handler for errors
	 */
	public GsonRequest(int method
			, String url
			, Class<T> objectClass
			, Map<String,String> headers
			, Listener<T> listener
			, ErrorListener errorListener) {
		
		super(method, url, errorListener);
		this.mClass = objectClass;
		this.mListener = listener;
		mGson = new Gson();
		if( null != headers ) {
			mHeader.putAll(headers);
		}
		mTypeToken=null;
	}

	public GsonRequest(int method
					   , String url
					   , TypeToken<T> typeToken
					   , Map<String,String> headers
					   , Listener<T> listener
					   , ErrorListener errorListener
					   ){
		super(method,url, errorListener);
		this.mTypeToken= typeToken;
		this.mListener = listener;
		this.mGson = new Gson();
		if( null != headers){
			mHeader.putAll(headers);
		}
		this.mClass=null;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			if( mTypeToken==null) {
				return Response.success(mGson.fromJson(json, mClass),
						HttpHeaderParser.parseCacheHeaders(response));
			}else{
				return  (Response<T>)Response.success( mGson.fromJson( json , mTypeToken.getType())
						,HttpHeaderParser.parseCacheHeaders( response));
			}
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		//return super.getHeaders();
		return mHeader;
	}
}

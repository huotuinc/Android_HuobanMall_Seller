package com.huotu.huobanmall.seller.common;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;



public class BaseService {
	public static ConnectType apnType = ConnectType.CMNET;
	protected final static String ERROR_NET = "连接服务器失败,请重试...";
	protected final static String ERROR_DATA = "数据解析出错,请重试...";
	protected final static String ERROR_NULL = "没有查询到你所需要的结果...";
	protected static final String ERROR_SAFE_KEY = "登录已超时,请重新尝试...";

	public enum ConnectType {
		CMWAP, CMNET, WIFI, UNIWAP, Unknow
	}

	public BaseService() {
	}

//	private DefaultHttpClient httpConnection() {
//		HttpParams httpParameters = new BasicHttpParams();
//		int timeoutConnection = 10000;// 连接超时
//		HttpConnectionParams.setConnectionTimeout(httpParameters,timeoutConnection);
//		int timeoutSocket = 15000;// 数据传输超时
//		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//
//		DefaultHttpClient client = new DefaultHttpClient(httpParameters);
//		chooseAPNType(client);
//		return client;
//	}

//	protected void chooseAPNType(DefaultHttpClient client) {
//		if (apnType == ConnectType.CMWAP || apnType == ConnectType.UNIWAP) {
//			HttpHost proxy = new HttpHost("10.0.0.172", 80);
//			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//					proxy);
//		}
//	}


	protected HttpURLConnection openMyConnection(String cUrl)throws IOException {
		HttpURLConnection hc = null;
		if (apnType == ConnectType.CMWAP || apnType == ConnectType.UNIWAP) {
			hc = requestHttpByWap(cUrl);
		} else {
			URL url = new URL(cUrl);
			hc = (HttpURLConnection) url.openConnection();
		}
		return hc;
	}

	public HttpURLConnection requestHttpByWap(String st) throws IOException {
		String urlt = st;
		String pUrl = "http://10.0.0.172";
		urlt = urlt.replace("http://", "");
		pUrl = pUrl + urlt.substring(urlt.indexOf("/"));
		urlt = urlt.substring(0, urlt.indexOf("/"));

		URL url = new URL(pUrl);
		HttpURLConnection hc = (HttpURLConnection) url.openConnection();

		hc.setRequestProperty("X-Online-Host", urlt);
		hc.setDoInput(true);

		return hc;
	}

}

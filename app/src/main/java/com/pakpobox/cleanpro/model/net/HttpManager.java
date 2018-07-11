package com.pakpobox.cleanpro.model.net;

import com.pakpobox.cleanpro.base.MyApplication;
import com.pakpobox.logger.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp网络请求管理类
 * User:Sean.Wei
 * Date:2018/3/7
 * Time:10:52
 */

public class HttpManager {
	private final String TAG = getClass().getSimpleName();
	public static final int ERR_TYPE_DATA_FORMATE = -3;//数据格式错误
	public static final int ERR_TYPE_EMPTY = -2;//数据空错误
	public static final int ERR_TYPE_CONNECT = -1;//连接错误
	public static final int ERR_TYPE_IO = 0;//io流错误
	public static final int CONNECT_TIMEOUT_DEF = 30 * 1000;
	public static final int READ_TIMEOUT_DEF = 30 * 1000;
	public static final int WRITE_TIMEOUT_DEF = 30 * 1000;

	private static final int CACHE_SIZE = 2 * 1024;

	private OkHttpClient mClient = null;
	private ConcurrentHashMap<String, Call> mHttpCallMap = null;

	public HttpManager(){
		this(CONNECT_TIMEOUT_DEF, READ_TIMEOUT_DEF, WRITE_TIMEOUT_DEF);
	}

	public HttpManager(int connectTimeout, int readTimeout, int writeTimeout) {
		SSLSocketFactory sslSocketFactory = null;
		try {
//			sslSocketFactory = SSLUtil.getSSLSocketFactory(MyApplication.getContext().getAssets().open("sslChina.cer"), MyApplication.getContext().getAssets().open("sslSingapore.cer"));
			sslSocketFactory = SSLUtil.getSSLSocketFactory(MyApplication.getContext().getAssets().open("sslChina.cer")
					, MyApplication.getContext().getAssets().open("sslSingapore.cer")
					, MyApplication.getContext().getAssets().open("storhub_com.crt")
					, MyApplication.getContext().getAssets().open("storhub_com_new.crt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mClient = new OkHttpClient.Builder()
//				.sslSocketFactory(MySSLSocketFactory.getSocketFactory(context))
//				.sslSocketFactory(sslSocketFactory)
				.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
				.readTimeout(readTimeout, TimeUnit.MILLISECONDS)
				.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
				.build();

		mHttpCallMap = new ConcurrentHashMap<>();
		Logger.t(TAG).i("Init HttpClient: ConnectTimeout:" + connectTimeout + "; ReadTimeout:" + readTimeout + "; WriteTimeout" + writeTimeout);
	}

	/**
	 * 终止HTTP请求
	 *
	 * @param url HTTP URL
	 */
	public void stopHttpRequest(String url) {
		Call httpCall = mHttpCallMap.remove(url);
		if (null != httpCall) {
			httpCall.cancel();
			Logger.t(TAG).w("Shutdown a HTTP request: " + url);
		}
	}

	/**
	 * 清除所有HTTP请求
	 */
	public void clearAllRequest(){
		for(Call httpCall : mHttpCallMap.values()){
			if (null != httpCall) {
				httpCall.cancel();
			}
		}
		mHttpCallMap.clear();
		Logger.t(TAG).w("Clear all HTTP request: size:" + mHttpCallMap.size());
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// GET
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * HTTP GET 异步获取数据
	 *
	 * @param url 网络地址
	 * @param headerValues 表头信息
	 * @param responseCallback 回调
	 */
	public void asyncGetDataByHttp(String url, HashMap<String, String> headerValues, HttpResponseCallback responseCallback) {
		Logger.t(TAG).i("HTTP-request(GET): " + url + "\nHeader:" + headerValues);
		Call call = newHttpCall(url, headerValues, null);
		call.enqueue(new ResponseCallback(url, responseCallback));
	}

	/**
	 * HTTP GET 同步获取数据
	 *
	 * @param url 网络地址
	 * @param headerValues 表头信息
	 */
	public byte[] syncGetDataByHttp(String url, HashMap<String, String> headerValues) {
		Logger.t(TAG).i("HTTP-request(GET): " + url + "\nHeader:" + headerValues);
		Call call = newHttpCall(url, headerValues, null);
		return responseData(url, call);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Post
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 异步上传文字数据
	 * @param url 网络地址
	 * @param postString 要上传的字符串
	 * @param responseCallback 回调
	 */
	public void asyncPostStringByHttp(String url, HashMap<String, String> headerValues, String postString, HttpResponseCallback responseCallback) {
		Logger.t(TAG).i("HTTP-request(POST): " + url + "\nupload-data: " + postString + "\nHeader:" + headerValues);
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postString);
		Call call = newHttpCall(url, headerValues, requestBody);
		call.enqueue(new ResponseCallback(url, responseCallback));
	}

	/**
	 * 异步上传文件
	 *
	 * @param url 网络地址
	 * @param file 文件对象
	 * @param responseCallback 回调
	 */
	public void asyncPostFileByHttp(String url, HashMap<String, String> headerValues, File file, HttpResponseCallback responseCallback) {
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
		Call call = newHttpCall(url, headerValues, requestBody);
		call.enqueue(new ResponseCallback(url, responseCallback));
	}

	/**
	 * HTTP POST 异步上传字节类型数据，该方法在回调中返回POST进度
	 *
	 * @param url 网络地址
	 * @param data 要上传的数据
	 * @param responseCallback 回调
	 */
	public void asyncPostBytesByHttp(String url, HashMap<String, String> headerValues, byte[] data, HttpResponseCallback responseCallback) {
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), data);
		Call call = newHttpCall(url, headerValues, requestBody);
		call.enqueue(new ResponseCallback(url, responseCallback));
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 私有方法
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//新建一个请求Call
	private Call newHttpCall(String url, HashMap<String, String> headerValues, RequestBody requestBody) {
		Request request = createRequest(url, headerValues, requestBody);
		Call call = mClient.newCall(request);
		mHttpCallMap.put(url, call);
		return call;
	}

	// 创建HTTP请求对象
	private Request createRequest(String url, HashMap<String, String> headerValues, RequestBody requestBody) {
		Request.Builder requestBuilder = new Request.Builder().url(url);

		if (null != requestBody) {
			requestBuilder.post(requestBody);
		}

		// 设置表头
		if (null != headerValues && headerValues.size() > 0) {
			Iterator<Entry<String, String>> headerIterator = headerValues.entrySet()
					.iterator();
			Entry<String, String> headerEntry = null;
			while (headerIterator.hasNext()) {
				headerEntry = headerIterator.next();
				if (null != headerEntry)
					requestBuilder.addHeader(headerEntry.getKey(), headerEntry.getValue());
			}
		}
		return requestBuilder.build();
	}

	// HTTP同步请求响应数据
	private byte[] responseData(String url, Call call) {
		byte[] responseData = null;
		try {
			Response response = call.execute();
			if (null != response) {
				if (response.isSuccessful()) {
					InputStream inputStream = response.body().byteStream();
					byte[] dataCache = new byte[CACHE_SIZE];

					ByteArrayOutputStream mByteBuffer = new ByteArrayOutputStream();
					int len;
					while ((len = inputStream.read(dataCache)) > 0) {
						mByteBuffer.write(dataCache, 0, len);
					}
					responseData = mByteBuffer.toByteArray();
					Logger.t(TAG).i("Response success: " + url + "\ndata:" + new String(responseData));

					inputStream.close();
					mByteBuffer.close();
				} else {
					Logger.t(TAG).e("Response error: " + url + "\ncode：" + response.code() + "\nmessage：" + response.message());
				}
			} else {
				Logger.t(TAG).e("Response error: " + url + "\nmessage：response is null");
			}
		} catch (IOException e) {
			Logger.t(TAG).e(e,"Response error: " + url + "\nmessage：IO error");
		}finally {
			mHttpCallMap.remove(url);
		}
		return responseData;
	}

	// HTTP异步请求响应回调
	private class ResponseCallback implements Callback {
		private String url = null;
		private HttpResponseCallback callback;


		public ResponseCallback(String url, HttpResponseCallback callback) {
			this.url = url;
			this.callback = callback;
		}

		@Override
		public void onFailure(Call call, IOException e) {
			mHttpCallMap.remove(url);
			Logger.t(TAG).e(e, "Request fail: url:" + url + "\n");
			if(null != callback)
				callback.requestFail(url, ERR_TYPE_CONNECT, "Request Error：" + e.getMessage());
		}

		@Override
		public void onResponse(Call call, Response response) throws IOException {
			InputStream inputStream = null;
			ByteArrayOutputStream mByteBuffer = null;
			try {
				if (response.isSuccessful()) {
					inputStream = response.body().byteStream();
					byte[] dataCache = new byte[CACHE_SIZE];

					mByteBuffer = new ByteArrayOutputStream();
					int len;
					while ((len = inputStream.read(dataCache)) > 0) {
						mByteBuffer.write(dataCache, 0, len);
					}

					byte[] data = mByteBuffer.toByteArray();
					Logger.t(TAG).i("Response success: " + url + "\ndata:" + new String(data));

					if(null != callback) {
						if (null == data || data.length <= 0) {
							callback.requestFail(url, ERR_TYPE_EMPTY, "Response data is empty");
						}else{
							callback.requestSuccess(url, data);
						}
					}
				} else {
					Logger.t(TAG).e("Response error: " + url + "\ncode：" + response.code() + "\nmessage：" + response.message());
					if(null != callback)
						callback.requestFail(url, response.code(), response.message());
				}
			} catch (IOException e) {
				Logger.t(TAG).e(e, "HTTP response IO error");
				if(null != callback)
					callback.requestFail(url, ERR_TYPE_IO, e.getMessage());
			} finally {
				mHttpCallMap.remove(url);
				if (null != inputStream) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
						Logger.t(TAG).e(e, "Stream close IO error");
					}
				}
				if(null != mByteBuffer) {
					try {
						mByteBuffer.close();
					}
					catch (IOException e) {
						e.printStackTrace();
						Logger.t(TAG).e(e, "Stream close IO error");
					}
				}
			}
		}
	}

	/**
	 * Http响应回调接口
	 */
	public interface HttpResponseCallback {
		/**
		 * HTTP Request成功回调
		 *
		 * @param url HTTP URL
		 * @param data 响应数据
		 */
		void requestSuccess(String url, byte[] data);

		/**
		 * HTTP Request失败回调
		 *
		 * @param url HTTP URL
		 * @param httpCode HTTP响应码
		 * @param errMsg 错误信息
		 */
		void requestFail(String url, int httpCode, String errMsg);
	}
}
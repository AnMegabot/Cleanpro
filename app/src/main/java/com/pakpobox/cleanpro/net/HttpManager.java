package com.pakpobox.cleanpro.net;

import com.pakpobox.cleanpro.application.MyApplication;
import com.pakpobox.cleanpro.net.callback.INetCallback;
import com.pakpobox.cleanpro.net.ssl.SSLUtil;
import com.pakpobox.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

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
import okhttp3.MultipartBody;
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
	public static final int CONNECT_TIMEOUT_DEF = 60 * 1000;
	public static final int READ_TIMEOUT_DEF = 60 * 1000;
	public static final int WRITE_TIMEOUT_DEF = 60 * 1000;

	private static final int CACHE_SIZE = 2 * 1024;

	private OkHttpClient mClient = null;
	private ConcurrentHashMap<Call, ResponseCallback> mHttpCallMap = null;

	public HttpManager(){
		SSLSocketFactory sslSocketFactory = null;
		try {
			sslSocketFactory = SSLUtil.getSSLSocketFactory(MyApplication.getContext().getAssets().open("sslChina.cer")
					, MyApplication.getContext().getAssets().open("sslSingapore.cer")
					, MyApplication.getContext().getAssets().open("storhub_com.crt")
					, MyApplication.getContext().getAssets().open("storhub_com_new.crt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mClient = new OkHttpClient.Builder()
//				.sslSocketFactory(MySSLSocketFactory.getSocketFactory(context))
				.sslSocketFactory(sslSocketFactory)
				.connectTimeout(CONNECT_TIMEOUT_DEF, TimeUnit.MILLISECONDS)
				.readTimeout(READ_TIMEOUT_DEF, TimeUnit.MILLISECONDS)
				.writeTimeout(WRITE_TIMEOUT_DEF, TimeUnit.MILLISECONDS)
				.build();

		mHttpCallMap = new ConcurrentHashMap<>();
		Logger.t(TAG).i("Init HttpClient: ConnectTimeout:" + CONNECT_TIMEOUT_DEF + "; ReadTimeout:" + READ_TIMEOUT_DEF + "; WriteTimeout" + WRITE_TIMEOUT_DEF);
	}

	/**
	 * 终止HTTP请求
	 * @param call 请求
	 */
	public void stopHttpRequest(Call call) {
		if (null != call) {
			call.cancel();
			ResponseCallback callback = mHttpCallMap.get(call);
			callback.removeCallback();
			Logger.t(TAG).w("Shutdown a HTTP request: " + call.request().url());
			mHttpCallMap.remove(call);
		}
	}

	/**
	 * 清除所有HTTP请求
	 */
	public void clearAllRequest(){
		Logger.t(TAG).w("Clear all HTTP request: size:" + mHttpCallMap.size());
		Iterator<ConcurrentHashMap.Entry<Call, ResponseCallback>> entries = mHttpCallMap.entrySet().iterator();
		while (entries.hasNext()) {
			ConcurrentHashMap.Entry<Call, ResponseCallback> entry = entries.next();
			if (null != entry.getKey()) {
				entry.getKey().cancel();
			}
			if (null != entry.getValue()) {
				entry.getValue().removeCallback();
			}
		}
		mHttpCallMap.clear();
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// GET
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * HTTP GET 异步获取数据
	 *
	 * @param url 网络地址
	 * @param headerValues 表头信息
	 * @param netCallback 回调
	 */
	public void asyncGetDataByHttp(String url, HashMap<String, String> headerValues, INetCallback netCallback) {
		Logger.t(TAG).i("HTTP-request(GET): " + url + "\nHeader:" + headerValues);
		Call call = newHttpCall(url, headerValues, null);
		ResponseCallback callback = new ResponseCallback(url, netCallback);
		mHttpCallMap.put(call, callback);
		call.enqueue(callback);
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
		mHttpCallMap.put(call, null);
		return responseData(url, call);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Post
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 异步上传文字数据
	 * @param url 网络地址
	 * @param postString 要上传的字符串
	 * @param netCallback 回调
	 */
	public void asyncPostStringByHttp(String url, HashMap<String, String> headerValues, String postString, INetCallback netCallback) {
		Logger.t(TAG).i("HTTP-request(POST): " + url + "\nupload-data: " + postString + "\nHeader:" + headerValues);
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), postString);
		Call call = newHttpCall(url, headerValues, requestBody);
		ResponseCallback callback = new ResponseCallback(url, netCallback);
		mHttpCallMap.put(call, callback);
		call.enqueue(callback);
	}

	/**
	 * 异步上传图片文件
	 *
	 * @param url 网络地址
	 * @param filePath 文件路径
	 * @param netCallback 回调
	 */
	public void asyncPostFileByHttp(String url, HashMap<String, String> headerValues, String filePath, INetCallback netCallback) {
		Logger.t(TAG).i("HTTP-request(POST): " + url + "\nupload-data: " + filePath + "\nHeader:" + headerValues);
		File file = new File(filePath);
		RequestBody image = RequestBody.create(MediaType.parse("image/png"), file);
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("file", filePath, image)
				.build();
		Call call = newHttpCall(url, headerValues, requestBody);
		ResponseCallback callback = new ResponseCallback(url, netCallback);
		mHttpCallMap.put(call, callback);
		call.enqueue(callback);
	}

	/**
	 * HTTP POST 异步上传字节类型数据，该方法在回调中返回POST进度
	 *
	 * @param url 网络地址
	 * @param data 要上传的数据
	 * @param netCallback 回调
	 */
	public void asyncPostBytesByHttp(String url, HashMap<String, String> headerValues, byte[] data, INetCallback netCallback) {
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), data);
		Call call = newHttpCall(url, headerValues, requestBody);
		ResponseCallback callback = new ResponseCallback(url, netCallback);
		mHttpCallMap.put(call, callback);
		call.enqueue(callback);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 私有方法
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//新建一个请求Call
	private Call newHttpCall(String url, HashMap<String, String> headerValues, RequestBody requestBody) {
		Request request = createRequest(url, headerValues, requestBody);
		Call call = mClient.newCall(request);
		return call;
	}

	// 创建HTTP请求对象
	private Request createRequest(String url, HashMap<String, String> headerValues, RequestBody requestBody) {
		Request.Builder requestBuilder = null;
		try {
			requestBuilder = new Request.Builder().url(url);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
			mHttpCallMap.remove(call);
		}
		return responseData;
	}

	// HTTP异步请求响应回调
	private class ResponseCallback implements Callback {
		private String url = null;
		private INetCallback callback;


		public ResponseCallback(String url, INetCallback callback) {
			this.url = url;
			this.callback = callback;
			if (null != callback)
				callback.onStart();
		}

		public void removeCallback() {
			callback = null;
		}

		@Override
		public void onFailure(Call call, IOException e) {
			mHttpCallMap.remove(call);
			Logger.t(TAG).e(e, "Request fail: url:" + url + "\n");
			if(null != callback)
				callback.onError(e);
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
					String responseStr = new String(data);
					Logger.t(TAG).i("Response success: " + url + "\ndata:" + responseStr);
					if (null != callback) {
						callback.onNext(data);
						callback.onComplete();
					}
				} else {
					Logger.t(TAG).e("Response error: " + url + "\ncode：" + response.code() + "\nmessage：" + response.message());
					if(null != callback)
						callback.onResponseError(response.code(), response.message());
				}
			} catch (IOException e) {
				Logger.t(TAG).e(e, "HTTP response IO error");
				if(null != callback)
					callback.onError(e);
			} finally {
				mHttpCallMap.remove(call);
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
}
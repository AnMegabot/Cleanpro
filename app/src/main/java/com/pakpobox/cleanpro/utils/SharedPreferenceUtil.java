package com.pakpobox.cleanpro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 通用SharedPreferences工具类
 * User:Sean.Wei
 * Date:2017/12/28
 * Time:15:18
 */
public class SharedPreferenceUtil {
	private SharedPreferences mPreference;
	private Editor mEditor;

	public SharedPreferenceUtil(Context context, String fileName) {
		if (mPreference == null || mEditor == null) {
			mPreference = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
			mEditor = mPreference.edit();
		}
	}

	public void commitInt(String key, int value) {
		if (null == mEditor)
			return;
		mEditor.putInt(key, value);
		mEditor.commit();
	}

	public int getInt(String key, int defValue) {
		if (mPreference == null)
			return defValue;
		return mPreference.getInt(key, defValue);
	}

	public long getLong(String key, long defValue) {
		if (mPreference == null)
			return defValue;
		return mPreference.getLong(key, defValue);
	}

	public void commitBoolean(String key, boolean value) {
		if (null == mEditor)
			return;
		mEditor.putBoolean(key, value);
		mEditor.commit();
	}

	public void commitLong(String key, long value) {
		if (null == mEditor)
			return;
		mEditor.putLong(key, value);
		mEditor.commit();
	}

	public boolean getBoolean(String key, boolean defValue) {
		if (mPreference == null)
			return defValue;
		return mPreference.getBoolean(key, defValue);
	}

	public void commitString(String key, String value) {
		if (null == mEditor)
			return;
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public String getString(String key, String defValue) {
		if (mPreference == null)
			return defValue;
		return mPreference.getString(key, defValue);
	}

	public void commitFloat(String key, float value) {
		if (null == mEditor)
			return;
		mEditor.putFloat(key, value);
		mEditor.commit();
	}

	public float getFloat(String key, float defValue) {
		if (mPreference == null)
			return defValue;
		return mPreference.getFloat(key, defValue);
	}

	public void remove(String key) {
		if (null == mEditor)
			return;
		mEditor.remove(key).commit();
	}

	public void clear() {
		if (null == mEditor)
			return;
		mEditor.clear().commit();
	}
}
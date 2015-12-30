package com.xguokr.util;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class XGUtil {

	private final static String SharedPreferencesName = "XGuokr";
	private static Editor mEditor;
	
	public static void LogUitl(String s){
		Log.i("XGUOKR", s);
	}
	
	public static void ToastUtil(Context context, String s){
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
	
	public static String SPGetStringUtil(Activity activity,String key){
		return activity.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE).getString(key, "");
	}
	
	public static void SPSaveUtil(Activity activity,String key,String value){
		XGUtil.LogUitl(activity.getClass().getName() + "  SPSaveUtil ,  key:" + key + "  value:" + value );
		if(mEditor == null){
			mEditor =activity.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE).edit();
		}
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public static String SPGetStringUtil(Context context,String key){
		//return XGuokrApplication.getApplication().getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE).getString(key, "");
		SharedPreferences s = context.getSharedPreferences(SharedPreferencesName, Context.MODE_PRIVATE);
		return  s.getString(key, "");
	}
}

package com.xguokr.activity;

import android.app.Application;

public class XGuokrApplication extends Application {

	public static  XGuokrApplication mXGuokrAppcation;

	@Override
	public void onCreate() {
		super.onCreate();
		mXGuokrAppcation = this;
	}

	public static Application getApplication(){
		return mXGuokrAppcation;
	}
	
	
}

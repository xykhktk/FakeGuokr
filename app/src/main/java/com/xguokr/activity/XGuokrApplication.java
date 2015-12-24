package com.xguokr.activity;

import android.app.Application;

public class XGuokrApplication extends Application {

	public static  XGuokrApplication mXGuokrAppcation;
	
	public static Application getApplication(){
		return mXGuokrAppcation;
	}
	
	
}

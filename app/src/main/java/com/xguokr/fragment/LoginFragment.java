package com.xguokr.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

public class LoginFragment extends Fragment{

	private WebView mWebView;
	//private TextView tv;
	private String cookieStr;
	private StringBuilder sb;
	private LoginSuccessCallBack mCallback;
	
	private WebViewClient mWebViewClient = new WebViewClient(){
		
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			
			if(url.equals(Const.URL_loginSUCCESS_1 )|| url.equals(Const.URL_loginSUCCESS_2)){
				//XGUtil.LogUitl("shouldOverrideUrlLoading" + cookieStr);
				if(parseCookie(cookieStr)){
					//mWebView.stopLoading();
					XGUtil.SPSaveUtil(getActivity(), Const.SPKey_CookieStr, cookieStr);
					if(mCallback != null) mCallback.loginSuccess();
					
				}else{
					//XGUtil.ToastUtil(getActivity(), "登陆失败");
					view.loadUrl(Const.URL_LOGIN);
				}
			}else{
				view.loadUrl(url);
			}
			return true;
			
		};
		
		
		public void onPageFinished(WebView view, String url) {
			
			cookieStr = CookieManager.getInstance().getCookie(url);
			if(Const.URL_LOGIN.equals(url)){
				view.stopLoading();
			}
			//XGUtil.LogUitl("onPageFinished" + cookieStr);
			if(parseCookie(cookieStr)){
				//XGUtil.ToastUtil(getActivity(), cookieStr);
				//mWebView.stopLoading();
				XGUtil.SPSaveUtil(getActivity(), Const.SPKey_CookieStr, cookieStr);
				if(mCallback != null) mCallback.loginSuccess();
			}
		};
	};
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, container, false);
		mWebView = (WebView) v.findViewById(R.id.WebView_fragemnt_login);
		
		//CookieSyncManager csm = CookieSyncManager.createInstance(XGuokrApplication.getApplication());
		CookieSyncManager csm = CookieSyncManager.createInstance(getActivity());
		CookieManager cm = CookieManager.getInstance();
		cm.hasCookies();
		cm.setAcceptCookie(true);
		cm.removeAllCookie();
		cm.removeSessionCookie();
		CookieSyncManager.getInstance().sync();

		mWebView.setWebViewClient(mWebViewClient);
		mWebView.getSettings().setJavaScriptEnabled(false);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.loadUrl(Const.URL_LOGIN);
		return v;
	}

	private boolean parseCookie(String s) {
		// TODO Auto-generated method stub
		if (s == null){
			XGUtil.ToastUtil(getContext(),"解析错误，没有网络吗？");
			return  false;
		}

		String token = "";
		String ukey = "";
		String[] cookieKV = s.split(";");
		for(int i = 0 ;i < cookieKV.length;i++){
			String[] KV = cookieKV[i].split("=");
			String Key = KV[0].trim();
			if(Key.equals(Const.CookieKey_Token)){
				token = KV[1].trim();
			}else if(Key.equals(Const.CookieKey_Ukey)){
				ukey = KV[1].trim();
			}
		}
		if(TextUtils.isEmpty(ukey) || TextUtils.isEmpty(token)){
			return false;
		}else{
			XGUtil.SPSaveUtil(getActivity(), Const.SPKey_Token, token);
			XGUtil.SPSaveUtil(getActivity(), Const.SPKey_Ukey, ukey);
			XGUtil.LogUitl(getClass().getName() + "   parseCookie  ukey:" + ukey + "  token:" + token);
			return true;
		}
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try {
			mCallback = (LoginSuccessCallBack)activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new ClassCastException("activity must implement LoginSuccessCallBack");
		}
	}
	
	public interface LoginSuccessCallBack{
		public void loginSuccess();
	}
	
}

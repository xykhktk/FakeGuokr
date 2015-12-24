package com.xguokr.net;

import android.text.TextUtils;

import com.xguokr.bean.KexuerenArticle;
import com.xguokr.util.XGUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class KexuerenArticleGet {
	
	private KexuerenArticle mKexuerenArticle;
	
	
	public void KexuerenArticleGet() {
		// TODO Auto-generated constructor stub
	}
	
	public void getKexuerenArticleByOKHttp(String address,final SuccessCallback mSuccessCallback,
			final FaileCallback mFaileCallback){

		new XGOkHttpConnection().get(address, new XGOkHttpConnection.onSuccess() {
			@Override
			public void success(String s) {
				if(mSuccessCallback != null) mSuccessCallback.onSuccess(stringResultToArticle(s));
			}
		}, new XGOkHttpConnection.onFaile() {
			@Override
			public void faile(int resultCode) {
				if(mFaileCallback != null) mFaileCallback.onFaile();
			}
		});

	}

	private KexuerenArticle stringResultToArticle(String s){
		mKexuerenArticle = new KexuerenArticle();
		try {
			JSONObject all = new JSONObject(s);
			JSONObject result = all.getJSONObject("result");

			if (!TextUtils.isEmpty(result.getString("date_published")))
				mKexuerenArticle.setTime(result.getString("date_published"));
			XGUtil.LogUitl(getClass().getName() + "result.getString(date_published)" + result.getString("date_published"));
			
			JSONObject author = result.getJSONObject("author");
			mKexuerenArticle.setAuthor(author.getString("nickname"));
			//mKexuerenArticle.setAuthorImgUrl(author.getString(""));
			
			mKexuerenArticle.setArticleContent(result.getString("content"));
			mKexuerenArticle.setTitile(result.getString("title"));
			mKexuerenArticle.setArticleImgUrl(result.getString("small_image"));
			mKexuerenArticle.setArticleID(result.getString("id"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return mKexuerenArticle;
	}



	public interface SuccessCallback{
		public void onSuccess(KexuerenArticle result);
	}
	
	public  interface FaileCallback{
		public void onFaile();
	}
}

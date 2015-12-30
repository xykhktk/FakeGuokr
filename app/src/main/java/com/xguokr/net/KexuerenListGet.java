package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.ArticlelistItem;
import com.xguokr.util.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KexuerenListGet {

	
	/*public void getKexuerenByHttpClinet(int articleNum,final SuccessCallback mSuccessCallback,final FaileCallback mFaileCallback){
		XGHttpConnection conn = new XGHttpConnection();
		conn.XGHttpGetConnection(Const.URL_kexueren,
				new XGHttpConnection.SuccessCallback() {

					@Override
					public void onSuccess(String s) {
						if(mSuccessCallback != null) mSuccessCallback.onSuccess(getActicleListFromJson(s));

					}
				}, new XGHttpConnection.FaileCallback() {

					@Override
					public void onFaile(int errorcode) {
						if(mFaileCallback != null) mFaileCallback.onFaile();

					}
				},Const.HttpKey_retrieve_type,Const.HttpValue_by_subject
				,Const.HttpKey_limit,articleNum + "");
	}*/


	public void getKexuerenByOkHttp(int articleNum,Activity activity,final SuccessCallback mSuccessCallback,final FaileCallback mFaileCallback){

		new XGOkHttpConnection().get(Const.URL_kexueren, new XGOkHttpConnection.onSuccess() {
			@Override
			public void success(String s) {
				if(mSuccessCallback != null) mSuccessCallback.onSuccess(getActicleListFromJson(s));
			}
		}, new XGOkHttpConnection.onFaile() {
			@Override
			public void faile(int resultCode) {
				if(mFaileCallback != null) mFaileCallback.onFaile();
			}
		},Const.HttpKey_retrieve_type,Const.HttpValue_by_subject
				,Const.HttpKey_limit,articleNum + "");
	}

	public static interface SuccessCallback{
		public void onSuccess(ArrayList<ArticlelistItem> list);
	}
	
	public static interface FaileCallback{
		public void onFaile();
	}
	
	private ArrayList<ArticlelistItem> getActicleListFromJson(String data){
		ArrayList<ArticlelistItem> list = new ArrayList<>();
		if(data != null && data.length() <= 0) return list;
		try {
			JSONObject all = new JSONObject(data);
			//JSONArray mJSONArray = new JSONArray(j.getJSONArray("result").toString());
			
			JSONArray mJSONArray = all.getJSONArray("result");
			//System.out.println(j.getJSONArray("result").toString());
			//JSONArray mJSONArray = new JSONArray(data);
			if(mJSONArray != null){
				for (int i = 0; i < mJSONArray.length(); i++) {
					JSONObject o = mJSONArray.getJSONObject(i);
					ArticlelistItem item = new ArticlelistItem();
					item.setTitle(o.getString("title"));
					item.setTitleImageUrl(o.getString("small_image"));
					item.setSummary(o.getString("summary"));
					item.setDate(o.getString("date_created"));
					item.setResourceUrl(o.getString("resource_url"));
					list.add(item);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}

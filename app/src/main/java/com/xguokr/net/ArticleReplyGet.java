package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.ReplyItem;
import com.xguokr.util.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ArticleReplyGet {

	private ArrayList<ReplyItem> result;
	
	/*public void getAriticleReplyByHttpClient(String articleId,final SuccessCallback mSuccessCallback ,final FaileCallback mFaileCallback){

		new XGHttpConnection().XGHttpGetConnection(Const.URL_ArticleReply,
				new XGHttpConnection.SuccessCallback() {
					
					@Override
					public void onSuccess(String s) {
						// TODO Auto-generated method stub
						if(mSuccessCallback != null) mSuccessCallback.onSuccess(StringToReplyList(s));
					}
				}, new XGHttpConnection.FaileCallback() {
					
					@Override
					public void onFaile(int errorcode) {
						// TODO Auto-generated method stub
						if(mFaileCallback != null) mFaileCallback.onFalie();
					}
				}, Const.HttpKey_articleid,articleId);
		
	}*/

		public void getAriticleReplyByOKHttp(Activity activity,String articleId,final SuccessCallback mSuccessCallback ,
												 final FaileCallback mFaileCallback){
			new XGOkHttpConnection().get(Const.URL_ArticleReply,
					new XGOkHttpConnection.onSuccess() {
						@Override
						public void success(String s) {
							if(mSuccessCallback != null) mSuccessCallback.onSuccess(StringToReplyList(s));
						}
					}, new XGOkHttpConnection.onFaile() {
						@Override
						public void faile(int resultCode) {
							if(mFaileCallback != null) mFaileCallback.onFalie();
						}
					}, Const.HttpKey_articleid,articleId);

		}


		private ArrayList<ReplyItem> StringToReplyList(String s){
		if(s.length() <= 0) return result;
		result = new ArrayList<>();
		try {
			JSONObject all = new JSONObject(s);
			JSONArray array = all.getJSONArray("result");
			for(int i = 0;i < array.length();i++){
				JSONObject o = array.getJSONObject(i);
				ReplyItem a = new ReplyItem();
				
				a.setHtml(o.getString("html"));
				a.setCount((i+1) +"");
				a.setDate_created(o.getString("date_created").substring(0, 16).replace("T", " "));
				
				JSONObject author = o.getJSONObject("author");
				a.setAuthor_Nickename(author.getString("nickname"));
				
				JSONObject avantar = author.getJSONObject("avatar");
				a.setAuthor_avatar(avantar.getString("normal"));
				
				result.add(a);
			}
			//http://apis.guokr.com/minisite/article_reply.json?article_id=440967
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public interface SuccessCallback{
		public void onSuccess(ArrayList<ReplyItem> r);
	}
	
	public interface FaileCallback{
		public void onFalie();
	}
}

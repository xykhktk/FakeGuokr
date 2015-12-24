package com.xguokr.net;

import com.xguokr.bean.UserInfo;
import com.xguokr.util.Const;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoGet {

	private UserInfo info;
	
	public void getInfo(String ukey,final SuccessCallBack mSuccessCallBack,FaileCallBack mFaileCallBack){
		
		String url = Const.URL_UserIndo +  ukey + ".json";

		new XGOkHttpConnection().get(url, new XGOkHttpConnection.onSuccess() {
			@Override
			public void success(String s) {
				if(mSuccessCallBack != null) mSuccessCallBack.onSuccess(StringResultToUserInfo(s));
			}
		}, new XGOkHttpConnection.onFaile() {
			@Override
			public void faile(int resultCode) {

			}
		});
		
		/*new XGHttpConnection().XGHttpGetConnection(url,
				new XGHttpConnection.SuccessCallback() {
					
					@Override
					public void onSuccess(String s) {
						// TODO Auto-generated method stub
						if(mSuccessCallBack != null) mSuccessCallBack.onSuccess(StringResultToUserInfo(s));
					}
				}, new XGHttpConnection.FaileCallback() {
					
					@Override
					public void onFaile(int errorcode) {
						// TODO Auto-generated method stub
						
					}
				});*/
	}
	
	private UserInfo StringResultToUserInfo(String s){
		info = new UserInfo();
		//XGUtil.LogUitl(s);
		try {
			JSONObject all = new JSONObject(s);
			if ( (all.getString("ok"))!= null && (all.getString("ok")).equals("true")){
				
				JSONObject result = all.getJSONObject("result");
				info.setAnswers_count(result.getString("answers_count"));
				info.setAvatar(result.getJSONObject("avatar").getString("large"));
				info.setBlogs_count(result.getString("blogs_count"));
				info.setDate_created(result.getString("date_created"));
				info.setNickname(result.getString("nickname"));
				info.setPosts_count(result.getString("posts_count"));
				info.setQuestions_count(result.getString("questions_count"));
				info.setFollowings_count(result.getString("followings_count"));
				info.setIntroduction(result.getString("introduction"));
				
				/*XGUtil.LogUitl("answers_count" +result.getString("answers_count"));
				XGUtil.LogUitl("avatar" +result.getJSONObject("avatar").getString("normal"));
				XGUtil.LogUitl("blogs_count" +result.getString("blogs_count"));
				XGUtil.LogUitl("date_created" + result.getString("date_created"));
				XGUtil.LogUitl("posts_count" +result.getString("posts_count"));
				XGUtil.LogUitl("questions_count" + result.getString("questions_count"));
				XGUtil.LogUitl("followings_count" + result.getString("followings_count"));
				XGUtil.LogUitl("introduction" + result.getString("introduction"));*/
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return info;
	}
	
	public interface SuccessCallBack{
		public void onSuccess(UserInfo info);
	}
	
	public interface FaileCallBack{
		public void onFaile();
	}
}

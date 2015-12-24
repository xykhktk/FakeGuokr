package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.QuestionTag;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class QuestionTagGet {

	private ArrayList<QuestionTag> Taglist;
	
	public QuestionTagGet() {
		// TODO Auto-generated constructor stub
		Taglist = new ArrayList<QuestionTag>();
	}
	
	public void getQuestionTag(Activity activity,int page ,final SuccessCallback mSuccessCallback,final FailCallback mFailCallback){

		/*new XGHttpConnection().XGHttpGetConnection(Const.URL_QuestionTag,
				new XGHttpConnection.SuccessCallback() {
					
					@Override
					public void onSuccess(String s) {
						// TODO Auto-generated method stub
						if(s == null && s.length() == 0){
							if(mFailCallback != null) mFailCallback.onFail(Const.Code_Noresult);
						}else{
							if (mSuccessCallback != null) mSuccessCallback.onSuccess( htmlToList(s));
						}
					}
				}, new XGHttpConnection.FaileCallback() {
					
					@Override
					public void onFaile(int errorcode) {
						// TODO Auto-generated method stub
						if(mFailCallback != null) mFailCallback.onFail(errorcode);
					}
				},"page",page + "");*/
	}


	public void getQuestionTagByOkhttp(int page ,Activity activity,final SuccessCallback mSuccessCallback,final FailCallback mFailCallback){

		XGUtil.LogUitl(getClass().getName() + "page" + page);
		new XGOkHttpConnection().get(Const.URL_QuestionTag, new XGOkHttpConnection.onSuccess() {
			@Override
			public void success(String s) {
				if (s == null && s.length() == 0) {
					if (mFailCallback != null) mFailCallback.onFail(Const.Code_Noresult);
				} else {
					if (mSuccessCallback != null) mSuccessCallback.onSuccess(htmlToList(s));
				}
			}
		}, new XGOkHttpConnection.onFaile() {
			@Override
			public void faile(int resultCode) {
				if (mFailCallback != null) mFailCallback.onFail(resultCode);
			}
		}, Const.HttpKey_page, page + "");

	}


	private ArrayList<QuestionTag> htmlToList(String s){
		Taglist.clear();
		Document doc = Jsoup.parse(s);
		Elements all = doc.getElementsByClass("join-list");
		if(all.size() == 1){
			Elements list = all.get(0).getElementsByTag("li");
			for(int i = 0;i < list.size();i++){
				QuestionTag q = new QuestionTag();
				//Log.i(Constants.LogiTag,list.get(i).getElementsByTag("img").attr("src"));
				q.setTagImgUrl(list.get(i).getElementsByTag("img").attr("src"));
				Elements desc = list.get(i).getElementsByClass("join-list-desc");
				for (int j = 0; j < desc.size(); j++) {
					//Log.i(Constants.LogiTag,desc.get(j).getElementsByTag("a").text());
					//Log.i(Constants.LogiTag,desc.get(j).getElementsByTag("span").text());
					//Log.i(Constants.LogiTag,desc.get(j).getElementsByTag("p").text());
					q.setTagName(desc.get(j).getElementsByTag("a").text());
					q.setTagNum(desc.get(j).getElementsByTag("span").text());
					q.setTagDesc(desc.get(j).getElementsByTag("p").text());
					q.setIndex(i+"");
				}
				Taglist.add(q);
			}
		}
		//Log.i(Const.LogiTag,getClass().getName() + " Taglist.size "+Taglist.size());
		return Taglist;
	}
		
	public interface SuccessCallback{
		public void onSuccess(ArrayList<QuestionTag> list);
	}
	
	public interface FailCallback{
		public void onFail(int errorCode);
	}
	
}

package com.xguokr.net;

import android.app.Activity;
import android.text.TextUtils;

import com.xguokr.bean.GroupTag;
import com.xguokr.util.Const;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class GroupTagGet {

	private ArrayList<GroupTag> Taglist;

	public GroupTagGet() {
		// TODO Auto-generated constructor stub
		Taglist = new ArrayList<GroupTag>();
	}
	
	
	public void getGroupTag(Activity activity,int page ,final SuccessCallback mSuccessCallback,final FailCallback mFailCallback){

		new XGOkHttpConnection().get(Const.URL_GroupTag, new XGOkHttpConnection.onSuccess() {
			@Override
			public void success(String s) {
				if(s == null || s.length() == 0){
					if(mFailCallback != null) mFailCallback.onFail(Const.Code_Noresult);
				}else{
					if (mSuccessCallback != null) mSuccessCallback.onSuccess(htmlToList(s));
				}
			}
		}, new XGOkHttpConnection.onFaile() {
			@Override
			public void faile(int resultCode) {
				if(mFailCallback != null) mFailCallback.onFail(resultCode);
			}
		},Const.HttpKey_post_id,page + "");

	}

	public void getGroupTagByOkHttp(int page ,Activity activity,final SuccessCallback mSuccessCallback,final FailCallback mFailCallback){

		new XGOkHttpConnection().get(Const.URL_GroupTag, new XGOkHttpConnection.onSuccess() {
			@Override
			public void success(String s) {
				if(s == null || s.length() == 0){
					if(mFailCallback != null) mFailCallback.onFail(Const.Code_Noresult);
				}else{
					if (mSuccessCallback != null) mSuccessCallback.onSuccess(htmlToList(s));
				}
			}
		}, new XGOkHttpConnection.onFaile() {
			@Override
			public void faile(int resultCode) {
				if(mFailCallback != null) mFailCallback.onFail(resultCode);
			}
		},"page",page+"");

	}


	private ArrayList<GroupTag> htmlToList(String s){
		Taglist.clear();
		Document doc = Jsoup.parse(s);
		//Elements all = doc.getElementsByClass("join-list");
		Elements ranks = doc.getElementsByClass("ranks");
				if(ranks.size() == 1){
					Elements lis = ranks.get(0).getElementsByTag("li");
					//XGUtil.LogUitl("" + lis.size());
					for(int i = 0;i < lis.size();i++){
						GroupTag g = new GroupTag();

						if(!TextUtils.isEmpty(lis.get(i).getElementsByClass("rank-num-top").text())){
							g.setRank_num_top(lis.get(i).getElementsByClass("rank-num-top").text());
						}else{
							g.setRank_num_top(lis.get(i).getElementsByClass("rank-num").text());
						}

				if(!TextUtils.isEmpty(lis.get(i).getElementsByClass("super-group-name").text())){
					g.setGroupName(lis.get(i).getElementsByClass("super-group-name").text());
				}else{
					g.setGroupName(lis.get(i).getElementsByAttribute("target").text());
				}

				g.setGroupMemberNum(lis.get(i).getElementsByClass("group-members").text());
				g.setGroupImageUrl(lis.get(i).getElementsByTag("img").attr("src"));
				g.setGroupId(lis.get(i).getElementsByClass("group-head").attr("data-group_id"));
				//XGUtil.LogUitl(g.getGroupId() + "  " + g.getGroupName() + "  " + g.getRank_num_top() +"  "+ g.getTagImageUrl() + " " + g.getGroupMemberNum());
				Taglist.add(g);
			}
		}
		return Taglist;
	}

	public interface SuccessCallback{
		public void onSuccess(ArrayList<GroupTag> list);
	}
	
	public interface FailCallback{
		public void onFail(int errorCode);
	}
	
}

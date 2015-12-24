package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.GroupArtcleListItem;
import com.xguokr.util.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yk on 2015/12/11.
 */
public class GroupArticleListGet {

    public void getGroupArticleListByOKhttp(Activity activity, String groupId, String limit, final SuccessCallback mSuccessCallback,
                                            final FaileCallback mFaileCallback){

        String url = "http://apis.guokr.com/group/post.json";
        new XGOkHttpConnection().get(url, new XGOkHttpConnection.onSuccess() {
            @Override
            public void success(String s) {
                if (s == null && s.length() == 0) {
                    if (mFaileCallback != null) mFaileCallback.onFaile(Const.Code_Noresult);
                } else {
                    if (mSuccessCallback != null) mSuccessCallback.onSuccess(stringResultToList(s));
                }
            }
        }, new XGOkHttpConnection.onFaile() {
            @Override
            public void faile(int resultCode) {
                if (mFaileCallback != null) mFaileCallback.onFaile(resultCode);
            }
        }, Const.HttpKey_retrieve_type,Const.HttpValue_by_group
                ,Const.HttpKey_group_id,groupId
                ,"limit", limit + "");

    }

    private ArrayList<GroupArtcleListItem> stringResultToList(String s){

        ArrayList<GroupArtcleListItem> list = new ArrayList<>();

        try {
            JSONObject all = new JSONObject(s);
            if (all.getString("ok").equals("true")){
                JSONArray result = all.getJSONArray("result");
                for (int i = 0;i < result.length();i++){
                    JSONObject o = result.getJSONObject(i);
                    GroupArtcleListItem item = new GroupArtcleListItem();
                    item.setTitle(o.getString("title"));
                    item.setDate_created(o.getString("date_created").substring(0,16).replace("T"," "));
                    item.setHtml(o.getString("html"));
                    item.setId(o.getString("id"));
                    //item.setContent(o.getString("content"));

                    JSONObject author = o.getJSONObject("author");
                    item.setAuthorNickename(author.getString("nickname"));
                    list.add(item);

                }
            }else{
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }



    public interface SuccessCallback{
        public void onSuccess(ArrayList<GroupArtcleListItem> result);
    }

    public  interface FaileCallback {
        public void onFaile(int resultCode);
    }

}

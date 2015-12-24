package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.ReplyItem;
import com.xguokr.util.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yk on 2015/12/11.
 */
public class GroupArticleReplyGet {

    private ArrayList<ReplyItem> list;

    public void getGroupArticleRplyByOKhttp(Activity activity, String postId, String limit, final SuccessCallback mSuccessCallback,
                                            final FaileCallback mFaileCallback){
        //http://apis.guokr.com/group/post_reply.json?retrieve_type=by_post&post_id=[pid]&limit=[limit]
        String url = "http://apis.guokr.com/group/post_reply.json";
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
        }, Const.HttpKey_retrieve_type,Const.HttpValue_by_post
                ,Const.HttpKey_post_id,postId
                ,"limit", limit + "");

    }

    private ArrayList<ReplyItem>  stringResultToList(String s){

        list = new ArrayList<>();

        try {
            JSONObject all = new JSONObject(s);
            if (all.getString("ok").equals("true")){
                JSONArray result = all.getJSONArray("result");
                for (int i = 0;i < result.length();i++){
                    JSONObject o = result.getJSONObject(i);
                    ReplyItem item = new ReplyItem();
                    item.setDate_created(o.getString("date_created").substring(0,16).replace("T"," "));
                    item.setHtml(o.getString("html"));
                    //item.setContent(o.getString("content"));

                    JSONObject author = o.getJSONObject("author");
                    item.setAuthor_Nickename(author.getString("nickname"));
                    JSONObject avatar = author.getJSONObject("avatar");
                    item.setAuthor_avatar(avatar.getString("normal"));

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
        public void onSuccess(ArrayList<ReplyItem>  result);
    }

    public  interface FaileCallback {
        public void onFaile(int resultCode);
    }

}

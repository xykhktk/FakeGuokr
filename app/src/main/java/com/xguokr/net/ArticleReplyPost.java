package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.ReplyItem;
import com.xguokr.util.Const;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yk on 2015/12/10.
 */
public class ArticleReplyPost {

    ReplyItem replyItem ;


    public void postReply(Activity activity,PostType postType,String content,String id,String token,String ukey,
                          final  SuccessCallback mSuccessCallback,final FaileCallback mFaileCallback){
        String url = null;
        String httpIdKey = null;
        if (postType == PostType.Replyquestion){
            url = Const.URL_ReplyQuestionAnswer;
            httpIdKey = Const.HttpKey_question_id;
        }else if (postType == PostType.ReplyKexuerenArticle) {
            url = Const.URL_ReplyKexuerenArticle;
            httpIdKey = Const.HttpKey_articleid;
        }else if (postType == PostType.ReplyGroupAricicle){
            url = Const.URL_ReplyGroupArticle;
            httpIdKey = Const.HttpKey_post_id;
        }

        XGOkHttpConnection xgOkHttpConnection = new XGOkHttpConnection();
        xgOkHttpConnection.setCookie(ukey,token);

        xgOkHttpConnection.post(url,new XGOkHttpConnection.onSuccess() {
                    @Override
                    public void success(String s) {
                        if (mSuccessCallback != null) mSuccessCallback.onSuccess(StringToReply(s));
                    }
                }, new XGOkHttpConnection.onFaile() {
                    @Override
                    public void faile(int resultCode) {
                        if (mFaileCallback != null) mFaileCallback.onFalie();
                    }
                }, httpIdKey, id
                , Const.HttpKey_access_token, token
                , Const.HttpKey_content,content);
    }


    private ReplyItem StringToReply(String s){
        replyItem = new ReplyItem();
        if(s.length() <= 0) return replyItem;

        try {
            JSONObject all = new JSONObject(s);
            JSONObject result = all.getJSONObject("result");

            replyItem.setHtml(result.getString("html"));
            replyItem.setDate_created(result.getString("date_created").substring(0, 16).replace("T", " "));
            JSONObject author = result.getJSONObject("author");
            replyItem.setAuthor_Nickename(author.getString("nickname"));
                JSONObject avantar = author.getJSONObject("avatar");
            replyItem.setAuthor_avatar(avantar.getString("normal"));

            //http://apis.guokr.com/minisite/article_reply.json?article_id=440967
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return replyItem;
    }


    public interface SuccessCallback{
        public void onSuccess(ReplyItem newReply);
    }

    public interface FaileCallback {
        public void onFalie();
    }

    public enum PostType{
        Replyquestion,ReplyKexuerenArticle,ReplyGroupAricicle
    }

}

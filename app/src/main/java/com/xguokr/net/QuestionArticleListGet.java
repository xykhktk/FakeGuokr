package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.QuestionArticleListItem;
import com.xguokr.util.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created on 2015/12/11.
 */
public class QuestionArticleListGet {

    public void getQuestionArticleListByOKhttp(Activity activity, String questiontagname, String limit, final SuccessCallback mSuccessCallback,
                                            final FaileCallback mFaileCallback){

        String url = "http://apis.guokr.com/ask/question.json";
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
        }, Const.HttpKey_retrieve_type,Const.HttpValue_by_tag
                ,Const.HttpKey_tag_name,questiontagname
                ,"limit", limit + "");

    }

    private ArrayList<QuestionArticleListItem> stringResultToList(String s){

        ArrayList<QuestionArticleListItem> list = new ArrayList<>();

        try {
            JSONObject all = new JSONObject(s);
            if (all.getString("ok").equals("true")){
                JSONArray result = all.getJSONArray("result");
                for (int i = 0;i < result.length();i++){
                    JSONObject o = result.getJSONObject(i);
                    QuestionArticleListItem item = new QuestionArticleListItem();
                    item.setId(o.getString("id"));
                    item.setDate_created(o.getString("date_created").substring(0, 16).replace("T", " "));
                    item.setAnswers_count(o.getString("answers_count"));
                    item.setFollowers_count(o.getString("followers_count"));
                    item.setQuestion(o.getString("question"));
                    item.setSummary(o.getString("summary"));

                    JSONObject author = o.getJSONObject("author");
                    item.setAuthor_nickename(author.getString("nickname"));
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
        public void onSuccess(ArrayList<QuestionArticleListItem> result);
    }

    public  interface FaileCallback {
        public void onFaile(int resultCode);
    }

}

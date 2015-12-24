package com.xguokr.net;

import android.app.Activity;

import com.xguokr.bean.QuestionArticleContent;
import com.xguokr.bean.ReplyItem;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created on 2015/12/11.
 */
public class QuestionArticleAndReplyGet {

    public void getQuestionArticleContentByOKhttp(Activity activity, String questionId, final GetContentSuccessCallback mGetContentSuccessCallback,
                                            final FaileCallback mFaileCallback){

        String url = "http://apis.guokr.com/ask/question/" + questionId + ".json";
        new XGOkHttpConnection().get(url, new XGOkHttpConnection.onSuccess() {
            @Override
            public void success(String s) {
                if (s == null && s.length() == 0) {
                    if (mFaileCallback != null) mFaileCallback.onFaile(Const.Code_Noresult);
                } else {
                    if (mGetContentSuccessCallback != null) mGetContentSuccessCallback.onGetContentSuccess(stringResultToContent(s));
                }
            }
        }, new XGOkHttpConnection.onFaile() {
            @Override
            public void faile(int resultCode) {
                if (mFaileCallback != null) mFaileCallback.onFaile(resultCode);
            }
        });

    }

    public void getQuestionReplyByOKhttp(Activity activity, String questionId,String limit, final GetReplySuccessCallback mGetReplySuccessCallback,
                                                  final FaileCallback mFaileCallback){
        //http://apis.guokr.com/ask/answer.json?retrieve_type=by_question&question_id=[qid]&limit=[limit]
        String url = "http://apis.guokr.com/ask/answer.json";
        new XGOkHttpConnection().get(url, new XGOkHttpConnection.onSuccess() {
            @Override
            public void success(String s) {
                if(mGetReplySuccessCallback != null) mGetReplySuccessCallback.onGetReplySuccess(stringResultToReplyList(s));
            }
        }, new XGOkHttpConnection.onFaile() {
            @Override
            public void faile(int resultCode) {

            }
        },Const.HttpKey_retrieve_type,Const.HttpValue_by_question
                ,Const.HttpKey_question_id,questionId
                ,Const.HttpKey_limit,limit);
    }

    private QuestionArticleContent stringResultToContent(String s){
        QuestionArticleContent content = new QuestionArticleContent();
        try {
            JSONObject all = new JSONObject(s);
            if (all.getString("ok").equals("true")) {
                XGUtil.LogUitl(getClass().getName() + "  ok equals true");
                JSONObject result = all.getJSONObject("result");
                content.setId(result.getString("id"));
                content.setDate_created(result.getString("date_created").substring(0, 16).replace("T", " "));
                content.setAnswers_count(result.getString("answers_count"));
                content.setFollowers_count(result.getString("followers_count"));
                content.setAnnotation_html(result.getString("annotation_html"));
                content.setSummary(result.getString("summary"));
                content.setQuestion(result.getString("question"));

                JSONObject author = result.getJSONObject("author");
                content.setAuthor_nickename(author.getString("nickname"));
                JSONObject avatar = author.getJSONObject("avatar");
                content.setAuthor_avatar(avatar.getString("normal"));
            }

        }catch (JSONException e1) {
            e1.printStackTrace();
        }

        return content;
    }

    private ArrayList<ReplyItem> stringResultToReplyList(String s){

        ArrayList<ReplyItem> list = new ArrayList<>();
        try {
            JSONObject all = new JSONObject(s);
            if (all.getString("ok").equals("true")) {
                JSONArray result = all.getJSONArray("result");
                for (int i = 0; i < result.length(); i++) {
                    JSONObject o = result.getJSONObject(i);
                    ReplyItem item = new ReplyItem();
                    item.setDate_created(o.getString("date_created").substring(0, 16).replace("T", " "));
                    item.setHtml(o.getString("html"));
                    //item.setContent(o.getString("content"));
                    JSONObject author = o.getJSONObject("author");
                    item.setAuthor_Nickename(author.getString("nickname"));
                    JSONObject avatar = author.getJSONObject("avatar");
                    item.setAuthor_avatar(avatar.getString("normal"));
                    list.add(item);
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }


        return list;
    }

    public interface  GetContentSuccessCallback{
        void onGetContentSuccess(QuestionArticleContent result);
    }

    public interface GetReplySuccessCallback{
        void onGetReplySuccess(ArrayList<ReplyItem> result);
    }

    public  interface FaileCallback {
        public void onFaile(int resultCode);
    }

}

package com.xguokr.net;

import android.app.Activity;
import android.text.TextUtils;

import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created  on 2015/12/15.
 */
public class TestToken {

    public static int testResult_logout = 2;
    public static int testResult_login = 1;

    private String url = "http://www.guokr.com/apis/community/relationship/follow.json";
    //http://www.guokr.com/apis/community/relationship/follow.json?access_token=[access_token]



    public void postRelationship(Activity activity,final  SuccessCallback mSuccessCallback,final FaileCallback mFaileCallback){

        String ukey = XGUtil.SPGetStringUtil(activity, Const.SPKey_Ukey);
        String token = XGUtil.SPGetStringUtil(activity, Const.SPKey_Token);
        XGUtil.LogUitl(getClass().getName() + "  read done : parseCookie  ukey:" + ukey + "  token:"+token);
        if (TextUtils.isEmpty(token) || TextUtils.isEmpty(ukey)){
            if (mFaileCallback != null) mFaileCallback.onFalie();
        }

        XGOkHttpConnection xgOkHttpConnection = new XGOkHttpConnection();
        //xgOkHttpConnection.setCookie(ukey,token);

        xgOkHttpConnection.get(url, new XGOkHttpConnection.onSuccess() {
            @Override
            public void success(String s) {
                if(getResult(s) == testResult_login){
                    if (mSuccessCallback != null) mSuccessCallback.onSuccess(getResult(s));
                }else{
                    if (mFaileCallback != null) mFaileCallback.onFalie();
                }
            }
        }, new XGOkHttpConnection.onFaile() {
            @Override
            public void faile(int resultCode) {
                if (mFaileCallback != null) mFaileCallback.onFalie();
            }
        }, Const.HttpKey_access_token, token);

    }

    private int getResult(String s) {
        if (s.length() <= 0) return testResult_logout;
        try {
            JSONObject all = new JSONObject(s);
            String ok = all.getString("ok");
            if (ok.equals("true")){
                return testResult_login;
            }else{
                return testResult_logout;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return testResult_logout;
    }

    public interface SuccessCallback{
        public void onSuccess(int result);
    }

    public interface FaileCallback{
        public void onFalie();
    }
}

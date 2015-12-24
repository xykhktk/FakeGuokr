package com.xguokr.net;

import android.os.AsyncTask;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xguokr.bean.ConnResult;
import com.xguokr.util.XGUtil;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2015/12/10.
 */
public class XGOkHttpConnection {

    private static OkHttpClient okHttpClient;
    private final static int CONNECTION_TIMEOUT = 30000;
    private final static int SO_TIMEOUT = 60000;
    private final static int UPLOAD_SO_TIMEOUT = 300000;
    //private Activity activity;

    public void get(final String url, final onSuccess mOnSuccess,final onFaile mOnfaile,String ...keyvalue){

        StringBuffer s = new StringBuffer();
        s.append(url + "?");
        for(int i = 0; i < keyvalue.length; i+=2){
            s.append(keyvalue[i] + "=" +keyvalue[i+1] + "&");
        }

        final String finalUrl = s.toString();
        XGUtil.LogUitl(getClass().getName() + "get"+ finalUrl);
        new AsyncTask<Void,Void,ConnResult>(){

            @Override
            protected ConnResult doInBackground(Void... params) {

                ConnResult result = new ConnResult();
                Request request =new Request.Builder().get().url(finalUrl).build();
                try {
                    Response response = getOkHttpClient().newCall(request).execute();
                    result.setResultCode(response.code());
                    if (result.getResultCode() == 200) result.setResultContent(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  result;
            }

            @Override
            protected void onPostExecute(ConnResult s) {
                super.onPostExecute(s);

                if (s.getResultCode() == 200){
                    if (mOnSuccess != null) mOnSuccess.success(s.getResultContent());
                    //XGUtil.LogUitl(getClass().getName() + " ResultContent  "+ s.getResultContent());
                }else{
                    if (mOnfaile != null) mOnfaile.faile(s.getResultCode());
                }
            }
        }.execute();
    }

    public void post(final String url, final onSuccess mOnSuccess,final onFaile mOnfaile,final String ... keyvalue) {

        new AsyncTask<Void,Void,ConnResult>(){

            @Override
            protected ConnResult doInBackground(Void... params) {
                ConnResult result = new ConnResult();
                FormEncodingBuilder builder = new FormEncodingBuilder();
                //builder.add("access_token", token);
                XGUtil.LogUitl("HttpFetcher post: " + " url:" + url);
                if(keyvalue.length > 1){
                    for (int i = 0; i < keyvalue.length; i+=2) {
                        builder.add(keyvalue[i],keyvalue[i+1]);
                        XGUtil.LogUitl("HttpFetcher post: " + "key " + keyvalue[i] + " ,value " + keyvalue[i+1]);
                    }
                }
                RequestBody formBody = builder.build();
                Request request = new Request.Builder().post(formBody).url(url).build();
                try {
                    Response response = getOkHttpClient().newCall(request).execute();

                    result.setResultCode(response.code());
                    result.setResultContent(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return  result;
            }

            @Override
            protected void onPostExecute(ConnResult s) {
                super.onPostExecute(s);

                if (s.getResultCode() == 201){
                    if (mOnSuccess != null) mOnSuccess.success(s.getResultContent());
                    XGUtil.LogUitl(getClass().getName() + " post success");
                }else{
                    if (mOnfaile != null) mOnfaile.faile(s.getResultCode());
                    XGUtil.LogUitl(getClass().getName() + "post faile  ,code  :" + s.getResultCode() + " Content -->" + s.getResultContent());
                }
            }
        }.execute();


    }

   /* public static ResultObject<String> post(String url, HashMap<String, String> params, boolean needToken) throws Exception {
        ResultObject<String> resultObject = new ResultObject<>();
        String token = UserAPI.getToken();
        if (params == null) {
            params = new HashMap<>();
        }
        if (needToken && !TextUtils.isEmpty(token)) {
            params.put("access_token", token);
        }
        FormEncodingBuilder builder = new FormEncodingBuilder();
        if (params.size() > 0) {
            for (HashMap.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
                Log.i("XXX", "HttpFetcher post:" + " url:" + url + ",  entry.getKey():" + entry.getKey() + "  entry.getValue():" + entry.getValue());
            }
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder().post(formBody).url(url).build();
        Response response = getDefaultHttpClient().newCall(request).execute();
        int statusCode = response.code();
        String result = response.body().string();
        resultObject.statusCode = statusCode;
        resultObject.result = result;
        return resultObject;
    }*/


    synchronized public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
            CookieManager cookieManager = new CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            okHttpClient.setCookieHandler(cookieManager);
            //okHttpClient.networkInterceptors().add(new RedirectInterceptor());
            okHttpClient.setConnectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setReadTimeout(SO_TIMEOUT, TimeUnit.MILLISECONDS);
            okHttpClient.setWriteTimeout(UPLOAD_SO_TIMEOUT, TimeUnit.MILLISECONDS);
            //setCookie(okHttpClient);
        }
        return okHttpClient;
    }

    synchronized public void setCookie(String ukey,String token) {
        OkHttpClient client = getOkHttpClient();
        //List<String> values = new ArrayList<>(Arrays.asList("_32353_access_token=" + XGUtil.SPGetStringUtil(activity, Const.CookieKey_Token), "_32353_ukey=" + XGUtil.SPGetStringUtil(activity, Const.CookieKey_Ukey)));
        List<String> values = new ArrayList<>(Arrays.asList("_32353_access_token=" + token, "_32353_ukey=" + ukey));
        //XGUtil.LogUitl(getClass().getName() + "setCookie()  _32353_access_token=" + XGUtil.SPGetStringUtil(activity, Const.CookieKey_Token) + " _32353_ukey=" + XGUtil.SPGetStringUtil(activity, Const.CookieKey_Ukey));
        XGUtil.LogUitl("setCookie : _32353_access_token=" + token + ";_32353_ukey=" + ukey);
        Map<String, List<String>> cookies = new HashMap<>();
        cookies.put("Set-Cookie", values);
        try {
            client.getCookieHandler().put(new URI("http://.guokr.com"), cookies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface onSuccess{
        void success(String s );
    }

    public interface onFaile{
        void faile(int resultCode);
    }
}

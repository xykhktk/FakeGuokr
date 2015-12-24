package com.xguokr.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xguokr.adapter.QuestionArticleReplyAdapter;
import com.xguokr.bean.QuestionArticleContent;
import com.xguokr.bean.QuestionArticleListItem;
import com.xguokr.bean.ReplyItem;
import com.xguokr.net.ArticleReplyPost;
import com.xguokr.net.QuestionArticleAndReplyGet;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.view.PProgressDialog;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class QuestionArticleAndReplyFragment extends Fragment implements View.OnClickListener {


    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private TextView question;
    private QuestionArticleListItem item;
    //private WebView webView;
    private QuestionArticleReplyAdapter adapter;
    private ArrayList<ReplyItem> data;
    private int aplyLimit = 20;
    private EditText replycontent;
    private ImageView send;

    public static String FragmentArgumentKey = "QuestionArticleAndReplyFragmentArgumentKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_questionarticleandreply, container, false);
        question = (TextView) v.findViewById(R.id.textview_question_question_articleandReply_fragment);
        replycontent = (EditText) v.findViewById(R.id.edittext_question_articleandreply_fragment);
        send = (ImageView) v.findViewById(R.id.imageview_question_articleandreply_fragment);
        item = (QuestionArticleListItem) getArguments().getSerializable(FragmentArgumentKey);
        send.setOnClickListener(this);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_question_articleandReply_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        data = new ArrayList<>();
        adapter = new QuestionArticleReplyAdapter(getContext(),data, new QuestionArticleReplyAdapter.onItemClick() {
            @Override
            public void QuestionArticleReplyListClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
        getQuestionContent();
        return v;

    }



    private void getQuestionContent(){
        final PProgressDialog pd = new PProgressDialog(getContext()).createDialog(getResources().getString(R.string.progressdialog_default_msg));
        pd.showPd();
        new QuestionArticleAndReplyGet().getQuestionArticleContentByOKhttp(getActivity(),item.getId(),
                new QuestionArticleAndReplyGet.GetContentSuccessCallback(){
                    @Override
                    public void onGetContentSuccess(QuestionArticleContent result) {
                        question.setText(result.getQuestion());
                        ReplyItem i = new ReplyItem();
                        if (!TextUtils.isEmpty(result.getAnnotation_html())){
                            i.setHtml(result.getAnnotation_html());
                            //webView.loadData(result.getAnnotation_html(),"text/html;charset=utf-8",null);
                        }else{
                            i.setHtml("<p>（没有问题描述）</p>");
                            //webView.loadData("没有问题描述", "text/html;charset=utf-8",null);
                        }
                        data.add(i);
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                        pd.dismissPd();
                        getQuestionReply();
                    }
                },new QuestionArticleAndReplyGet.FaileCallback() {
                    @Override
                    public void onFaile(int resultCode) {
                        pd.dismissPd();
                        XGUtil.ToastUtil(getContext(), getResources().getString(R.string.toast_get_faile));
                    }
                });
    }

    private void getQuestionReply(){
        new QuestionArticleAndReplyGet().getQuestionReplyByOKhttp(getActivity(),item.getId(),aplyLimit + "",
            new QuestionArticleAndReplyGet.GetReplySuccessCallback() {
                @Override
                public void onGetReplySuccess(ArrayList<ReplyItem> result) {
                    //XGUtil.LogUitl(getClass().getName() + "   result:" + result.size());
                   if (result.size() > 0){
                       data.addAll(result);
                   }else{
                       ReplyItem i = new ReplyItem();
                       i.setHtml(getResources().getString(R.string.question_no_reply));
                   }
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                }
            }, new QuestionArticleAndReplyGet.FaileCallback() {
                @Override
                public void onFaile(int resultCode) {

                }
        });
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageview_question_articleandreply_fragment:
                String reply = replycontent.getText().toString();
                if(TextUtils.isEmpty(reply)){
                    return;
                }

                replycontent.setText("");

                String ukey = XGUtil.SPGetStringUtil(getActivity(), Const.SPKey_Ukey);
                String token = XGUtil.SPGetStringUtil(getActivity(), Const.SPKey_Token);
                XGUtil.LogUitl(getClass().getName() + "read done : parseCookie  ukey:" + ukey + "  token:"+token);
                if (TextUtils.isEmpty(token) || TextUtils.isEmpty(ukey)){
                    XGUtil.ToastUtil(getContext(),"token或token为空");
                    return;
                }

                new ArticleReplyPost().postReply(getActivity(), ArticleReplyPost.PostType.Replyquestion, reply, item.getId(), token, ukey,
                        new ArticleReplyPost.SuccessCallback() {
                            @Override
                            public void onSuccess(ReplyItem newReply) {
                                data.add(newReply);
                                //data.get(data.size() - 1).setCount(data.size() + "");
                                adapter.setData(data);
                                adapter.notifyDataSetChanged();
                            }
                        }, new ArticleReplyPost.FaileCallback() {
                            @Override
                            public void onFalie() {

                            }
                        });
                break;
            default:
        }

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

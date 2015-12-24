package com.xguokr.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.xguokr.bean.GroupArtcleListItem;
import com.xguokr.util.XGUtil;
import com.xguokr.view.TTextView;
import com.xguokr.xguokr.R;

public class GroupArticleFragment extends Fragment implements View.OnClickListener{

    //private WebView webView;
    private TTextView tTextView;
    private GroupArtcleListItem groupArtcleListItem;
    public static String GroupArticleFragmentArgumentKey = "GroupArticleFragmentArgumentKey";

    private FloatingActionsMenu floatingActionsMenu;
    private FloatingActionButton replyBtn;
    private FloatingActionButton thumbUp;
    private OnReplyButtonClickListener onReplyButtonClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grouparticle, container, false);
       /* webView = (WebView) v.findViewById(R.id.webview_grouparticle);
        groupArtcleListItem = new GroupArtcleListItem();
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowContentAccess(false);
        webSettings.setDefaultFixedFontSize(14);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);*/
        tTextView = (TTextView)v.findViewById(R.id.ttextview_grouparticle);
        replyBtn = (FloatingActionButton) v.findViewById(R.id.floatingactionbutton_reply_grouparticle);
        thumbUp = (FloatingActionButton) v.findViewById(R.id.floatingactionbutton_thumbup_grouparticle);
        replyBtn.setOnClickListener(this);
        thumbUp.setOnClickListener(this);

        Bundle b = getArguments();
        groupArtcleListItem = (GroupArtcleListItem) b.getSerializable("GroupArticleFragmentArgumentKey");
       // webView.loadData(groupArtcleListItem.getHtml(), "text/html; charset=UTF-8", null);
        tTextView.loadHtml(groupArtcleListItem.getHtml());

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
        onReplyButtonClickListener = (OnReplyButtonClickListener) activity;
    }catch (ClassCastException e){
        throw new ClassCastException(activity.toString() +
                " must implement GroupArticleFragment.onReplyButtonClickListener");
    }

}

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floatingactionbutton_reply_grouparticle:
                if (onReplyButtonClickListener != null) onReplyButtonClickListener.OnReplyButtonClick(groupArtcleListItem);
                break;
            case R.id.floatingactionbutton_thumbup_grouparticle:
                XGUtil.ToastUtil(getContext(), "thumbup");
                break;
            default:
        }
    }

    public interface OnReplyButtonClickListener{
        void OnReplyButtonClick(GroupArtcleListItem item);
    }
}

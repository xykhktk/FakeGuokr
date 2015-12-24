package com.xguokr.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xguokr.adapter.QuestionArticleListAdapter;
import com.xguokr.bean.QuestionArticleListItem;
import com.xguokr.bean.QuestionTag;
import com.xguokr.net.QuestionArticleListGet;
import com.xguokr.util.CacheUtil;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class QuestionArticlesListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private OnQuestionArticlesListClickListener mListener;
    private QuestionTag questionTag;
    private RecyclerView recyclerView;
    private QuestionArticleListAdapter adapter;
    private ArrayList<QuestionArticleListItem> data;
    private int limit = 20;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String CacheKey = "QuestionArticlesListFragmentCacheKey";
    private String QuestionCacheKey;
    private final String Uniqname = "QuestionArtcleListItem";
    public  static String FragmentArgumentKey = "QuestionArticlesListFragmentArgumentKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_questionarticleslist, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.SwipeRefreshLayout_questionarticlelist);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue_ff, R.color.red);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));


        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_questionarticlelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        data = new ArrayList<>();
        adapter = new QuestionArticleListAdapter(getContext(),data, new QuestionArticleListAdapter.onItemClick() {
            @Override
            public void QuestionArticleListClick(int position) {
                if(mListener != null) mListener.onQuestionArticlesListClick(data.get(position));
            }
        }, new QuestionArticleListAdapter.onLastItemListener() {
            @Override
            public void onLastItem() {
                limit += 20;
                getArticlesListData();
            }
        });
        recyclerView.setAdapter(adapter);

        questionTag = (QuestionTag) getArguments().get(FragmentArgumentKey);
        QuestionCacheKey = CacheKey + "_Question_" + questionTag.getIndex();

        Object o = new CacheUtil().readList(getContext(),QuestionCacheKey,Uniqname);
        if(o != null && o instanceof ArrayList){
            data = (ArrayList<QuestionArticleListItem>) o;
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }else{
            getArticlesListData();
        }
        return v;
    }

    private void getArticlesListData(){
        swipeRefreshLayout.setRefreshing(true);
        new QuestionArticleListGet().getQuestionArticleListByOKhttp(getActivity(),questionTag.getTagName(), limit + "",

                new QuestionArticleListGet.SuccessCallback() {
                    @Override
                    public void onSuccess(ArrayList<QuestionArticleListItem> result) {
                        data = result;
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                        new CacheUtil().saveList(getContext(), QuestionCacheKey, Uniqname, data);
                        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                    }
                }, new QuestionArticleListGet.FaileCallback() {
                    @Override
                    public void onFaile(int resultCode) {
                        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                    }
                });

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnQuestionArticlesListClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnQuestionArticlesListClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        limit =20;
        getArticlesListData();
    }

    public interface OnQuestionArticlesListClickListener {
        // TODO: Update argument type and name
        public void onQuestionArticlesListClick(QuestionArticleListItem item);
    }

}

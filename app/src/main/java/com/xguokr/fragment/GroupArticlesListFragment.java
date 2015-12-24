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

import com.xguokr.adapter.GroupArticleListAdapter;
import com.xguokr.bean.GroupArtcleListItem;
import com.xguokr.bean.GroupTag;
import com.xguokr.net.GroupArticleListGet;
import com.xguokr.util.CacheUtil;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class GroupArticlesListFragment extends Fragment implements  SwipeRefreshLayout.OnRefreshListener{

    private OnGroupArticlesListClickListener mListener;
    private GroupTag groupTag;
    private RecyclerView recyclerView;
    private GroupArticleListAdapter adapter;
    private ArrayList<GroupArtcleListItem> data;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int limit = 20;

    private String CacheKey = "GroupArticlesListFragmentCacheKey";
    private String GroupCacheKey;
    private final String Uniqname = "GroupArtcleListItem";
    public  static String GroupArticlesListFragmentFragmentArgumentKey = "GroupArticlesListFragmentFragmentArgumentKey";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grouparticleslist, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.SwipeRefreshLayout_grouparticlelist);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue_ff, R.color.red);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_grouparticlelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        data = new ArrayList<>();
        adapter = new GroupArticleListAdapter(getContext(),data, new GroupArticleListAdapter.onItemClick() {
            @Override
            public void GroupArticleListClick(int position) {
                if (mListener != null) mListener.onGroupArticlesListClick(data.get(position));
            }
        }, new GroupArticleListAdapter.onLastItemListener() {
            @Override
            public void onLastItem() {
                limit += 20;
                getArticlesListData();
            }
        });
        recyclerView.setAdapter(adapter);

        groupTag = (GroupTag) getArguments().get(GroupArticlesListFragmentFragmentArgumentKey);
        GroupCacheKey = CacheKey + "_Group_" + groupTag.getGroupId();

        Object o = new CacheUtil().readList(getContext(),GroupCacheKey,Uniqname);
        if(o != null && o instanceof ArrayList){
            data = (ArrayList<GroupArtcleListItem>) o;
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }else{
            getArticlesListData();
        }
        return v;
    }

    private void getArticlesListData(){
        swipeRefreshLayout.setRefreshing(true);
        new GroupArticleListGet().getGroupArticleListByOKhttp(getActivity(), groupTag.getGroupId(), limit + "",
                new GroupArticleListGet.SuccessCallback() {
                    @Override
                    public void onSuccess(ArrayList<GroupArtcleListItem> result) {
                        // XGUtil.LogUitl(getClass().getName() + "  result:" + result.size());
                        data = result;
                        adapter.setData(data);
                        adapter.notifyDataSetChanged();
                        new CacheUtil().saveList(getContext(),GroupCacheKey,Uniqname,data);
                        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                    }
                }, new GroupArticleListGet.FaileCallback() {
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
            mListener = (OnGroupArticlesListClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGroupArticlesListClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        limit = 20;
        getArticlesListData();
    }

    public interface OnGroupArticlesListClickListener {
        // TODO: Update argument type and name
        public void onGroupArticlesListClick(GroupArtcleListItem item);
    }

}

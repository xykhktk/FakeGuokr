package com.xguokr.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xguokr.adapter.GroupTagAdapterRv;
import com.xguokr.bean.GroupTag;
import com.xguokr.net.GroupTagGet;
import com.xguokr.util.CacheUtil;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class GroupTagListFragment extends android.support.v4.app.Fragment implements  SwipeRefreshLayout.OnRefreshListener{

    private OnGroupTagListClickListener mGroupTagListClickListener;
    private int currentPage = 1;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<GroupTag> data;
    private ArrayList<GroupTag> newData;
    private GroupTagAdapterRv adapter;
    private boolean isInit = true;
    private boolean noMore = false;

    private final String DiskLruCache_Key = "GroupTagList";
    private final String DiskLruCache_UniqueName = "GroupTag";

    public GroupTagListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grouptaglist, container, false);

        data = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshLayout_fragment_grouptaglist);
        swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_fragment_grouptaglist);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4,GridLayoutManager.VERTICAL,false));

        adapter = new GroupTagAdapterRv(getContext(),data, new GroupTagAdapterRv.onGroupTagClick() {
            @Override
            public void groupTagClick(int position) {
                if(mGroupTagListClickListener != null) mGroupTagListClickListener.onGroupTagListClick( data.get(position));
                //XGUtil.ToastUtil(getContext(),data.get(position).getGroupName());
            }
        }, new GroupTagAdapterRv.onLastItemCAllback() {
            @Override
            public void LastItemCAllback() {
                loadmore();
            }
        });
        recyclerView.setAdapter(adapter);

        Object o = new CacheUtil().readList(getContext(),DiskLruCache_Key,DiskLruCache_UniqueName);
        if(o != null &&  o instanceof ArrayList){
            data = (ArrayList<GroupTag>) o;
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }else{
            getGroupTag();
        }

        return v;
    }

    private void getGroupTag() {
        swipeRefreshLayout.setRefreshing(true);

        new GroupTagGet().getGroupTagByOkHttp(currentPage,getActivity(), new GroupTagGet.SuccessCallback() {
            @Override
            public void onSuccess(ArrayList<GroupTag> list) {
                if (list.size() < 50){
                    noMore = true;
                }

                if(isInit){
                    data = list;
                    isInit = false;
                }else{
                    data.addAll(data.size(),list);
                }
                new CacheUtil().saveList(getContext(),DiskLruCache_Key,DiskLruCache_UniqueName,data);
                adapter.setData(data);
                adapter.notifyDataSetChanged();
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            }
        }, new GroupTagGet.FailCallback() {
            @Override
            public void onFail(int errorCode) {
                XGUtil.ToastUtil(getContext(),"错误 " + errorCode);
            }
        });


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mGroupTagListClickListener = (OnGroupTagListClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnGroupTagListClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mGroupTagListClickListener = null;
        if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

    private void loadmore(){
        if (noMore) {
            XGUtil.ToastUtil(getContext(),"没有更多小组了");
            return;
        }
        currentPage++;
        getGroupTag();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        isInit = true;
        getGroupTag();
    }

    public interface OnGroupTagListClickListener {
        public void onGroupTagListClick(GroupTag tag);
    }

}

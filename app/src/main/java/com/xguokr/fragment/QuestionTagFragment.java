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

import com.xguokr.adapter.QuestionTagAdapterRv;
import com.xguokr.bean.QuestionTag;
import com.xguokr.net.QuestionTagGet;
import com.xguokr.util.CacheUtil;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class QuestionTagFragment extends Fragment
		implements SwipeRefreshLayout.OnRefreshListener{
	
	private ArrayList<QuestionTag> data;
	//private ArrayList<QuestionTag> newData;
	private int currentPage = 1;
	private boolean isInit = true;

	private QuestionTagAdapterRv adapter;
	private RecyclerView recyclerView;
	private SwipeRefreshLayout swipeRefreshLayout;
	private OnGroupTagListClickListener onGroupTagListClickListener;

	private final String DiskLruCache_Key = "QuestionTagList";
	private final String DiskLruCache_UniqueName = "QuestionTag";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_questiontag, container, false);

			swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshLayout_layout_questiontagfragment);
			swipeRefreshLayout.setColorSchemeResources(R.color.blue_ff, R.color.red);
			swipeRefreshLayout.setOnRefreshListener(this);
			swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
					.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
							.getDisplayMetrics()));

			recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_layout_questiontagfragment);
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

		data = new ArrayList<>();
		//newData = new ArrayList<>();
		adapter = new QuestionTagAdapterRv(getActivity(),data, new QuestionTagAdapterRv.onQuestionTagClick() {
			@Override
			public void questionTagClick(int position) {
				//Toast.makeText(getContext(),"tag:" + data.get(position).getTagName(),Toast.LENGTH_SHORT).show();
				if (onGroupTagListClickListener != null) onGroupTagListClickListener.onGroupTagListClick(data.get(position));
			}
		},new QuestionTagAdapterRv.onLastItemCAllback(){
			@Override
			public void LastItemCAllback() {
				loadmore();
			}
		});

		recyclerView.setAdapter(adapter);

		Object o =  new CacheUtil().readList(getContext(),DiskLruCache_Key,DiskLruCache_UniqueName);
		if (o != null  && o instanceof ArrayList){
			data = (ArrayList<QuestionTag>) o;
			adapter.setData(data);
			adapter.notifyDataSetChanged();
		}else{
			getQuestionTag();
		}
		return v;
	}


	private void getQuestionTag() {
		// TODO Auto-generated method stub
		swipeRefreshLayout.setRefreshing(true);

		new QuestionTagGet().getQuestionTagByOkhttp(currentPage,getActivity(), new QuestionTagGet.SuccessCallback() {

			@Override
			public void onSuccess(ArrayList<QuestionTag> list) {
				//newData = list;
				if (isInit) {
					//data.clear();
					data = list;
					isInit = false;
				}else{
					data.addAll(list);
				}
				adapter.setData(data);
				adapter.notifyDataSetChanged();
				new CacheUtil().saveList(getContext(),DiskLruCache_Key,DiskLruCache_UniqueName,data);
				swipeRefreshLayout.setRefreshing(false);
			}
		}, new QuestionTagGet.FailCallback() {
			@Override
			public void onFail(int errorCode) {

			}
		});

	}

	private void loadmore(){
		if(currentPage == 3){
			XGUtil.ToastUtil(getActivity(), "没有更多问答标签了");
			return;
		}
		currentPage++;
		getQuestionTag();
	}

	@Override
	public void onRefresh() {
		isInit = true;
		currentPage = 1;
		getQuestionTag();
		//new RefreshTask().execute();
	}


	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			onGroupTagListClickListener = (OnGroupTagListClickListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(activity.getClass().getName() + " must implement OnGroupTagListClickListener");
		}

	}

	@Override
    public void onDetach() {
        super.onDetach();
        isInit = true;
		if(swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
    }

	public interface OnGroupTagListClickListener {
		void onGroupTagListClick(QuestionTag tag);
	}
}

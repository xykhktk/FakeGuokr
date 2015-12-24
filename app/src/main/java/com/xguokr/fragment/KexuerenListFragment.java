package com.xguokr.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xguokr.adapter.KexuerenListAdapterRv;
import com.xguokr.bean.ArticlelistItem;
import com.xguokr.net.KexuerenListGet;
import com.xguokr.util.CacheUtil;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class KexuerenListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


	private int currentActicleNum = 20;
	private ArrayList<ArticlelistItem> data;
	private onKexuerenListItemClickListener mOnKexuerenListItemClickListener;

	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private KexuerenListAdapterRv adapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	private boolean isInit = true;


	private final String DiskLruCache_Key = "KexuerenList";
	private final String DiskLruCache_UniqueName = "ArticlelistItem";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_kexuerenarticlelist, container, false);
		init(v);
		return v;
	}

	
	private void init(View v) {
		swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.swiperefreshLayout_kexuerenfragment);
		swipeRefreshLayout.setColorSchemeResources(R.color.blue_ff, R.color.red);
		swipeRefreshLayout.setOnRefreshListener(this);
		swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
						.getDisplayMetrics()));

		recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView_kexuerenfragment);
		layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		data = new ArrayList<>();
		adapter = new KexuerenListAdapterRv(getContext(),data, new KexuerenListAdapterRv.onKexuerenItemClickListener() {
			@Override
			public void onItemClick(int position) {
				if(mOnKexuerenListItemClickListener != null)
					mOnKexuerenListItemClickListener.onKexuerenListItemClick(data.get(position).getResourceUrl());
			}
		}, new KexuerenListAdapterRv.onLastItemListener() {
			@Override
			public void onLastItem() {
				currentActicleNum += 10;
				getArticleListAndSetToListView();
			}
		});
		recyclerView.setAdapter(adapter);

		Object o = new CacheUtil().readList(getContext(), DiskLruCache_Key, DiskLruCache_UniqueName);
		if (o != null && o instanceof ArrayList){
			data = (ArrayList<ArticlelistItem>) o;
			adapter.setData(data);
			adapter.notifyDataSetChanged();
		}else{
			getArticleListAndSetToListView();
		}

	}
	
	void getArticleListAndSetToListView(){
		swipeRefreshLayout.setRefreshing(true);

		new KexuerenListGet().getKexuerenByOkHttp(currentActicleNum,getActivity(), new KexuerenListGet.SuccessCallback() {
			@Override
			public void onSuccess(ArrayList<ArticlelistItem> list) {
				if(list.size() > 0){
					data = list;
					if (isInit) 	isInit = false;
					new CacheUtil().saveList(getContext(), DiskLruCache_Key, DiskLruCache_UniqueName, data);
					adapter.setData(data);
					adapter.notifyDataSetChanged();
					if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
			}
		}}, new KexuerenListGet.FaileCallback() {
			@Override
			public void onFaile() {
				if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
			}
		});

	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		
		try {
			mOnKexuerenListItemClickListener = (onKexuerenListItemClickListener)activity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new ClassCastException(activity.toString() + "must implementonKexuerenListItemClickListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		isInit = true;
		if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onRefresh() {
		currentActicleNum = 10;
		getArticleListAndSetToListView();
	}

	public interface onKexuerenListItemClickListener{
		void onKexuerenListItemClick(String url);
	}
	

}

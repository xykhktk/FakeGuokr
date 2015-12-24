package com.xguokr.fragment;

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

import com.xguokr.adapter.KexuerenArticleReplyAdapter;
import com.xguokr.bean.ReplyItem;
import com.xguokr.net.ArticleReplyGet;
import com.xguokr.net.ArticleReplyPost;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.view.PProgressDialog;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

public class ArticleReplyFragment extends Fragment implements  View.OnClickListener{

	//private ListView mlistview;
	private RecyclerView recyclerView;
	private ArrayList<ReplyItem> data;
	private KexuerenArticleReplyAdapter adapter;
	private String mArticleId;
	private EditText editText;
	private ImageView replyImageView;
	public static String ArticleReplyFragmentBundleKey = "ArticleReplyFragmentBundleKey";
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_articlereply, container, false);
		mArticleId = (String) getArguments().get("ArticleReplyFragmentBundleKey");
		editText = (EditText) v.findViewById(R.id.edittext_replyfragment);
		replyImageView = (ImageView) v.findViewById(R.id.imageview_replyfragment);
		editText.setHint("回复文章");
		replyImageView.setOnClickListener(this);

		//mlistview = (ListView) v.findViewById(R.id.listview_replyfragment);
		recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview_replyfragment);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

		data = new ArrayList<>();
		adapter = new KexuerenArticleReplyAdapter(getContext(),data, new KexuerenArticleReplyAdapter.onItemClickListener() {
			@Override
			public void onItemClick(int position) {

			}
		});
		recyclerView.setAdapter(adapter);


		initview();
		return v;
	}

	private void initview() {
		// TODO Auto-generated method stub
		final PProgressDialog pd = new PProgressDialog(getContext()).createDialog(getResources().getString(R.string.progressdialog_default_msg));
		pd.showPd();
		new ArticleReplyGet().getAriticleReplyByOKHttp(getActivity(),mArticleId,
				new ArticleReplyGet.SuccessCallback() {
					@Override
					public void onSuccess(ArrayList<ReplyItem> r) {
						data = r;
						adapter.setData(data);
						adapter.notifyDataSetChanged();
						pd.dismissPd();
					}
				}, new ArticleReplyGet.FaileCallback() {
					@Override
					public void onFalie() {
						pd.dismissPd();
						XGUtil.ToastUtil(getContext(),getResources().getString(R.string.toast_get_faile));
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.imageview_replyfragment:

				String content = editText.getText().toString();
				if (TextUtils.isEmpty(content)) {
					XGUtil.ToastUtil(getContext(),"回复内容为空");
					return;
				}
				editText.setText("");

				String ukey = XGUtil.SPGetStringUtil(getActivity(), Const.SPKey_Ukey);
				String token = XGUtil.SPGetStringUtil(getActivity(), Const.SPKey_Token);
				XGUtil.LogUitl(getClass().getName() + "read done : parseCookie  ukey:" + ukey + "  token:"+token);
				if (TextUtils.isEmpty(token) || TextUtils.isEmpty(ukey)){
					XGUtil.ToastUtil(getContext(),"token或token为空");
					return;
				}

				new ArticleReplyPost().postReply(getActivity(),ArticleReplyPost.PostType.ReplyKexuerenArticle,content,mArticleId,token,ukey,
						new ArticleReplyPost.SuccessCallback() {
							@Override
							public void onSuccess(ReplyItem newReply) {
								data.add(newReply);
								data.get(data.size() -1 ).setCount(data.size() + "");
								adapter.setData(data);
								adapter.notifyDataSetChanged();
							}
						}, new ArticleReplyPost.FaileCallback() {
				@Override
				public void onFalie() {
					XGUtil.ToastUtil(getContext(),"回复失败");
				}
			});

				break;
			default:

		}
	}
}

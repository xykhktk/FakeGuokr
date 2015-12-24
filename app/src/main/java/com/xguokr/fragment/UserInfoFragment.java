package com.xguokr.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xguokr.bean.UserInfo;
import com.xguokr.net.UserInfoGet;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.view.PProgressDialog;
import com.xguokr.xguokr.R;

public class UserInfoFragment extends Fragment {

	private String ukey;
	private TextView nickname ;
	private TextView date_created;
	private ImageView avatar;
	private TextView blogs_count;
	private TextView questions_count;
	private TextView posts_count;
	private TextView answers_count;
	private TextView followings_count;
	private TextView introduction;

	private PProgressDialog pd;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_userinfo, container, false);
		nickname = (TextView) v.findViewById(R.id.textview_nickname_userinfofragment);
		date_created = (TextView) v.findViewById(R.id.textview_date_created_userinfofragment);
		blogs_count = (TextView) v.findViewById(R.id.textview_blogs_count_userinfofragment);
		questions_count = (TextView) v.findViewById(R.id.textview_questions_count_userinfofragment);
		posts_count = (TextView) v.findViewById(R.id.textview_posts_count_userinfofragment);
		answers_count = (TextView) v.findViewById(R.id.textview_answers_count_userinfofragment);
		followings_count = (TextView) v.findViewById(R.id.textview_followings_count_userinfofragment);
		nickname = (TextView) v.findViewById(R.id.textview_nickname_userinfofragment);
		introduction = (TextView) v.findViewById(R.id.textview_introduction_userinfofragment);
		avatar = (ImageView) v.findViewById(R.id.imageview_userinfo_fragment);

		ukey = XGUtil.SPGetStringUtil(getActivity(), Const.SPKey_Ukey);
		XGUtil.LogUitl(getClass().getName() + "ukey" + ukey);


		getinfo();
		return v;
	}


	private void getinfo() {
		// TODO Auto-generated method stub
		pd = new PProgressDialog(getContext()).createDialog("加载个人信息中");
		pd.showPd();

		new UserInfoGet().getInfo(ukey,new  UserInfoGet.SuccessCallBack() {
			
			@Override
			public void onSuccess(UserInfo info) {
				// TODO Auto-generated method stub
				Picasso.with(getActivity()).load(info.getAvatar()).into(avatar);
				String createtime = info.getDate_created().substring(0, 16).replace("T", " ");
				String intro = 	info.getIntroduction();
				if(TextUtils.isEmpty(intro)) intro = "这家伙很懒，还没有写简介 ";
				
				nickname.setText(info.getNickname());
				date_created.setText(createtime);
				blogs_count.setText(info.getBlogs_count());
				questions_count.setText(info.getQuestions_count());
				posts_count.setText(info.getPosts_count());
				answers_count.setText(info.getAnswers_count());
				followings_count.setText(info.getFollowings_count());
				introduction.setText(intro);
				pd.dismissPd();
				XGUtil.LogUitl(getClass().getName() + " success ,should dismiss");
				//XGUtil.LogUitl(getClass().getName() + "  answers_count" + info.getAnswers_count());
				//XGUtil.LogUitl(getClass().getName() + "   introduction  " + info.getNickname());
			}
		}, new UserInfoGet.FaileCallBack() {
			
			@Override
			public void onFaile() {
				// TODO Auto-generated method stub
				pd.dismissPd();
				XGUtil.ToastUtil(getContext(), getResources().getString(R.string.toast_get_faile));
			}
		});
	}

	@Override
	public void onDetach() {
		super.onDetach();
		pd.dismissPd();
	}

}

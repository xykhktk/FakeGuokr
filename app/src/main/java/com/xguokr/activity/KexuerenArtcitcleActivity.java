package com.xguokr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.xguokr.fragment.KexuerenArticleFragment;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.xguokr.R;

public class KexuerenArtcitcleActivity extends AppCompatActivity
		implements View.OnClickListener,KexuerenArticleFragment.OnGetArticleSuccess{

	KexuerenArticleFragment KexuerenArticleFragment;
	Fragment currentFragment;

	FragmentTransaction ft;
	private Toolbar toolbar;
	private String url;
	private String articleId;
	private FloatingActionsMenu floatingActionsMenu;
	private FloatingActionButton replyBtn;
	private FloatingActionButton thumbUp;

	protected void onCreate(android.os.Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_articleactivity);
		toolbar = (Toolbar) findViewById(R.id.toolbar_KexuerenArtcitcleActivity);
		replyBtn = (FloatingActionButton) findViewById(R.id.floatingactionbutton_reply_articleacticity);
		thumbUp = (FloatingActionButton) findViewById(R.id.floatingactionbutton_thumbup_articleacticity);
		replyBtn.setOnClickListener(this);
		thumbUp.setOnClickListener(this);

		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("");

		url = getIntent().getStringExtra(Const.IntentKey_To_ArticleActivity);
		KexuerenArticleFragment = new KexuerenArticleFragment();
		Bundle bundle = new Bundle();
		bundle.putString(KexuerenArticleFragment.KexuerenArticleFragmentArgumentKey,url);
		KexuerenArticleFragment.setArguments(bundle);
		ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.framelayout_articleacticity, KexuerenArticleFragment);
		ft.commit();
		currentFragment= KexuerenArticleFragment;

	};
	
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		//return super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.articleactivitymenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}*/

	@Override
	public void setTheme(int resid) {
		if(XGUtil.SPGetStringUtil(this, Const.SPKey_Theme).equals(Const.Theme_Day)){
			resid = R.style.AppThemeDay;
		}else{
			resid = R.style.AppThemeNight;
		}
		super.setTheme(resid);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.floatingactionbutton_reply_articleacticity:
				Intent i = new Intent(this,KexuerenArticleReplyActivity.class);
				articleId = KexuerenArticleFragment.getArticleID();
				i.putExtra(Const.IntentKey_To_ArticleReplyActivity,articleId);
				startActivity(i);
				break;
			case R.id.floatingactionbutton_thumbup_articleacticity:
				XGUtil.ToastUtil(this,"thumbup");
				break;
			default:
		}
	}

	@Override
	public void gettArticleSuccess(String title) {
		assert getSupportActionBar() != null;
		getSupportActionBar().setTitle(title);
	}
}

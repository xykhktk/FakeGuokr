package com.xguokr.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xguokr.bean.KexuerenArticle;
import com.xguokr.net.KexuerenArticleGet;
import com.xguokr.util.Const;
import com.xguokr.util.XGUtil;
import com.xguokr.view.PProgressDialog;
import com.xguokr.view.TTextView;
import com.xguokr.xguokr.R;

public class KexuerenArticleFragment extends Fragment{

	private String articleUrl;
	private TextView title;
	private TextView author;
	private OnGetArticleSuccess onGetArticleSuccess;
	//private WebView webViewContent;
	private TTextView content;
	private ImageView articleImg;
	private String articleID;
	public static String KexuerenArticleFragmentArgumentKey = "KexuerenArticleFragmentArgumentKey";

	public String getArticleID() {
		return articleID;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_kexuerenarticle, container, false);
		title =(TextView) v.findViewById(R.id.textview_title_kexuerenarticlefragment);
		author = (TextView) v.findViewById(R.id.textview_author_kexuerenarticlefragment);
		//articleImg = (ImageView) v.findViewById(R.id.ImageView_kexuerenarticlefragment);
		//webViewContent = (WebView) v.findViewById(R.id.webview_content_kexuerenarticlefragment);
		content = (TTextView) v.findViewById(R.id.ttextview_content_kexuerenarticlefragment);
		articleUrl = getArguments().getString(KexuerenArticleFragmentArgumentKey);
		getArticle();

		return v;
	}

	private void getArticle() {
		final PProgressDialog pd = new PProgressDialog(getContext()).createDialog(getResources().getString(R.string.progressdialog_default_msg));
		pd.showPd();

		new KexuerenArticleGet().getKexuerenArticleByOKHttp(articleUrl,
				new KexuerenArticleGet.SuccessCallback() {

					@Override
					public void onSuccess(KexuerenArticle result) {
						// TODO Auto-generated method stub
						articleID = result.getArticleID();
						Log.i(Const.LogiTag, getClass().getName() + " ArticleID" + articleID);

						title.setText(result.getTitile());
						title.setTextSize(20);
						author.setText("作者:" + result.getAuthor());
						/*WebSettings  setting = webViewContent.getSettings();
						setting.setAllowContentAccess(false);
						setting.setDefaultFixedFontSize(12);
						setting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
						//setting.setBuiltInZoomControls(true);
						//setting.setSupportZoom(true);
						webViewContent.loadData(result.getArticleContent(), "text/html; charset=UTF-8" , null);*/
						content.loadHtml(result.getArticleContent());
						if (onGetArticleSuccess != null) onGetArticleSuccess.gettArticleSuccess(result.getTitile());
						pd.dismissPd();
					}

				}, new KexuerenArticleGet.FaileCallback() {

					@Override
					public void onFaile() {
						// TODO Auto-generated method stub
						pd.dismissPd();
						XGUtil.ToastUtil(getContext(), getResources().getString(R.string.toast_get_faile));
					}
				});
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try{
			onGetArticleSuccess = (OnGetArticleSuccess) activity;
		}catch(ClassCastException e){
			throw new ClassCastException("Activity must implement OnGetArticleSuccess.");
		}
	}

	public  interface OnGetArticleSuccess{
		void gettArticleSuccess(String title);
	}

}

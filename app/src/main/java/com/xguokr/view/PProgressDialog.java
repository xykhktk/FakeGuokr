
package com.xguokr.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xguokr.xguokr.R;

public class PProgressDialog extends Dialog {
	private Context context = null;
	private PProgressDialog pProgressDialog = null;
	
	public PProgressDialog(Context context){
		super(context);
		this.context = context;
	}
	
	public PProgressDialog(Context context, int theme) {
        super(context, theme);
		this.context = context;
    }
	
	public  PProgressDialog createDialog(String msg){

		pProgressDialog = new PProgressDialog(context, R.style.CustomProgressDialog);	//??
		//LayoutInflater inflater = LayoutInflater.from(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.customprogressdialog, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.cotent_customprogressdialog);

		ImageView image = (ImageView) v.findViewById(R.id.imageview_customprogressdialog);
		TextView tipTextView = (TextView) v.findViewById(R.id.textview_customprogressdialog);

		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.progress_round_rotate);

		image.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);

		//pProgressDialog = new PProgressDialog(context, R.style.CustomProgressDialog);	//??
		//pProgressDialog.setCancelable(false);
		pProgressDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		return pProgressDialog;
		/*pProgressDialog = new PProgressDialog(context, R.style.CustomProgressDialog);
		pProgressDialog.setContentView(R.layout.customprogressdialog);
		return pProgressDialog;*/
	}
 
	public void showPd(){
		if ( pProgressDialog ==null) createDialog("");

		pProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		WindowManager.LayoutParams param = pProgressDialog.getWindow().getAttributes();
		param.dimAmount = 0.0f;
		pProgressDialog.getWindow().setAttributes(param);
		pProgressDialog.setCanceledOnTouchOutside(false);
		pProgressDialog.show();
	}

	public void dismissPd(){
		if ( pProgressDialog !=null) pProgressDialog.dismiss();
	}
}

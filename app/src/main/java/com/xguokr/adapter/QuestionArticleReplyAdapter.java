package com.xguokr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xguokr.bean.ReplyItem;
import com.xguokr.util.NetUtil;
import com.xguokr.view.TTextView;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

/**
 * Created on 2015/12/12.
 */
public class QuestionArticleReplyAdapter extends RecyclerView.Adapter {

    private ArrayList<ReplyItem> data;
    private Context context;
    private onItemClick onItemClick;
    private boolean downloadPic = false;
    private final int Type_first_item = 0x11;//系统默认viewType是0

    public QuestionArticleReplyAdapter(Context context, ArrayList<ReplyItem> data,
                                       QuestionArticleReplyAdapter.onItemClick onItemClick) {
        super();
        this.data = data;
        this.onItemClick = onItemClick;
        this.context = context;
        downloadPic = NetUtil.isDownLoadImg(context);
    }

    public void setData(ArrayList<ReplyItem> data) {
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ) return Type_first_item;
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == Type_first_item){
            return new QARFirstItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_questionarticlerepylist_firstitem,null));
        }
        return new QARViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_questionarticlerepylist,null),onItemClick);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == 0){
            /*WebView webview =((QARFirstItemViewHolder) holder).getWebView();
            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setJavaScriptCanOpenWindowsAutomatically(false);
            settings.setAllowContentAccess(false);
            settings.setDefaultFixedFontSize(12);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            webview.loadData(data.get(0).getHtml(), "text/html; charset=UTF-8", null);
            //webview.loadDataWithBaseURL(null,data.get(0).getHtml(),"text/html; charset=UTF-8","UTF-8",null);
            XGUtil.LogUitl(getClass().getName() + "   webview load : " + data.get(0).getHtml());*/
            QARFirstItemViewHolder vh = (QARFirstItemViewHolder)holder;
            vh.gettTextView().loadHtml(data.get(0).getHtml());
        }else{
            QARViewHolder vh = (QARViewHolder) holder;
            if (data.get(position).getHtml().equals(context.getResources().getString(R.string.question_no_reply))){
                TextView tv = vh.getConent();
                tv.setText(context.getResources().getString(R.string.question_no_reply));
                tv.setTextSize(24);
            }else{
                vh.getPubTime().setText(data.get(position).getDate_created());
                vh.getFloor().setText((position) + "楼");
                vh.getAuthorNickname().setText(data.get(position).getAuthor_Nickename());
                //vh.getConent().setText(Html.fromHtml(data.get(position).getHtml()));
                vh.getConent().loadHtml(data.get(position).getHtml());

                if (downloadPic){
                    Picasso.with(context).load(data.get(position).getAuthor_avatar()).into(vh.getAuthorvatar());
                }else{
                    vh.getAuthorvatar().setImageResource(R.drawable.default_image);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class QARFirstItemViewHolder extends  RecyclerView.ViewHolder{
        //private WebView webView;
        private TTextView tTextView;
        public QARFirstItemViewHolder(View itemView) {
            super(itemView);
            //webView = (WebView) itemView.findViewById(R.id.webview_questionarticlereply_firstitem);
            tTextView = (TTextView) itemView.findViewById(R.id.ttextview_questionarticlereply_firstitem);
        }

        public TTextView gettTextView() {
            return tTextView;
        }
    }

    private class QARViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private ImageView authorvatar;
        private TextView authorNickname;
        private TextView pubTime;
        private TextView floor;
        // private WebView html;
        //private TextView conent;
        private TTextView conent;

        private QuestionArticleReplyAdapter.onItemClick onItemClick;

        public QARViewHolder(View itemView,QuestionArticleReplyAdapter.onItemClick onItemClick) {
            super(itemView);
            authorvatar = (ImageView) itemView.findViewById(R.id.imageview_authoravatar_cell_questionarticlereply);
            authorNickname = (TextView) itemView.findViewById(R.id.textview_authornickname_cell_questionarticlereply);
            pubTime = (TextView) itemView.findViewById(R.id.textview_pubtime_cell_questionarticlereply);
            floor = (TextView) itemView.findViewById(R.id.textview_floor_cell_questionarticlereply);
            conent = (TTextView) itemView.findViewById(R.id.textview_content_cell_questionarticlereply);

            this.onItemClick = onItemClick;
            itemView.setOnClickListener(this);
        }

        public ImageView getAuthorvatar() {
            return authorvatar;
        }

        public TextView getAuthorNickname() {
            return authorNickname;
        }

        public TextView getPubTime() {
            return pubTime;
        }

        public TextView getFloor() {
            return floor;
        }

        public TTextView getConent() {
            return conent;
        }

        @Override
        public void onClick(View v) {
            if (onItemClick != null) onItemClick.QuestionArticleReplyListClick(getAdapterPosition());
        }
    }

    public interface  onItemClick{
        void QuestionArticleReplyListClick(int position);
    }
}

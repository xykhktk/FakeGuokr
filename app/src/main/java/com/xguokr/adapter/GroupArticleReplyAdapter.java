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
 * Created by yk on 2015/12/13.
 */
public class GroupArticleReplyAdapter extends RecyclerView.Adapter {

    private ArrayList<ReplyItem> data;
    private Context context;
    private onItemClickListener onItemClickListener;
    private boolean downloadPic = false;

    public void setData(ArrayList<ReplyItem> data) {
        this.data = data;
    }

    public GroupArticleReplyAdapter(Context context, ArrayList<ReplyItem> data, GroupArticleReplyAdapter.onItemClickListener onItemClickListener) {
        super();
        this.data = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        downloadPic = NetUtil.isDownLoadImg(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //XGUtil.LogUitl(getClass().getName() + "  onCreateViewHolder" );
        return new GARViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_grouparticlereplylist,null),onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final GARViewHolder vh = (GARViewHolder) holder;
        vh.getAuthorNickname().setText(data.get(position).getAuthor_Nickename());
        vh.getFloor().setText((position + 1) + "楼");
        vh.getPubTime().setText(data.get(position).getDate_created());
        vh.getHtml().loadHtml(data.get(position).getHtml());

        /*WebView webView= vh.getHtml();
        WebSettings setting = webView.getSettings();
        setting.setAllowContentAccess(false);
        setting.setDefaultFixedFontSize(14);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.loadData(data.get(position).getHtml(), "text/html;charset=utf-8", null);*/

        if (downloadPic){
            Picasso.with(context).load(data.get(position).getAuthor_avatar()).into(vh.getAuthorvatar());
        }else{
            vh.getAuthorvatar().setImageResource(R.drawable.default_image);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class GARViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView authorvatar;
        private TextView authorNickname;
        private TextView pubTime;
        private TextView floor;
        // private WebView html;
        //private TextView html;
        private TTextView html;
        private GroupArticleReplyAdapter.onItemClickListener onItemClickListener;

        public GARViewHolder(View itemView,GroupArticleReplyAdapter.onItemClickListener onItemClickListener) {
            super(itemView);
            this.onItemClickListener = onItemClickListener;
            authorvatar = (ImageView) itemView.findViewById(R.id.imageview_authoravatar_cell_grouparticlereply);
            authorNickname = (TextView) itemView.findViewById(R.id.textview_authornickname_cell_grouparticlereply);
            pubTime = (TextView) itemView.findViewById(R.id.textview_pubtime_cell_grouparticlereply);
            floor = (TextView) itemView.findViewById(R.id.textview_floor_cell_grouparticlereply);
            //html = (WebView) itemView.findViewById(R.id.webview_content_cell_grouparticlereply);
            html = (TTextView) itemView.findViewById(R.id.textview_content_cell_grouparticlereply);
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

        public TTextView getHtml() {
            return html;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
}

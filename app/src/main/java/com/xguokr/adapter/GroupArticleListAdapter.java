package com.xguokr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xguokr.bean.GroupArtcleListItem;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

/**
 * Created by yk on 2015/12/12.
 */
public class GroupArticleListAdapter extends RecyclerView.Adapter {

    private ArrayList<GroupArtcleListItem> data;
    private Context context;
    private onItemClick onItemClick;
    private onLastItemListener onLastItemListener;

    public GroupArticleListAdapter(Context context,ArrayList<GroupArtcleListItem> data,
                                   GroupArticleListAdapter.onItemClick onItemClick,GroupArticleListAdapter.onLastItemListener onLastItemListener) {
        super();
        this.data = data;
        this.onItemClick = onItemClick;
        this.context = context;
        this.onLastItemListener = onLastItemListener;
    }

    public void setData(ArrayList<GroupArtcleListItem> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GALViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_grouparticlelist,null),onItemClick);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GALViewHolder vh = (GALViewHolder) holder;
        ((GALViewHolder) holder).getTitle().setText(data.get(position).getTitle());
        ((GALViewHolder) holder).getAuthor().setText("作者：" + data.get(position).getAuthorNickename());
        ((GALViewHolder) holder).getPubtime().setText(data.get(position).getDate_created());
        if (position == getItemCount() -1 ){
            if (onLastItemListener != null ) onLastItemListener.onLastItem();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class GALViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private TextView title;
        private TextView author;
        private TextView pubtime;
        private GroupArticleListAdapter.onItemClick onItemClick;

        public GALViewHolder(View itemView,GroupArticleListAdapter.onItemClick onItemClick) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.textview_title_cell_grouparticlelist);
            author = (TextView) itemView.findViewById(R.id.textview_author_cell_grouparticlelist);
            pubtime = (TextView) itemView.findViewById(R.id.textview_pubtime_cell_grouparticlelist);
            this.onItemClick = onItemClick;
            itemView.setOnClickListener(this);
        }

        public TextView getTitle() {
            return title;
        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getPubtime() {
            return pubtime;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public void setAuthor(TextView author) {
            this.author = author;
        }

        public void setPubtime(TextView pubtime) {
            this.pubtime = pubtime;
        }

        @Override
        public void onClick(View v) {
            if (onItemClick != null) onItemClick.GroupArticleListClick(getAdapterPosition());
        }
    }

    public interface  onItemClick{
        void GroupArticleListClick(int position);
    }

    public interface onLastItemListener {
        public void onLastItem();
    }
}

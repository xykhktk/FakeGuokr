package com.xguokr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xguokr.bean.QuestionArticleListItem;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

/**
 * Created on 2015/12/12.
 */
public class QuestionArticleListAdapter extends RecyclerView.Adapter {

    private ArrayList<QuestionArticleListItem> data;
    private Context context;
    private onItemClick onItemClick;
    private onLastItemListener onLastItemListener;

    public QuestionArticleListAdapter(Context context, ArrayList<QuestionArticleListItem> data,
                                      QuestionArticleListAdapter.onItemClick onItemClick,QuestionArticleListAdapter.onLastItemListener onLastItemListener) {
        super();
        this.data = data;
        this.onItemClick = onItemClick;
        this.context = context;
        this.onLastItemListener = onLastItemListener;
    }

    public void setData(ArrayList<QuestionArticleListItem> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QALViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_questionarticlelist,null),onItemClick);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QALViewHolder vh = (QALViewHolder) holder;
        ((QALViewHolder) holder).getQuestion().setText(data.get(position).getQuestion());
        ((QALViewHolder) holder).getAuthor().setText("作者：" + data.get(position).getAuthor_nickename());
        ((QALViewHolder) holder).getPubtime().setText(data.get(position).getDate_created());
        if (position == getItemCount() -1){
            if (onLastItemListener != null) onLastItemListener.onLastItem();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class QALViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private TextView question;
        private TextView author;
        private TextView pubtime;
        private QuestionArticleListAdapter.onItemClick onItemClick;

        public QALViewHolder(View itemView,QuestionArticleListAdapter.onItemClick onItemClick) {
            super(itemView);
            question = (TextView) itemView.findViewById(R.id.textview_title_cell_questionarticlelist);
            author = (TextView) itemView.findViewById(R.id.textview_author_cell_questionarticlelist);
            pubtime = (TextView) itemView.findViewById(R.id.textview_pubtime_cell_questionarticlelist);
            this.onItemClick = onItemClick;
            itemView.setOnClickListener(this);
        }

        public TextView getQuestion() {
            return question;
        }

        public TextView getAuthor() {
            return author;
        }

        public TextView getPubtime() {
            return pubtime;
        }

        @Override
        public void onClick(View v) {
            if (onItemClick != null) onItemClick.QuestionArticleListClick(getAdapterPosition());
        }
    }

    public interface  onItemClick{
        void QuestionArticleListClick(int position);
    }

    public interface onLastItemListener {
        public void onLastItem();
    }
}

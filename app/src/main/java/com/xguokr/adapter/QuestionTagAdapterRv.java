package com.xguokr.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xguokr.bean.QuestionTag;
import com.xguokr.util.NetUtil;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

/**
 * Created by yk on 2015/12/7.
 */
public class QuestionTagAdapterRv extends RecyclerView.Adapter {

    private ArrayList<QuestionTag> data;
    private Context context;
    private QuestionTagAdapterRv.onQuestionTagClick onQuestionTagClick;
    private onLastItemCAllback onLastItemCAllback;
    private boolean downloadPic = false;

    public void setData(ArrayList<QuestionTag> data) {
        this.data = data;
    }

    public ArrayList<QuestionTag> getData() {
        return data;
    }

    public void addData(ArrayList<QuestionTag> data) {
        this.data.addAll(data.size(),data);
    }

    public QuestionTagAdapterRv() {
        super();
        data = new ArrayList<>();
    }

    public QuestionTagAdapterRv( Context context,ArrayList<QuestionTag> data,
                                 QuestionTagAdapterRv.onQuestionTagClick onQuestionTagClick,onLastItemCAllback onLastItemCAllback) {
        super();
        data = new ArrayList<>();
        this.data = data;
        this.context = context;
        this.onQuestionTagClick = onQuestionTagClick;
        this.onLastItemCAllback = onLastItemCAllback;
        downloadPic = NetUtil.isDownLoadImg(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuestionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_questiontagrv,null),onQuestionTagClick);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QuestionVH vh = (QuestionVH) holder;
        vh.getIndex().setText("" + (position + 1));
        vh.getTagTitle().setText(data.get(position).getTagName());
        vh.getNumber().setText(data.get(position).getTagNum());
        vh.getDesc().setText(data.get(position).getTagDesc());
        if (downloadPic){
            Picasso.with(context).load(data.get(position).getTagImgUrl()).into(vh.getImageView());
        }else{
            vh.getImageView().setImageResource(R.drawable.default_image);
        }

        if (getItemCount() - 1 == position){
            onLastItemCAllback.LastItemCAllback();
            //XGUtil.LogUitl(getClass().getName() + "LastItemCAllback");
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private class QuestionVH extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private CardView cardView;
        private TextView index;
        private ImageView imageView;
        private TextView tagTitle;
        private TextView number;
        private TextView desc;
        private QuestionTagAdapterRv.onQuestionTagClick onQuestionTagClick;

        public QuestionVH(View itemView , QuestionTagAdapterRv.onQuestionTagClick onQuestionTagClick) {
            super(itemView);
            index = (TextView) itemView.findViewById(R.id.textview_index_cell_questiontag);
            imageView = (ImageView) itemView.findViewById(R.id.imageview_cell_questiontagrv);
            tagTitle = (TextView) itemView.findViewById(R.id.textview_tagname_cell_questiontagrv);
            number = (TextView) itemView.findViewById(R.id.textview_tagnum_cell_questiontagrv);
            desc =  (TextView) itemView.findViewById(R.id.textview_tagdesc_cell_questiontagrv);
            cardView = (CardView) itemView.findViewById(R.id.cardview_cell_questiontagrv);
            this.onQuestionTagClick = onQuestionTagClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onQuestionTagClick != null){
                onQuestionTagClick.questionTagClick(getPosition());
            }
        }

        public TextView getIndex() {
            return index;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTagTitle() {
            return tagTitle;
        }

        public TextView getNumber() {
            return number;
        }

        public TextView getDesc() {
            return desc;
        }
    }

    public  interface onQuestionTagClick{
        void  questionTagClick(int position);
    }

    public interface onLastItemCAllback{
        void LastItemCAllback();
    }
}

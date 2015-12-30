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
import com.xguokr.bean.ArticlelistItem;
import com.xguokr.util.NetUtil;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

/**
 * Created by yk on 2015/12/5.
 */
public class KexuerenListAdapterRv extends RecyclerView.Adapter {

    ArrayList<ArticlelistItem> data;
    Context context;
    onKexuerenItemClickListener onItemClickListener;
    onLastItemListener onLastItemListener;
    private boolean downloadPic = false;

    public void setData(ArrayList<ArticlelistItem> data) {
        this.data = data;
    }

    public void addData(ArrayList<ArticlelistItem> data){
        this.data.addAll(this.data.size(),data);
    }

    public KexuerenListAdapterRv() {
        super();
        data = new ArrayList<>();
    }

    public KexuerenListAdapterRv(Context context, ArrayList<ArticlelistItem> data,
                                 onKexuerenItemClickListener onItemClickListener,
                                 onLastItemListener onLastItemListener) {
        super();
        data = new ArrayList<>();
        this.data = data;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.onLastItemListener = onLastItemListener;
        downloadPic = NetUtil.isDownLoadImg(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new rvViewHolde(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_articlelistitem, null), onItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        rvViewHolde viewHolde = (rvViewHolde) holder;
        viewHolde.getTextView().setText(data.get(position).getTitle());

        if (downloadPic){
            Picasso.with(context).load(data.get(position).getTitleImageUrl()).into(viewHolde.getImageView());
        }else{
            viewHolde.getImageView().setImageResource(R.drawable.default_image);
        }
        //XGUtil.LogUitl("posi" + position + "getItemCount" + getItemCount());
        if (position == (getItemCount() -1 )){
            if(onLastItemListener != null)
                onLastItemListener.onLastItem();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class rvViewHolde extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private ImageView imageView;
        private TextView textView;
        private onKexuerenItemClickListener onItemClickListener;

        public rvViewHolde(View itemView, onKexuerenItemClickListener onItemClickListener) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardview_articlelistitem);
            imageView = (ImageView) itemView.findViewById(R.id.image_articlelistitem);
            textView = (TextView) itemView.findViewById(R.id.textview_articlelistitem);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        public CardView getCardView() {
            return cardView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getTextView() {
            return textView;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) onItemClickListener.onItemClick(getPosition());
        }
    }

    public interface onKexuerenItemClickListener {
        public void onItemClick(int position);
    }

    public interface onLastItemListener {
        public void onLastItem();
    }

}

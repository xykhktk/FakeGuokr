package com.xguokr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xguokr.bean.GroupTag;
import com.xguokr.xguokr.R;

import java.util.ArrayList;

/**
 * Created by yk on 2015/12/7.
 */
public class GroupTagAdapterRv extends RecyclerView.Adapter {

    private ArrayList<GroupTag> data;
    private Context context;
    private GroupTagAdapterRv.onGroupTagClick onGroupTagClick;
    private onLastItemCAllback onLastItemCAllback;

    public void setData(ArrayList<GroupTag> data) {
        //this.data.clear();
        this.data = data;
    }

    public ArrayList<GroupTag> getData() {
        return data;
    }

    public void addData(ArrayList<GroupTag> data) {
        this.data.addAll(data.size(),data);
    }

    public GroupTagAdapterRv() {
        super();
        data = new ArrayList<>();
    }

    public GroupTagAdapterRv(Context context, ArrayList<GroupTag> data,
                             GroupTagAdapterRv.onGroupTagClick onGroupTagClick, onLastItemCAllback onLastItemCAllback) {
        super();
        data = new ArrayList<>();
        this.data = data;
        this.context = context;
        this.onGroupTagClick = onGroupTagClick;
        this.onLastItemCAllback = onLastItemCAllback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GroupVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_grouptag,null),onGroupTagClick);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GroupVH vh = (GroupVH) holder;
        vh.getGroupName().setText(data.get(position).getGroupName());
        vh.getIndex().setText(data.get(position).getRank_num_top());
        vh.getMembers().setText(data.get(position).getGroupMemberNum());
        Picasso.with(context).load(data.get(position).getTagImageUrl()).into(vh.getImageView());
        if (getItemCount() - 1 == position){
            onLastItemCAllback.LastItemCAllback();
            //XGUtil.LogUitl(getClass().getName() + "LastItemCAllback");
        }
        //XGUtil.LogUitl(data.get(position).getRank_num_top());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    private class GroupVH extends RecyclerView.ViewHolder implements  View.OnClickListener{

        private ImageView imageView;
        private TextView index;
        private TextView groupName;
        private TextView members;
        private GroupTagAdapterRv.onGroupTagClick onGroupTagClick;

        public GroupVH(View itemView, GroupTagAdapterRv.onGroupTagClick onGroupTagClick) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview_cell_grouptag);
            index = (TextView) itemView.findViewById(R.id.textview_index_cell_grouptag);
            groupName = (TextView) itemView.findViewById(R.id.textview_groupname_cell_grouptag);
            members =  (TextView) itemView.findViewById(R.id.textview_members_cell_grouptag);
            this.onGroupTagClick = onGroupTagClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onGroupTagClick != null){
                onGroupTagClick.groupTagClick(getPosition());
            }
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getIndex() {
            return index;
        }

        public TextView getGroupName() {
            return groupName;
        }

        public TextView getMembers() {
            return members;
        }
    }

    public  interface onGroupTagClick{
        void  groupTagClick(int position);
    }

    public interface onLastItemCAllback{
        void LastItemCAllback();
    }
}

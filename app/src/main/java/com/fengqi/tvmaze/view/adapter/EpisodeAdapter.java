package com.fengqi.tvmaze.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengqi.tvmaze.R;
import com.fengqi.tvmaze.model.Episode;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by fengqi on 2017-08-16.
 */

public class EpisodeAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;
    private List<Episode> mData;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mClickListener = null;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }

    public EpisodeAdapter(Context context, List<Episode> data) {
        this.mData = data;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.episode_item, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder hdr = (ViewHolder)holder;
        holder.itemView.setTag(position);

        Episode ep = mData.get(position);

        Picasso.with(mContext).load(ep.getIcon()).into(hdr.mImgIcon);

        hdr.mTextTitle.setText(ep.name);
        hdr.mTextTime.setText(ep.getTime());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mClickListener != null) {
            mClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_icon)
        ImageView mImgIcon;
        @BindView(R.id.text_title)
        TextView mTextTitle;
        @BindView(R.id.text_time)
        TextView mTextTime;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
}

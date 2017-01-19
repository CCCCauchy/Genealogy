package com.oraro.genealogy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oraro.genealogy.R;
import com.oraro.genealogy.constant.Constant;
import com.oraro.genealogy.data.entity.Decision;


/**
 * Created by Administrator on 2016/10/19.
 */
public class DecisionAdapter extends BaseListAdapter<Decision> {
    private final String TAG = this.getClass().getSimpleName();
    public DecisionAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new DecisionViewHolder(mInflater.inflate(R.layout.decision_list_item_layout, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        Log.i(TAG, "onBindDefaultViewHolder.."+position);
        DecisionViewHolder holder = (DecisionViewHolder) h;
        Decision item = items.get(position);
        holder.decisionTitle.setText(item.getTitle());
        holder.decisionContent.setText(item.getContent());
        if (item.getState() == Constant.NEED_APPROVE) {
            holder.decisionImageView.setImageResource(R.mipmap.not_approved);
        } else if (item.getState() == Constant.IN_PROGRESS) {
            holder.decisionImageView.setImageResource(R.mipmap.in_progress);
        } else if (item.getState() == Constant.CLOSED) {
            holder.decisionImageView.setImageResource(R.mipmap.closed);
        } else {
            holder.decisionImageView.setImageResource(R.mipmap.default_photo);
        }
    }

    public static final class DecisionViewHolder extends RecyclerView.ViewHolder {
        ImageView decisionImageView;
        TextView decisionTitle;
        TextView decisionContent;

        public DecisionViewHolder(View itemView) {
            super(itemView);
            decisionImageView = (ImageView) itemView.findViewById(R.id.decision_list_image);
            decisionTitle = (TextView) itemView.findViewById(R.id.decision_list_title);
            decisionContent = (TextView) itemView.findViewById(R.id.decision_list_content);
        }
    }
}

package com.oraro.genealogy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.Decision;

/**
 * Created by Administrator on 2016/11/11.
 */
public class VoteVerifyAdapter extends BaseListAdapter<Decision> {
    public VoteVerifyAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new VoteVerifyViewHolder(mInflater.inflate(R.layout.vote_verify_list_item_layout, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        VoteVerifyViewHolder holder = (VoteVerifyViewHolder) h;
        Decision item = items.get(position);
        holder.decisionTitle.setText(item.getTitle());
        holder.decisionContent.setText(item.getContent());
    }

    class VoteVerifyViewHolder extends RecyclerView.ViewHolder {
        TextView decisionTitle;
        TextView decisionContent;

        public VoteVerifyViewHolder(View itemView) {
            super(itemView);
            decisionTitle = (TextView) itemView.findViewById(R.id.vote_verify_title);
            decisionContent = (TextView) itemView.findViewById(R.id.vote_verify_content);
        }
    }
}

package com.oraro.genealogy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.User;

/**
 * Created by Administrator on 2016/11/15.
 */
public class VoteGroupAdapter extends BaseListAdapter<User> {
    public VoteGroupAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return new VoteGroupViewHolder(mInflater.inflate(R.layout.vote_group_item_layout, parent, false));
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {
        VoteGroupViewHolder voteGroupViewHolder = (VoteGroupViewHolder) h;
        User user = items.get(position);
        voteGroupViewHolder.username.setText(user.getGenealogyName());
        String roleName = null;
        for (String string: user.getRoleName()) {
            if (roleName == null) {
                roleName = string;
            } else {
                roleName = roleName+"、"+string;
            }
        }
        Log.i("sysout","name="+user.getGenealogyName()+",rolename="+roleName);
//        if (user.getRole() == Constant.PATRIARCH) {
//            roleName = "族长";
//        } else if (user.getRole() == Constant.ELDER) {
//            roleName = "长老";
//        } else if (user.getRole() == Constant.SECRETARY) {
//            roleName = "长老秘书";
//        } else {
//            roleName = "族员";
//        }
        voteGroupViewHolder.role.setText(roleName);
    }

    class VoteGroupViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView role;

        public VoteGroupViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.vote_group_item_username);
            role = (TextView) itemView.findViewById(R.id.vote_group_item_role);
        }
    }
}

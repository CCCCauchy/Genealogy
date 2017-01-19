package com.oraro.genealogy.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.oraro.genealogy.data.entity.Commonweal;

/**
 * Created by Administrator on 2016/11/7.
 */
public class CommonwealAdapter extends BaseListAdapter<Commonweal> {
    public CommonwealAdapter(Context context, int mode) {
        super(context, mode);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateDefaultViewHolder(ViewGroup parent, int type) {
        return null;
    }

    @Override
    protected void onBindDefaultViewHolder(RecyclerView.ViewHolder h, int position) {

    }
}

package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.mvp.presenter.VoteGroupPresenter;
import com.oraro.genealogy.ui.adapter.BaseListAdapter;
import com.oraro.genealogy.ui.adapter.VoteGroupAdapter;
import com.oraro.genealogy.ui.view.ErrorLayout;
import com.oraro.genealogy.ui.view.tag.TagBaseAdapter;
import com.oraro.genealogy.ui.view.tag.TagCloudLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class VoteGroupActivity extends BasicActivity<VoteGroupPresenter> implements TagCloudLayout.TagItemClickListener, ErrorLayout.OnActiveClickListener, BaseListAdapter.OnLoadingListener, BaseListAdapter.OnLoadingHeaderCallBack {
    private final String TAG = this.getClass().getSimpleName();
    private EditText voteGroupEdit;
    private TagBaseAdapter tagBaseAdapter;
    private TagCloudLayout voteGroupTagLayout;
    private RecyclerView userList;
    private ErrorLayout mErrorLayout;

    private int mCurrentPage = 0;
    private int mCurrentTagPostion = 0;
    private BaseListAdapter<User> mUserAdapter;


    List<String> voteGroupTagList;


    @Override
    public VoteGroupPresenter createPresenter() {
        return new VoteGroupPresenter();
    }

    @Override
    public BasicActivity getUi() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_group);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        voteGroupEdit = (EditText) findViewById(R.id.vote_group_edit);
        voteGroupTagLayout = (TagCloudLayout) findViewById(R.id.vote_group_tag);
        userList = (RecyclerView) findViewById(R.id.vote_group_list);
        mErrorLayout = (ErrorLayout) findViewById(R.id.vote_group_error_frame);

        voteGroupEdit.clearFocus();

        voteGroupTagList = new ArrayList<>();
        voteGroupTagList.add("全体成员");
        voteGroupTagList.add("决策层");
        voteGroupTagList.add("长老层");
        voteGroupTagList.add("族长");
        tagBaseAdapter = new TagBaseAdapter(this, voteGroupTagList);
        voteGroupTagLayout.setAdapter(tagBaseAdapter);
        voteGroupTagLayout.setItemClickListener(this);

        mErrorLayout.setOnActiveClickListener(this);
        userList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (mUserAdapter != null) {
            userList.setAdapter(mUserAdapter);
        } else {
            mUserAdapter = new VoteGroupAdapter(this, BaseListAdapter.BOTH_HEADER_FOOTER);
            mUserAdapter.setOnLoadingListener(this);
            mUserAdapter.setOnLoadingHeaderCallBack(this);
            userList.setAdapter(mUserAdapter);
            mErrorLayout.setState(ErrorLayout.LOAD_FAILED);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.initiate_vote_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.vote_submit:
                Intent intent = new Intent();
                intent.putExtra("group_type", mCurrentTagPostion);
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void itemClick(int position) {
        mCurrentPage = 0;
        mCurrentTagPostion = position;
        voteGroupEdit.setText(voteGroupTagList.get(position));
        getPresenter().requestVoteGroup(mCurrentTagPostion, mCurrentPage, 20);
    }

    @Override
    public void onLoadActiveClick() {
        Log.i(TAG, "onLoadActiveClick");
        mErrorLayout.setState(ErrorLayout.LOADING);
        getPresenter().requestVoteGroup(mCurrentTagPostion, mCurrentPage, 20);
    }

    /**
     * 请求成功，读取缓存成功，加载数据
     *
     * @param result
     */
    public void onLoadResultData(List<User> result) {
        if (result == null) return;
        if (mCurrentPage == 0) {
            mUserAdapter.clear();
        }

        if (mUserAdapter.getDataSize() + result.size() == 0) {
            mErrorLayout.setState(ErrorLayout.EMPTY_DATA);
            mUserAdapter.setState(BaseListAdapter.STATE_HIDE);
            return;
        } else if (result.size() < 20) {
            mUserAdapter.setState(BaseListAdapter.STATE_NO_MORE);
        } else {
            mUserAdapter.setState(BaseListAdapter.STATE_LOAD_MORE);
        }
        Iterator<User> iterator = result.iterator();
        final List<User> data = mUserAdapter.getDataSet();
        while (iterator.hasNext()) {
            User obj = iterator.next();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId().equals(obj.getId())) {
                    data.set(i, obj);
                    iterator.remove();
                    break;
                }
            }
        }
        if (mCurrentPage == 0) {
            mUserAdapter.addItems(0, result);
        } else {
            mUserAdapter.addItems(result);
        }
    }

    @Override
    public void onLoading() {
        mCurrentPage++;
        mUserAdapter.setState(BaseListAdapter.STATE_LOADING);
        getPresenter().requestVoteGroup(mCurrentPage, mCurrentPage, 20);
    }

    /**
     * 加载完成!
     */
    public void onLoadFinishState(int mode) {
        mErrorLayout.setState(ErrorLayout.HIDE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderHolder(ViewGroup parent) {
        return new VoteGroupHeaderViewHolder(LayoutInflater.from(VoteGroupActivity.this).inflate(R.layout.vote_group_item_layout, parent, false));
    }

    @Override
    public void onBindHeaderHolder(RecyclerView.ViewHolder holder, int position) {
        VoteGroupHeaderViewHolder voteGroupViewHolder = (VoteGroupHeaderViewHolder) holder;
        voteGroupViewHolder.username.setText("姓名");
        voteGroupViewHolder.role.setText("身份");
    }

    class VoteGroupHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView role;

        public VoteGroupHeaderViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.vote_group_item_username);
            role = (TextView) itemView.findViewById(R.id.vote_group_item_role);
        }
    }
}

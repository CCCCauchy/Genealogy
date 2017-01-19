package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.mvp.presenter.VoteVerifyListPresenter;
import com.oraro.genealogy.ui.adapter.BaseListAdapter;
import com.oraro.genealogy.ui.adapter.VoteVerifyAdapter;
import com.oraro.genealogy.ui.view.ErrorLayout;
import com.oraro.genealogy.util.Utils;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class VoteVerifyActivity extends BasicActivity<VoteVerifyListPresenter> implements SwipeRefreshLayout.OnRefreshListener, ErrorLayout.OnActiveClickListener, BaseListAdapter.OnLoadingListener, BaseListAdapter.OnItemClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private static final String BUNDLE_STATE_REFRESH = "BUNDLE_STATE_REFRESH";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mListView;
    private ErrorLayout mErrorLayout;

    private int mCurrentPage = 0;
    private BaseListAdapter<Decision> mAdapter;

    private static final int STATE_NONE = 0;
    private static final int STATE_REFRESHING = 1;
    private static final int STATE_PRESSING = 2;// 正在下拉但还没有到刷新的状态
    private static final int STATE_CACHE_LOADING = 3;

    private static final int LOAD_MODE_DEFAULT = 1; // 默认的下拉刷新,小圆圈
    private static final int LOAD_MODE_UP_DRAG = 2; // 上拉到底部时刷新
    private static final int LOAD_MODE_CACHE = 3; // 缓存,ErrorLayout显示情况

    private int mState = STATE_NONE;

    @Override
    public VoteVerifyListPresenter createPresenter() {
        return new VoteVerifyListPresenter();
    }

    @Override
    public BasicActivity getUi() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_universal_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mListView = (RecyclerView) findViewById(R.id.list_view);
        mErrorLayout = (ErrorLayout) findViewById(R.id.error_frame);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Log.i(TAG, "onViewCreated..getRefreshable:" + getRefreshable());

        if (getRefreshable()) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
            mSwipeRefreshLayout.setColorSchemeResources(
                    R.color.swipe_refresh_first, R.color.swipe_refresh_second,
                    R.color.swipe_refresh_third, R.color.swipe_refresh_four
            );
            mSwipeRefreshLayout.setProgressBackgroundColorSchemeResource(
                    R.color.light_refresh_progress_background);
        }

        mErrorLayout.setOnActiveClickListener(this);

        mListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter = new VoteVerifyAdapter(this, BaseListAdapter.ONLY_FOOTER);
            mListView.setAdapter(mAdapter);
            mAdapter.setOnLoadingListener(this);
            mAdapter.setOnItemClickListener(this);
            mErrorLayout.setState(ErrorLayout.LOADING);
        }

        if (savedInstanceState != null) {
            if (mState == STATE_REFRESHING && getRefreshable()
                    && savedInstanceState.getInt(BUNDLE_STATE_REFRESH, STATE_NONE) == STATE_REFRESHING) {
                mSwipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(true);
                    }
                });
            }

            if (mState == STATE_CACHE_LOADING && getRefreshable()
                    && savedInstanceState.getInt(BUNDLE_STATE_REFRESH, STATE_NONE) == STATE_CACHE_LOADING) {
                mErrorLayout.setState(ErrorLayout.LOADING);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 请求成功，读取缓存成功，加载数据
     *
     * @param result
     */
    public void onLoadResultData(List<Decision> result) {
        if (result == null) return;
        Log.i(TAG, mCurrentPage + "..onLoadResultData.." + result.size());
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }

        if (mAdapter.getDataSize() + result.size() == 0) {
            mErrorLayout.setState(ErrorLayout.EMPTY_DATA);
            mAdapter.setState(BaseListAdapter.STATE_HIDE);
            return;
        } else if (result.size() < 20) {
            Log.i(TAG, mCurrentPage + "..onLoadResultData.." + result.size());
            mAdapter.setState(BaseListAdapter.STATE_NO_MORE);
        } else {
            mAdapter.setState(BaseListAdapter.STATE_LOAD_MORE);
        }
        Iterator<Decision> iterator = result.iterator();
        final List<Decision> data = mAdapter.getDataSet();
        while (iterator.hasNext()) {
            Decision obj = iterator.next();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId().equals(obj.getId())) {
                    data.set(i, obj);
                    iterator.remove();
                    break;
                }
            }
        }
        if (mCurrentPage == 0) {
            mAdapter.addItems(0, result);
        } else {
            mAdapter.addItems(result);
        }
    }


    /**
     * 设置是否可刷新
     */
    public boolean getRefreshable() {
        return true;
    }

    @Override
    public void onRefresh() {
        Log.i(TAG, "onRefresh..." + mState);
        if (mState == STATE_REFRESHING && getRefreshable())
            return;
        if (!Utils.hasInternet(this)) {
            onNetworkInvalid(LOAD_MODE_DEFAULT);
            return;
        }
        onLoadingState(LOAD_MODE_DEFAULT);
        getPresenter().requestData(LOAD_MODE_DEFAULT, 0);
    }

    @Override
    public void onLoadActiveClick() {
        Log.i(TAG, "onLoadActiveClick");
        mErrorLayout.setState(ErrorLayout.LOADING);
        getPresenter().requestData(LOAD_MODE_DEFAULT, 0);
    }

    @Override
    public void onLoading() {
        Log.i(TAG, "onLoading:" + mState);
        if (mState == STATE_REFRESHING) {
            mAdapter.setState(BaseListAdapter.STATE_REFRESHING);
            return;
        }
        mCurrentPage++;
        mAdapter.setState(BaseListAdapter.STATE_LOADING);
        getPresenter().requestData(LOAD_MODE_UP_DRAG, mCurrentPage);
    }

    /**
     * when there has not valid network
     */
    public void onNetworkInvalid(int mode) {
        Log.i(TAG, "onNetworkInvalid:" + mode);
        switch (mode) {
            case LOAD_MODE_DEFAULT:
                if (mAdapter == null || mAdapter.getDataSize() == 0) {
                    mErrorLayout.setState(ErrorLayout.NOT_NETWORK);
                    mSwipeRefreshLayout.setRefreshing(false);
                    mSwipeRefreshLayout.setEnabled(false);
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                break;
            case LOAD_MODE_UP_DRAG:
                mAdapter.setState(BaseListAdapter.STATE_INVALID_NETWORK);
                break;
        }
    }

    /**
     * 顶部加载状态
     */
    public void onLoadingState(int mode) {
        Log.i(TAG, "onLoadingState:" + mode);
        switch (mode) {
            case LOAD_MODE_DEFAULT:
                mState = STATE_REFRESHING;
                mSwipeRefreshLayout.setRefreshing(true);
                mSwipeRefreshLayout.setEnabled(false);
                break;
            case LOAD_MODE_CACHE:
                mState = STATE_CACHE_LOADING;
                mErrorLayout.setState(ErrorLayout.LOADING);
                break;
        }
    }

    /**
     * 加载完成!
     */
    public void onLoadFinishState(int mode) {
        Log.i(TAG, "onLoadFinishState:" + mode);
        switch (mode) {
            case LOAD_MODE_DEFAULT:
                mErrorLayout.setState(ErrorLayout.HIDE);
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(true);
                mState = STATE_NONE;
                break;
            case LOAD_MODE_UP_DRAG:
//                onLoadResultData was already deal with the state
//                mAdapter.setState(BaseListAdapter.STATE_LOAD_MORE);
                break;
            case LOAD_MODE_CACHE:
                mErrorLayout.setState(ErrorLayout.HIDE);
                break;
        }
    }

    /**
     * when load data broken
     */
    public void onLoadErrorState(int mode) {
        Log.i(TAG, "onLoadErrorState:" + mode);
        switch (mode) {
            case LOAD_MODE_DEFAULT:
                mSwipeRefreshLayout.setEnabled(true);
                if (mAdapter.getDataSize() > 0) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    mState = STATE_NONE;
                    Toast.makeText(this, "数据加载失败", Toast.LENGTH_SHORT).show();
                } else {
                    mErrorLayout.setState(ErrorLayout.LOAD_FAILED);
                }
                break;
            case LOAD_MODE_UP_DRAG:
                mAdapter.setState(BaseListAdapter.STATE_LOAD_ERROR);
                break;
        }
    }


    @Override
    public void onItemClick(int position, long id, View view) {
        Toast.makeText(this, mAdapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, VoteDetailActivity.class);
        intent.putExtra("form_id", mAdapter.getItem(position).getFormId())
                .putExtra("title", mAdapter.getItem(position).getTitle())
                .putExtra("content", mAdapter.getItem(position).getContent());
        startActivity(intent);
    }
}

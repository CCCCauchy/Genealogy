package com.oraro.genealogy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.Commonweal;
import com.oraro.genealogy.mvp.presenter.CommonwealListPresenter;
import com.oraro.genealogy.ui.activity.InitiateCommonwealActivity;
import com.oraro.genealogy.ui.activity.InitiateVoteActivity;
import com.oraro.genealogy.ui.activity.MainActivity;
import com.oraro.genealogy.ui.adapter.BaseListAdapter;
import com.oraro.genealogy.ui.adapter.CommonwealAdapter;

/**
 * Created by Administrator on 2016/11/7.
 */
public class CommonwealListFragment extends BaseListFragment<Commonweal, CommonwealListPresenter> {
    @Override
    protected BaseListAdapter<Commonweal> onSetupAdapter() {
        return new CommonwealAdapter(getActivity(),BaseListAdapter.ONLY_FOOTER);
    }

    @Override
    public CommonwealListPresenter createPresenter() {
        return new CommonwealListPresenter();
    }

    @Override
    public BaseFragment getUi() {
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.decision_list_menu, menu);
        menu.getItem(0).setTitle("申请");
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.family_commonweal));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.initiate_vote:
                getActivity().startActivity(new Intent(getActivity(), InitiateCommonwealActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

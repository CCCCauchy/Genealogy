package com.oraro.genealogy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.oraro.floatingbutton.FloatingActionButton;
import com.oraro.genealogy.R;
import com.oraro.genealogy.constant.Constant;
import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.mvp.presenter.DecisionListPresenter;
import com.oraro.genealogy.ui.activity.InitiateVoteActivity;
import com.oraro.genealogy.ui.activity.MainActivity;
import com.oraro.genealogy.ui.activity.VoteDetailActivity;
import com.oraro.genealogy.ui.activity.VoteVerifyActivity;
import com.oraro.genealogy.ui.adapter.BaseListAdapter;
import com.oraro.genealogy.ui.adapter.DecisionAdapter;
import com.oraro.genealogy.util.Utils;

/**
 * Created by Administrator on 2016/10/19.
 */
public class DecisionListFragment extends BaseListFragment<Decision, DecisionListPresenter> implements BaseListAdapter.OnItemClickListener, View.OnClickListener {
    private FloatingActionButton reviewDecision = null;

    @Override
    protected BaseListAdapter<Decision> onSetupAdapter() {
        return new DecisionAdapter(getActivity(), BaseListAdapter.ONLY_FOOTER);
    }

    @Override
    public DecisionListPresenter createPresenter() {
        return new DecisionListPresenter();
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView.setPadding(0, setDividerColor(), 0, 0);
        mAdapter.setOnItemClickListener(this);
        reviewDecision = (FloatingActionButton) view.findViewById(R.id.floating_button);
        if (Utils.getCurrentUser() != null && Utils.getCurrentUser().getRole() != Constant.NORMAL) {
        }
        reviewDecision.setVisibility(View.VISIBLE);
        reviewDecision.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.decision_list_menu, menu);
        ((MainActivity) getActivity()).setActionBarTitle(getActivity().getString(R.string.family_decision));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.initiate_vote:
                getActivity().startActivity(new Intent(getActivity(), InitiateVoteActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position, long id, View view) {
        Toast.makeText(getActivity(), mAdapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), VoteDetailActivity.class);
        intent.putExtra("action", "vote");
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_button:
                getActivity().startActivity(new Intent(getActivity(), VoteVerifyActivity.class));
                break;
            default:
                break;
        }
    }
}

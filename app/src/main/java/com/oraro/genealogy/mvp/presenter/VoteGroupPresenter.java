package com.oraro.genealogy.mvp.presenter;

import android.util.Log;

import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.DecisionModel;
import com.oraro.genealogy.ui.activity.VoteGroupActivity;
import com.oraro.genealogy.ui.fragment.BaseListFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class VoteGroupPresenter extends Presenter<VoteGroupActivity> implements OnLoadDataListener<List<User>> {
    private DecisionModel decisionModel;

    @Override
    public void onUiReady(VoteGroupActivity ui) {
        super.onUiReady(ui);
        decisionModel = new DecisionModel();
        decisionModel.requestVoteGroup(0, 0, 20, this);
    }

    public void requestVoteGroup(int groupType, int page, int rows) {
        decisionModel.requestVoteGroup(groupType, page, rows, this);
    }

    @Override
    public void onSuccess(List<User> data) {
        getUi().onLoadResultData(data);
        getUi().onLoadFinishState(BaseListFragment.LOAD_MODE_DEFAULT);
    }

    @Override
    public void onFailure(Throwable e) {

    }
}

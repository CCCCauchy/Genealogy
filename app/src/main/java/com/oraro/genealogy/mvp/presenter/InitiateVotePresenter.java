package com.oraro.genealogy.mvp.presenter;


import android.util.Log;
import android.widget.Toast;

import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.DecisionModel;
import com.oraro.genealogy.ui.activity.InitiateVoteActivity;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/8.
 */
public class InitiateVotePresenter extends Presenter<InitiateVoteActivity> implements OnLoadDataListener<Object> {
    private DecisionModel decisionModel;


    public InitiateVotePresenter() {
        decisionModel = new DecisionModel();
    }

    public void submitVote(Decision decision, Map<String, String> optionPictrue) {
        decisionModel.submitVote(decision, optionPictrue, this);
    }

    @Override
    public void onSuccess(Object data) {
        if (getUi() != null)
            getUi().finish();
    }

    @Override
    public void onFailure(Throwable e) {
        Log.i(this.getClass().getSimpleName(), "onFailure", e);
        if (getUi() != null) {
            getUi().dismissProgress();
            Toast.makeText(getUi(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

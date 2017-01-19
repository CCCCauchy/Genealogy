package com.oraro.genealogy.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.DecisionModel;
import com.oraro.genealogy.ui.activity.VoteDetailActivity;

/**
 * Created by Administrator on 2016/11/14.
 */
public class VoteDetailPresenter extends Presenter<VoteDetailActivity> implements OnLoadDataListener<Decision> {
    private final String TAG = this.getClass().getSimpleName();
    private DecisionModel decisionModel = null;

    @Override
    public void onUiReady(VoteDetailActivity ui) {
        super.onUiReady(ui);
        decisionModel = new DecisionModel();
        decisionModel.requestVoteVerifyDetail(ui.getFormId(), this);
    }

    public void verifyVote(int formId, String reviewedStatus) {
        decisionModel.verifyVote(formId, reviewedStatus, this);
    }

    @Override
    public void onSuccess(Decision data) {
        if (data != null) {
            if ("APPROVE".equals(data.getAction())) {
                Toast.makeText(getUi(), "Have Approved", Toast.LENGTH_SHORT).show();
                getUi().finish();
            } else if ("REJECT".equals(data.getAction())) {
                Toast.makeText(getUi(), "Have Reject", Toast.LENGTH_SHORT).show();
                getUi().finish();
            } else {
                getUi().onLoadData(data);
                for (int i = 0; i < data.getOptionText().size(); i++) {
                    if (data.getOptionPicture().containsKey(Integer.toString(i))) {
                        Log.i(TAG, "onSuccess.." + data.getOptionPicture().get(Integer.toString(i)));
                    }
                }
            }
        } else {

        }
    }

    @Override
    public void onFailure(Throwable e) {
        Log.i(TAG, "onFailure..", e);
    }
}

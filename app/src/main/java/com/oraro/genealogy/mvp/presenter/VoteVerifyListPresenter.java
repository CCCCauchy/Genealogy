package com.oraro.genealogy.mvp.presenter;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.DecisionModel;
import com.oraro.genealogy.ui.activity.VoteVerifyActivity;
import com.oraro.genealogy.ui.fragment.BaseListFragment;
import com.oraro.genealogy.ui.fragment.DecisionListFragment;
import com.oraro.genealogy.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
public class VoteVerifyListPresenter extends Presenter<VoteVerifyActivity> implements OnLoadDataListener<List<Decision>> {
    private static int j = 0;

    private DecisionModel decisionModel = null;


    @Override
    public void onUiReady(VoteVerifyActivity ui) {
        super.onUiReady(ui);
        decisionModel = new DecisionModel();
        requestData(BaseListFragment.LOAD_MODE_DEFAULT, 0);
    }

    @Override
    public void requestData(final int mode, int pageNum) {
        super.requestData(mode, pageNum);
        if (!Utils.hasInternet(getUi())) {
            getUi().onNetworkInvalid(mode);
            return;
        }
        decisionModel.requestVoteVerifyList(pageNum, 20, this);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                List<Decision> result = new ArrayList<>();
//                for (int i = 0; i < 20; i++) {
//                    Decision decision = new Decision();
//                    decision.setId((long) j);
//                    decision.setContent("content:1234567890.21354687544sdfh123654weryhv" + j);
//                    decision.setTitle("title" + j);
//                    result.add(decision);
//                    j++;
//                }
//                getUi().onLoadResultData(result);
//                getUi().onLoadFinishState(mode);
//            }
//        }, 1000);
    }

    @Override
    public void onSuccess(List<Decision> data) {
        getUi().onLoadResultData(data);
        getUi().onLoadFinishState(BaseListFragment.LOAD_MODE_DEFAULT);
        for (Decision decision : data) {
//            decision.save();
        }
    }

    @Override
    public void onFailure(Throwable e) {
        Log.i("sysout", "onFailure", e);
    }
}

package com.oraro.genealogy.mvp.model;

import android.util.Log;

import com.oraro.genealogy.constant.Constant;
import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.data.http.HttpManager;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;

import java.util.List;
import java.util.Map;

import rx.Observer;

/**
 * Created by Administrator on 2016/11/8.
 */
public class DecisionModel {


    public void submitVote(Decision decision, Map<String, String> optionPictrue, final OnLoadDataListener listener) {
        HttpManager.getInstance().submitVote(decision, optionPictrue, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(Object o) {
                listener.onSuccess(o);
            }
        });
    }

    public void requestVoteVerifyList(int page, int rows, final OnLoadDataListener listener) {
        HttpManager.getInstance().requestVoteVerifyList(page, rows, new Observer<List<Decision>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(List<Decision> decision) {
                listener.onSuccess(decision);
            }
        });
    }


    public void requestVoteVerifyDetail(int formId, final OnLoadDataListener listener) {
        HttpManager.getInstance().requestVoteVerifyDetail(formId, new Observer<Decision>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(Decision decision) {
                listener.onSuccess(decision);
            }
        });
    }

    public void verifyVote(int formId, final String reviewedStatus, final OnLoadDataListener listener) {
        HttpManager.getInstance().verifyVote(formId, reviewedStatus, new Observer<Decision>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(Decision decision) {
                Log.i("sysout", "onNext");
                if (decision == null) {
                    decision = new Decision();
                    if (Constant.APPROVE.equals(reviewedStatus)) {
                        decision.setAction("APPROVE");
                    } else if (Constant.REJECT.equals(reviewedStatus)) {
                        decision.setAction("REJECT");
                    }
                }
                listener.onSuccess(decision);
            }
        });
    }

    public void requestVoteGroup(int groupType, int page, int rows, final OnLoadDataListener listener) {
        HttpManager.getInstance().requestVoteGroup(groupType, page, rows, new Observer<List<User>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(List<User> users) {
                listener.onSuccess(users);
            }
        });
    }
}

package com.oraro.genealogy.mvp.presenter;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.ui.fragment.BaseListFragment;
import com.oraro.genealogy.ui.fragment.DecisionListFragment;
import com.oraro.genealogy.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19.
 */
public class DecisionListPresenter extends Presenter<DecisionListFragment> {
    private final String TAG = this.getClass().getSimpleName();
    private static int j = 0;

    @Override
    public void onUiReady(DecisionListFragment ui) {
        super.onUiReady(ui);
        Log.i(TAG,"onUiReady");
        ui.getView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                getUi().getView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                requestData(BaseListFragment.LOAD_MODE_DEFAULT, 0);
            }
        });

    }

    @Override
    public void requestData(final int mode, int pageNum) {
        if (!Utils.hasInternet(getUi().getActivity())) {
            getUi().onNetworkInvalid(mode);
            return;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Decision> result = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    Decision decision = new Decision();
                    decision.setId((long) j);
                    decision.setContent("content:" + j);
                    decision.setTitle("title" + j);
                    result.add(decision);
                    j++;
                }
                getUi().onLoadResultData(result);
                getUi().onLoadFinishState(mode);
            }
        }, 1000);
    }
}

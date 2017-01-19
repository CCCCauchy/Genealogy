package com.oraro.genealogy.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.oraro.genealogy.mvp.presenter.Presenter;


/**
 * Created by thanatos on 15/12/21.
 */
public abstract class BaseFragment<P extends Presenter> extends Fragment {

    private P mPresenter;

    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";

    public abstract P createPresenter();

    public abstract BaseFragment getUi();

    protected BaseFragment() {
        mPresenter = createPresenter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter.onUiReady(getUi());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUiDestroy(getUi());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.onSaveInstanceState(outState);
    }


    public P getPresenter() {
        return mPresenter;
    }

}

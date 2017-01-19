package com.oraro.genealogy.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.oraro.genealogy.R;
import com.oraro.genealogy.constant.Constant;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.ui.view.dialog.CustomProgressDialog;

/**
 * Created by Administrator on 2016/10/14.
 */
public abstract class BasicActivity<P extends Presenter> extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    protected SharedPreferences mSharedPreferences;
    private CustomProgressDialog mCustomProgressDialog;
    private P mPresenter;

    public abstract P createPresenter();

    public abstract BasicActivity getUi();

    public P getPresenter() {
        return mPresenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        if (savedInstanceState != null && mPresenter != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        }
        mSharedPreferences = this.getSharedPreferences(Constant.SHARED_PREFERENCE_NAME,
                Context.MODE_WORLD_READABLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.onUiReady(getUi());
    }

    protected String getAppVersionName() {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.i(TAG, "Exception", e);
        }
        return versionName;
    }

    public void showProgress() {
        showProgress(null);
    }

    public void showProgress(String paramString) {
        if (paramString == null) {
            paramString = getResources().getString(R.string.loading);
        }
        mCustomProgressDialog = new CustomProgressDialog(this);
        mCustomProgressDialog.showProgess(paramString);
        return;
    }

    public void dismissProgress() {
        if (this.mCustomProgressDialog == null) {
            return;
        }
        this.mCustomProgressDialog.dismiss();
        this.mCustomProgressDialog = null;
    }

    protected void hideIME() {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    protected void showIME(View view) {
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mInputMethodManager.showSoftInput(view, 0);
    }

    @Override
    protected void onDestroy() {
        dismissProgress();
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onUiDestroy(getUi());
    }
}

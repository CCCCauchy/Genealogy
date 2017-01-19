package com.oraro.genealogy.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.oraro.genealogy.data.retrofit.ApiException;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.UserModel;
import com.oraro.genealogy.ui.activity.LockActivity;
import com.oraro.genealogy.ui.activity.LockSetupActivity;
import com.oraro.genealogy.ui.activity.LoginActivity;
import com.oraro.genealogy.ui.activity.MainActivity;

/**
 * Created by Administrator on 2016/10/18.
 */
public class CheckLockPatternPresent implements OnLoadDataListener {
    private String TAG = this.getClass().getSimpleName();
    private UserModel mUserModel;
    private Context mContext = null;
    private int failureTimes = 1;

    public CheckLockPatternPresent(LockActivity activity) {
        mContext = activity;
        mUserModel = new UserModel();
    }

    public void checkLockPattern(String lockPatternString) {
        mUserModel.checkLockPattern(lockPatternString, this);
    }

    @Override
    public void onSuccess(Object data) {
        ((LockActivity) mContext).dismissProgress();
        Intent intent = null;
        if (((LockActivity) mContext).isSetGestrue()) {
            intent = new Intent(mContext, LockSetupActivity.class);
        } else {
            intent = new Intent(mContext, MainActivity.class);
        }
        mContext.startActivity(intent);
        ((LockActivity) mContext).finish();
    }

    @Override
    public void onFailure(Throwable e) {
        Log.i(TAG, "onFailure..", e);
        ((LockActivity) mContext).refershUIWhenFailure();
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        failureTimes++;
        if (failureTimes > 2 || (e instanceof ApiException && ((ApiException) e).isTokenInvalid())) {
            if (!((LockActivity) mContext).isSetGestrue()) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
            }
            ((LockActivity) mContext).finish();
        }
    }
}

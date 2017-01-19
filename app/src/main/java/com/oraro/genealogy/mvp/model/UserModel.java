package com.oraro.genealogy.mvp.model;

import android.util.Log;

import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.data.http.HttpManager;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;

import rx.Observer;

/**
 * Created by Administrator on 2016/10/10.
 */
public class UserModel {
    private final String TAG = this.getClass().getSimpleName();

    public void userLogin(String username, String password, String deviceId, String appVersion, final OnLoadDataListener listener) {
        HttpManager.getInstance().doUserLogin(username, password, deviceId, appVersion, new Observer<User>() {
            @Override
            public void onCompleted() {
                Log.e("UserModel", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("UserModel", "onError==" + e.getMessage());
                listener.onFailure(e);
            }

            @Override
            public void onNext(User user) {
                Log.i("UserModel", "user.." + user.getGenealogyName());
                listener.onSuccess(user);
            }
        });
    }

    public void resetPassword(String idCard, String phoneNum, String newPassword, final OnLoadDataListener listener) {
        HttpManager.getInstance().doFogotPassword(idCard, phoneNum, newPassword, new Observer<Object>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "resetPassword onCompleted..");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "resetPassword onError..");
                listener.onFailure(e);
            }

            @Override
            public void onNext(Object object) {
                Log.i(TAG, "resetPassword onnext.." + object);
                object = new Object();
                listener.onSuccess(object);
            }
        });
    }

    public void uploadLockPattern(String lockPatternString, final OnLoadDataListener listener) {
        HttpManager.getInstance().uploadLockPattern(lockPatternString, new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(Object o) {
                if (o == null)
                    o = new Object();
                listener.onSuccess(o);
            }
        });
    }

    public void checkLockPattern(String lockPatternString, final OnLoadDataListener listener) {
        HttpManager.getInstance().checkLockPattern(lockPatternString, new Observer<Object>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onFailure(e);
            }

            @Override
            public void onNext(Object o) {
                if (o == null)
                    o = new Object();
                listener.onSuccess(o);
            }
        });
    }
}

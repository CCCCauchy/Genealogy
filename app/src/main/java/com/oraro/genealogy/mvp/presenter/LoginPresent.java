package com.oraro.genealogy.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.data.entity.User_Table;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.UserModel;
import com.oraro.genealogy.ui.activity.LoginActivity;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

/**
 * Created by Administrator on 2016/10/10.
 */
public class LoginPresent implements OnLoadDataListener<User> {
    private String TAG = this.getClass().getSimpleName();
    private UserModel mUserModel;
    private Context mContext = null;

    public LoginPresent(LoginActivity activity) {
        mContext = activity;
        this.mUserModel = new UserModel();
    }

    public void doUserLogin(String username, String password, String deviceId, String appVersion) {
        Log.i(TAG, "doUserLogin");
        mUserModel.userLogin(username, password, deviceId, appVersion, this);
    }

    @Override
    public void onSuccess(User user) {
        Log.i(TAG, "onSuccess..." + user.getRole()+",userid="+user.getUserId());
        user.setCurrentUser(Boolean.TRUE);
        user.setHasLockSetup(false);
        user.save();
        List<User> allUser = new Select().from(User.class).where(User_Table.userId.isNot(user.getUserId())).queryList();
        for (User otherUser:allUser) {
            otherUser.setCurrentUser(Boolean.FALSE);
            otherUser.save();
            Log.i(TAG,"set "+otherUser.getGenealogyName()+" not as current");
        }
        ((LoginActivity) mContext).dismissProgress();
        ((LoginActivity) mContext).finish();
        if (user.getIsFirstLogin() == 1) {
            mContext.startActivity(new Intent("com.oraro.genealogy.locksetup"));
        } else {
            mContext.startActivity(new Intent("com.oraro.genealogy.main"));
        }
    }

    @Override
    public void onFailure(Throwable e) {
        Log.i(TAG, "onFailure" + e.getMessage());
        ((LoginActivity) mContext).dismissProgress();
        Toast.makeText(mContext,mContext.getString(R.string.login_failed),Toast.LENGTH_LONG).show();
    }
}

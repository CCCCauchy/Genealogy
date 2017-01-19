package com.oraro.genealogy.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.UserModel;
import com.oraro.genealogy.ui.activity.LockSetupActivity;
import com.oraro.genealogy.ui.activity.MainActivity;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by Administrator on 2016/10/18.
 */
public class UploadLockPatternPresent implements OnLoadDataListener {
    private String TAG = this.getClass().getSimpleName();
    private UserModel mUserModel;
    private Context mContext = null;

    public UploadLockPatternPresent(LockSetupActivity activity) {
        mContext = activity;
        mUserModel = new UserModel();
    }

    public void uploadLockPattern(String lockPatternString) {
        mUserModel.uploadLockPattern(lockPatternString, this);
    }

    @Override
    public void onSuccess(Object data) {
        User user = new Select().from(User.class).querySingle();
        user.setHasLockSetup(true);
        user.save();
        Intent intent = new Intent(mContext, MainActivity.class);
        mContext.startActivity(intent);
        ((LockSetupActivity) mContext).finish();
    }

    @Override
    public void onFailure(Throwable e) {
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}

package com.oraro.genealogy.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.oraro.genealogy.mvp.listener.OnLoadDataListener;
import com.oraro.genealogy.mvp.model.UserModel;
import com.oraro.genealogy.ui.activity.ResetPasswordActivity;

/**
 * Created by Administrator on 2016/10/17.
 */
public class ResetPasswordPresent implements OnLoadDataListener<Object> {
    private String TAG = this.getClass().getSimpleName();
    private UserModel mUserModel;
    private Context mContext = null;

    public ResetPasswordPresent(ResetPasswordActivity activity) {
        mContext = activity;
        mUserModel = new UserModel();
    }

    public void resetPassword(String idCard, String phoneNum, String newPassword){
        mUserModel.resetPassword(idCard, phoneNum, newPassword, this);
    }
    @Override
    public void onSuccess(Object data) {
        Log.i(TAG,"onSuccess."+data.toString());
        ((ResetPasswordActivity) mContext).finish();
    }

    @Override
    public void onFailure(Throwable e) {
        Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_LONG).show();
    }
}

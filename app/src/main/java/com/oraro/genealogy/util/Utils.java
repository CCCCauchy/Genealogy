package com.oraro.genealogy.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.view.inputmethod.InputMethodManager;

import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.data.entity.User_Table;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by Administrator on 2016/10/13.
 */
public class Utils {
    private static ConnectivityManager mCnnManager;
    private static InputMethodManager mSoftInputManager;

    public static ConnectivityManager getCnnManager(Context context) {
        if (mCnnManager == null)
            mCnnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return mCnnManager;
    }

    public static InputMethodManager getSoftInputManager(Context context) {
        if (mSoftInputManager == null)
            mSoftInputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return mSoftInputManager;
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 检测是否有网络
     *
     * @return
     */
    public static boolean hasInternet(Context context) {
        return getCnnManager(context).getActiveNetworkInfo() != null && getCnnManager(context).getActiveNetworkInfo().isAvailable();
    }

    /**
     * 网络类型
     *
     * @return
     */
    public static int getInternetType(Context context) {
        return getCnnManager(context).getActiveNetworkInfo().getType();
    }

    /**
     * 获取当前用户实体
     */
    public static User getCurrentUser() {
        User user = null;
        try {
            if (!new Select().from(User.class).queryList().isEmpty()) {
                user = new Select().from(User.class).where(User_Table.isCurrentUser.is(Boolean.TRUE)).querySingle();
            }
        } catch (Exception e) {
        }
        return user;
    }
}

package com.oraro.genealogy.mvp.presenter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This is a base class for all presenters. Subclasses can override
 * <p/>
 * to be notified about the need of freeing resources.
 *
 */
public abstract class Presenter<View> {
    @Nullable
    private View mUi;

    /**
     * 请求网络数据
     *
     * @param mode
     * @param pageNum
     */
    public void requestData(int mode, int pageNum){

    }


    /**
     * Called after the UI view has been created.  That is when fragment.onViewCreated() is called.
     *
     * @param ui The Ui implementation that is now ready to be used.
     */
    public void onUiReady(View ui) {
        mUi = ui;
    }

    /**
     * Called when the UI view is destroyed in Fragment.onDestroyView().
     */
    public final void onUiDestroy(View ui) {
        onUiUnready(ui);
        mUi = null;
    }

    /**
     * To be overriden by Presenter implementations.  Called when the fragment is being
     * destroyed but before ui is set to null.
     */
    public void onUiUnready(View ui) {
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    public View getUi() {
        return mUi;
    }
}

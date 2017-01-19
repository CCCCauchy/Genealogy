package com.oraro.genealogy.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.oraro.genealogy.R;
import com.oraro.genealogy.mvp.presenter.CheckLockPatternPresent;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.ui.view.LockPatternView;

import java.util.List;


public class LockActivity extends BasicActivity implements
        LockPatternView.OnPatternListener {
    private static final String TAG = "LockActivity";

    private List<LockPatternView.Cell> lockPattern;
    private LockPatternView lockPatternView;
    CheckLockPatternPresent checkLockPatternPresent;
    private boolean setGestrue = false;

    @Override
    public Presenter createPresenter() {
        return null;
    }

    @Override
    public BasicActivity getUi() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lock);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        lockPatternView.setOnPatternListener(this);
        checkLockPatternPresent = new CheckLockPatternPresent(LockActivity.this);
        setGestrue = getIntent().getBooleanExtra("set_gesture", false);

    }

    @Override
    public void onPatternStart() {
        Log.d(TAG, "onPatternStart");
    }

    @Override
    public void onPatternCleared() {
        Log.d(TAG, "onPatternCleared");
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {
        Log.d(TAG, "onPatternCellAdded");
        Log.e(TAG, LockPatternView.patternToString(pattern));
        // Toast.makeText(this, LockPatternView.patternToString(pattern),
        // Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        Log.d(TAG, "onPatternDetected");

        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            Toast.makeText(this,
                    R.string.lockpattern_recording_incorrect_too_short,
                    Toast.LENGTH_SHORT).show();
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            return;
        }

        checkLockPatternPresent.checkLockPattern(LockPatternView.patternToString(pattern));
        lockPatternView.disableInput();
        showProgress();
//        if (pattern.equals(lockPattern)) {
//            finish();
//        } else {
//            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
//            Toast.makeText(this, "密码错误", Toast.LENGTH_LONG)
//                    .show();
//        }
    }


    public void refershUIWhenFailure() {
        dismissProgress();
        lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
        lockPatternView.enableInput();
    }

    public boolean isSetGestrue() {
        return setGestrue;
    }
}

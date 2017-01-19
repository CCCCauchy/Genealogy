package com.oraro.genealogy.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.oraro.genealogy.R;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.mvp.presenter.UploadLockPatternPresent;
import com.oraro.genealogy.ui.view.LockPatternView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LockSetupActivity extends BasicActivity implements
        LockPatternView.OnPatternListener, OnClickListener {

    private static final String TAG = "LockSetupActivity";
    private LockPatternView lockPatternView;
    private Button leftButton;
    private Button rightButton;
    private View mSkipLockSetup = null;
    private TextView mLockSetupAttention = null;

    private static final int STEP_1 = 1; // 开始
    private static final int STEP_2 = 2; // 第一次设置手势完成
    private static final int STEP_3 = 3; // 按下继续按钮
    private static final int STEP_4 = 4; // 第二次设置手势完成
    // private static final int SETP_5 = 4; // 按确认按钮

    private int step;

    private List<LockPatternView.Cell> choosePattern;

    private boolean confirm = false;

    @Override
    public Presenter createPresenter() {
        return null;
    }

    @Override
    public BasicActivity getUi() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_setup);
        lockPatternView = (LockPatternView) findViewById(R.id.lock_pattern);
        lockPatternView.setOnPatternListener(this);
        leftButton = (Button) findViewById(R.id.left_btn);
        rightButton = (Button) findViewById(R.id.right_btn);
        mSkipLockSetup = findViewById(R.id.skip_lock_setup);
        mLockSetupAttention = (TextView) findViewById(R.id.lock_setup_attention);
        mSkipLockSetup.setOnClickListener(this);

        step = STEP_1;
        updateView();
    }

    private void updateView() {
        switch (step) {
            case STEP_1:
                Log.i(TAG,"STEP_1");
                leftButton.setText(R.string.cancel);
                rightButton.setText("");
                rightButton.setEnabled(false);
                choosePattern = null;
                confirm = false;
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;
            case STEP_2:
                Log.i(TAG,"STEP_2");
                leftButton.setText(R.string.try_again);
                rightButton.setText(R.string.continue_);
                rightButton.setEnabled(true);
                lockPatternView.disableInput();
                if (true) {
                    try {
                        LockSetupActivity.this.getMainLooper().getThread().sleep(1000);
                        mLockSetupAttention.setText(getString(R.string.draw_pattern_confirm));
                        step = STEP_3;
                        updateView();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case STEP_3:
                Log.i(TAG,"STEP_3");
                leftButton.setText(R.string.cancel);
                rightButton.setText("");
                rightButton.setEnabled(false);
                lockPatternView.clearPattern();
                lockPatternView.enableInput();
                break;
            case STEP_4:
                Log.i(TAG,"STEP_4.."+confirm);
                leftButton.setText(R.string.cancel);
                if (confirm) {
                    rightButton.setText(R.string.confirm);
                    rightButton.setEnabled(true);
                    lockPatternView.disableInput();
                    UploadLockPatternPresent uploadLockPatternPresent = new UploadLockPatternPresent(LockSetupActivity.this);
                    uploadLockPatternPresent.uploadLockPattern(LockPatternView.patternToString(choosePattern));
                    Log.i(TAG,LockPatternView.patternToString(choosePattern));
                } else {
                    rightButton.setText("");
                    lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    mLockSetupAttention.setText(getString(R.string.please_try_again));
                    lockPatternView.enableInput();
                    rightButton.setEnabled(false);
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_btn:
                if (step == STEP_1 || step == STEP_3 || step == STEP_4) {
                    finish();
                } else if (step == STEP_2) {
                    step = STEP_1;
                    updateView();
                }
                break;

            case R.id.right_btn:
                if (step == STEP_2) {
                    step = STEP_3;
                    updateView();
                } else if (step == STEP_4) {

//                SharedPreferences preferences = getSharedPreferences("jk",
//                         MODE_PRIVATE);
//                preferences
//                        .edit()
//                        .putString("dsfljk",
//                                LockPatternView.patternToString(choosePattern))
//                        .commit();

                    Intent intent = new Intent(this, LockActivity.class);
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.skip_lock_setup:
                startActivity(new Intent(LockSetupActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }

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
    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        Log.d(TAG, "onPatternDetected");

        if (pattern.size() < LockPatternView.MIN_LOCK_PATTERN_SIZE) {
            Toast.makeText(this,
                    R.string.lockpattern_recording_incorrect_too_short,
                    Toast.LENGTH_LONG).show();
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            return;
        }

        if (choosePattern == null) {
            choosePattern = new ArrayList<LockPatternView.Cell>(pattern);
            //           Log.d(TAG, "choosePattern = "+choosePattern.toString());
//            Log.d(TAG, "choosePattern.size() = "+choosePattern.size());
            Log.d(TAG, "choosePattern = " + Arrays.toString(choosePattern.toArray()));

            step = STEP_2;
            updateView();
            return;
        }
//[(row=1,clmn=0), (row=2,clmn=0), (row=1,clmn=1), (row=0,clmn=2)]
//[(row=1,clmn=0), (row=2,clmn=0), (row=1,clmn=1), (row=0,clmn=2)]    

        Log.d(TAG, "choosePattern = " + Arrays.toString(choosePattern.toArray()));
        Log.d(TAG, "pattern = " + Arrays.toString(pattern.toArray()));

        if (choosePattern.equals(pattern)) {
//            Log.d(TAG, "pattern = "+pattern.toString());
//            Log.d(TAG, "pattern.size() = "+pattern.size());
            Log.d(TAG, "pattern = " + Arrays.toString(pattern.toArray()));

            confirm = true;
        } else {
            confirm = false;
        }

        step = STEP_4;
        updateView();

    }

}

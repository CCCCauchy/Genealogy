package com.oraro.genealogy.ui.activity;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.util.Utils;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wangxing on 2016/10/8.
 */
public class WelcomeActivity extends BasicActivity {
    private final String TAG = this.getClass().getSimpleName();
    private RelativeLayout welcom_ui;
    private TextView timing_display;
    private Context mContext;
    private int timeCount = 1;
    private String timing_display_str = "";
    private Handler mHandler = null;

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
        setContentView(R.layout.activity_welcome);
        init();
        handleWelcomeUibackground();
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    /**
     * Call init the activity data method.
     */
    private void init() {
        mContext = getApplicationContext();
        initView();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        displayTiming();
                        break;
                    default:
                        break;
                }

            }
        };
    }

    /**
     * Call init view data method.
     */
    private void initView() {
        welcom_ui = (RelativeLayout) findViewById(R.id.welcome_ui);
        timing_display = (TextView) findViewById(R.id.timing_display);
        timing_display_str = getResources().getText(R.string.welcome_timing).toString();
        timing_display.setText(timeCount + timing_display_str);
    }

    /**
     * Call handle the welcome gui theme method.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void handleWelcomeUibackground() {
//        File file = new File("welcome/welcome_background.png");
//        Glide.with(this).load(file).asBitmap().skipMemoryCache(true).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
//
//                welcom_ui.setBackground(new BitmapDrawable(bitmap));
//            }
//        });
        welcom_ui.setBackground(new BitmapDrawable(getImageFromAssetsFile("welcome/welcome_background.png")));
    }

    /**
     * Call get image from assets file method .
     *
     * @param fileName
     * @return
     */
    private Bitmap getImageFromAssetsFile(String fileName) {
        Bitmap image = null;
        AssetManager am = getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Call display timing method.
     */
    private void displayTiming() {
        timeCount--;
        if (timeCount <= 0) {
            User user = Utils.getCurrentUser();
            if (user != null && user.isHasLockSetup()) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
            finish();
        } else {
            timing_display.setText(timeCount + timing_display_str);
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    }
}

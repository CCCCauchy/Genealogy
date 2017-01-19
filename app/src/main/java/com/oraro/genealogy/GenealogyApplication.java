package com.oraro.genealogy;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Administrator on 2016/10/13.
 */
public class GenealogyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowConfig.Builder build = new FlowConfig.Builder(getApplicationContext());
        build.openDatabasesOnInit(true);
        FlowManager.init(new FlowConfig.Builder(getApplicationContext()).build());
    }
}

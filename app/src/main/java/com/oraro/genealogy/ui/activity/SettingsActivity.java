package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.oraro.genealogy.R;
import com.oraro.genealogy.data.entity.User;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.util.Utils;
import com.raizlabs.android.dbflow.sql.language.Select;

/**
 * Created by Administrator on 2016/11/2.
 */
public class SettingsActivity extends BasicActivity implements View.OnClickListener {
    private TextView setGesture = null;
    private TextView checkVersion = null;
    private TextView logout = null;

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
        setContentView(R.layout.activity_setttings);
        setGesture = (TextView) findViewById(R.id.set_gesture);
        checkVersion = (TextView) findViewById(R.id.check_version);
        logout = (TextView) findViewById(R.id.logout);

        setGesture.setOnClickListener(this);
        checkVersion.setOnClickListener(this);
        logout.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        User user = Utils.getCurrentUser();
        switch (v.getId()) {
            case R.id.set_gesture:
                Intent intent = null;
                if (user != null && user.isHasLockSetup()) {
                    intent = new Intent(SettingsActivity.this, LockActivity.class);
                    intent.putExtra("set_gesture",true);
                } else {
                    intent = new Intent(SettingsActivity.this, LockSetupActivity.class);
                }
                startActivity(intent);
                break;
            case R.id.check_version:
                break;
            case R.id.logout:
                try {
                    if (!new Select().from(User.class).queryList().isEmpty()) {
                        user.setCurrentUser(false);
                        user.save();
                        Toast.makeText(SettingsActivity.this, R.string.logout, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
                break;
            default:
                break;
        }
    }
}

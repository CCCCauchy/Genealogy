package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.oraro.genealogy.R;
import com.oraro.genealogy.mvp.presenter.LoginPresent;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.util.Utils;

/**
 * Created by Administrator on 2016/10/13.
 */
public class LoginActivity extends BasicActivity implements View.OnClickListener {
    private String TAG = this.getClass().getSimpleName();
    private EditText mUserNameEdit = null;
    private EditText mPasswordEdit = null;
    private Button mLoginButton = null;
    private TextView mJoinFamilyText = null;
    private TextView mFogotPasswordText = null;
    private CheckBox mRememberUsernameCheckbox = null;
    private Button mClearUsernameButton = null;
    private Button mShowPasswordButton = null;
    private TextWatcher mUsernameWatcher = null;
    private TextWatcher mPasswordWatcher = null;

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
        setContentView(R.layout.activity_login);
        mUserNameEdit = (EditText) findViewById(R.id.username_edit_text);
        mPasswordEdit = (EditText) findViewById(R.id.password_edit_text);
        mLoginButton = (Button) findViewById(R.id.user_login);
        mJoinFamilyText = (TextView) findViewById(R.id.join_the_family_textview);
        mFogotPasswordText = (TextView) findViewById(R.id.fogot_password_textview);
        mRememberUsernameCheckbox = (CheckBox) findViewById(R.id.remember_username_checkbox);
        mClearUsernameButton = (Button) findViewById(R.id.clear_username_button);
        mShowPasswordButton = (Button) findViewById(R.id.show_password_button);

        mLoginButton.setOnClickListener(this);
        mJoinFamilyText.setOnClickListener(this);
        mFogotPasswordText.setOnClickListener(this);
        mClearUsernameButton.setOnClickListener(this);
        mShowPasswordButton.setOnClickListener(this);
        initWatcher();
        mUserNameEdit.addTextChangedListener(mUsernameWatcher);
        mPasswordEdit.addTextChangedListener(mPasswordWatcher);

        String username = mSharedPreferences.getString("username", null);
        if (username != null) {
            mUserNameEdit.setText(username);
            mUserNameEdit.setSelection(username.length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_login:
                showProgress();
                hideIME();
                String username = null;
                String password = null;
                if (mUserNameEdit.getText() != null) {
                    username = mUserNameEdit.getText().toString();
                }
                if (mPasswordEdit.getText() != null) {
                    password = mPasswordEdit.getText().toString();
                }
                if (mRememberUsernameCheckbox.isChecked()) {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("username", username);
                    editor.commit();
                }
                if (username != null && password != null) {
                    LoginPresent mLoginPresent = new LoginPresent(LoginActivity.this);
                    mLoginPresent.doUserLogin(username, password, Build.DEVICE, Utils.getAppVersionName(LoginActivity.this));
                }
                break;
            case R.id.join_the_family_textview:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.fogot_password_textview:
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
                break;
            case R.id.clear_username_button:
                mUserNameEdit.setText(null);
                mPasswordEdit.setText(null);
                break;
            case R.id.show_password_button:
                if (mPasswordEdit.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    mPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    mPasswordEdit.setSelection(mPasswordEdit.getText().length());
                    mShowPasswordButton.setBackgroundResource(R.mipmap.not_show_password);
                } else {
                    mPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mPasswordEdit.setSelection(mPasswordEdit.getText().length());
                    mShowPasswordButton.setBackgroundResource(R.mipmap.show_password);
                }
//                mPasswordEdit.setSelection(mPasswordEdit.getText().toString().length());
                break;
            default:
                break;
        }

    }

    private void initWatcher() {
        mUsernameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mClearUsernameButton.setVisibility(View.VISIBLE);
                } else {
                    mClearUsernameButton.setVisibility(View.INVISIBLE);
                }
            }
        };
        mPasswordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mShowPasswordButton.setVisibility(View.VISIBLE);
                } else {
                    mShowPasswordButton.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

}

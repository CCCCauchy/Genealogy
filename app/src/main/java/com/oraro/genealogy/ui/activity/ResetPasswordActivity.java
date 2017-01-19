package com.oraro.genealogy.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.oraro.genealogy.R;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.mvp.presenter.ResetPasswordPresent;

/**
 * Created by Administrator on 2016/10/14.
 */
public class ResetPasswordActivity extends BasicActivity implements View.OnClickListener {

    private EditText mIDCardEdit = null;
    private EditText mPhoneNumberEdit = null;
    private EditText mNewPasswordEdit = null;
    private Button mShowNewPasswordButton = null;
    private Button mSubmitButton = null;

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
        setContentView(R.layout.activity_reset_password);
        mIDCardEdit = (EditText) findViewById(R.id.id_card_edit);
        mPhoneNumberEdit = (EditText) findViewById(R.id.phone_number_edit);
        mNewPasswordEdit = (EditText) findViewById(R.id.new_password_edit);
        mShowNewPasswordButton = (Button) findViewById(R.id.show_new_password_button);
        mSubmitButton = (Button) findViewById(R.id.reset_password_button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.reset_password));

        mShowNewPasswordButton.setOnClickListener(this);
        mSubmitButton.setOnClickListener(this);
        mNewPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mShowNewPasswordButton.setVisibility(View.VISIBLE);
                } else {
                    mShowNewPasswordButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_new_password_button:
                if (mNewPasswordEdit.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    mNewPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                    mNewPasswordEdit.setSelection(mNewPasswordEdit.getText().length());
                    mShowNewPasswordButton.setBackgroundResource(R.mipmap.not_show_password);
                } else {
                    mNewPasswordEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mNewPasswordEdit.setSelection(mNewPasswordEdit.getText().length());
                    mShowNewPasswordButton.setBackgroundResource(R.mipmap.show_password);
                }
                break;
            case R.id.reset_password_button:
                ResetPasswordPresent mResetPasswordPresent = new ResetPasswordPresent(ResetPasswordActivity.this);
                if (mIDCardEdit.getText() != null && mPhoneNumberEdit.getText() != null && mNewPasswordEdit != null) {
                    mResetPasswordPresent.resetPassword(mIDCardEdit.getText().toString(), mPhoneNumberEdit.getText().toString(), mNewPasswordEdit.getText().toString());
                }
                break;

            default:
                break;
        }
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
}

package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.oraro.genealogy.R;
import com.oraro.genealogy.mvp.presenter.InitiateCommonwealPresenter;

/**
 * Created by Administrator on 2016/11/16.
 */
public class InitiateCommonwealActivity extends BasicActivity<InitiateCommonwealPresenter> implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private EditText commonwealNameEdit = null;
    private EditText commonwealGenerationEdit = null;
    private EditText commonwealDegreeEdit = null;
    private EditText commonwealSexEdit = null;
    private EditText commonwealBirthEdit = null;
    private EditText commonwealAmountEdit = null;
    private EditText commonwealSituationEdit = null;
    private EditText commonwealBankEdit = null;
    private EditText commonwealTypeEdit = null;
    private EditText commonwealInvitationEdit = null;
    private EditText commonwealReasonEdit = null;
    private ImageButton commonwealAddPictrue = null;
    private GridView commonwealPictrueGridView = null;

    @Override
    public InitiateCommonwealPresenter createPresenter() {
        return new InitiateCommonwealPresenter();
    }

    @Override
    public BasicActivity getUi() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_commonweal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        commonwealNameEdit = (EditText) findViewById(R.id.commonweal_name_edit);
        commonwealGenerationEdit = (EditText) findViewById(R.id.commonweal_generation_edit);
        commonwealDegreeEdit = (EditText) findViewById(R.id.commonweal_degree_edit);
        commonwealSexEdit = (EditText) findViewById(R.id.commonweal_sex_edit);
        commonwealBirthEdit = (EditText) findViewById(R.id.commonweal_birth_edit);
        commonwealAmountEdit = (EditText) findViewById(R.id.commonweal_amount_edit);
        commonwealSituationEdit = (EditText) findViewById(R.id.commonweal_situation_edit);
        commonwealBankEdit = (EditText) findViewById(R.id.commonweal_bank_edit);
        commonwealTypeEdit = (EditText) findViewById(R.id.commonweal_type_edit);
        commonwealInvitationEdit = (EditText) findViewById(R.id.commonweal_invitation_edit);
        commonwealReasonEdit = (EditText) findViewById(R.id.commonweal_reason_edit);
        commonwealAddPictrue = (ImageButton) findViewById(R.id.commonweal_add_pictrue);
        commonwealPictrueGridView = (GridView) findViewById(R.id.commonweal_pictrue_gridView);

        commonwealAddPictrue.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.initiate_vote_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.vote_submit:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commonweal_add_pictrue:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }
}

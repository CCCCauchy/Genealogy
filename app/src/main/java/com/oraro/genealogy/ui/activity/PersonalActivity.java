package com.oraro.genealogy.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.oraro.genealogy.R;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.ui.view.tag.TagBaseAdapter;
import com.oraro.genealogy.ui.view.tag.TagCloudLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/7.
 */
public class PersonalActivity extends BasicActivity implements View.OnClickListener {
    private TagCloudLayout selfTagLayout;
    private TagCloudLayout otherTagLayout;
    private TagBaseAdapter selfTagAdapter;
    private TagBaseAdapter otherTagAdapter;
    private EditText editTag;
    private FrameLayout editTagFrameLayout;
    private Button submitTag;
    List<String> selfTagList;
    List<String> otherTagList;

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
        setContentView(R.layout.activity_personal);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        selfTagLayout = (TagCloudLayout) findViewById(R.id.self_tag_layout);
        otherTagLayout = (TagCloudLayout) findViewById(R.id.other_tag_layout);
        editTag = (EditText) findViewById(R.id.edit_tag);
        editTagFrameLayout = (FrameLayout) findViewById(R.id.edit_tag_frame);
        submitTag = (Button) findViewById(R.id.submit_tag);
        submitTag.setOnClickListener(this);
        selfTagList = new ArrayList<>();
        selfTagList.add("南京");
        selfTagList.add("富二代");
        selfTagList.add("医生");

        otherTagList = new ArrayList<>();
        otherTagList.add("北京");
        otherTagList.add("官二代");
        otherTagList.add("经纪人");
        selfTagAdapter = new TagBaseAdapter(this, selfTagList);
        otherTagAdapter = new TagBaseAdapter(this, otherTagList);
        selfTagLayout.setAdapter(selfTagAdapter);

        ImageButton imageButton = new ImageButton(this);
        imageButton.setBackgroundResource(R.mipmap.add_tag);
        otherTagLayout.setAdapter(otherTagAdapter, imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTag.requestFocus();
                showIME(editTag);
                editTagFrameLayout.setVisibility(View.VISIBLE);
            }
        });
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
        switch (v.getId()) {
            case R.id.submit_tag:
                if (editTag != null && editTag.getText() != null) {
                    otherTagList.add(editTag.getText().toString());
                    otherTagAdapter.notifyDataSetChanged();
                    editTag.setText(null);
                    hideIME();
                    editTagFrameLayout.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (editTagFrameLayout.getVisibility() == View.VISIBLE) {
            editTagFrameLayout.setVisibility(View.INVISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}

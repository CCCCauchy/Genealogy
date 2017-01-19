package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.SimpleResource;
import com.oraro.genealogy.R;
import com.oraro.genealogy.constant.Constant;
import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.mvp.presenter.Presenter;
import com.oraro.genealogy.mvp.presenter.VoteDetailPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/11.
 */
public class VoteDetailActivity extends BasicActivity<VoteDetailPresenter> implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();

    private TextView title = null;
    private TextView content = null;
    private ImageView voteStateImage = null;
    private TextView beginTime = null;
    private TextView endTime = null;
    private Button approveButton = null;
    private Button rejectButton = null;
    private Button voteButton = null;
    private RecyclerView optionListView = null;


    private VoteDetialOptionAdapter optionListAdapter = null;

    private int formId = -1;
    private List<String> optionText = new ArrayList<>();
    private Map<String, String> optionPicture = new HashMap<>();

    @Override

    public VoteDetailPresenter createPresenter() {
        return new VoteDetailPresenter();
    }

    @Override
    public BasicActivity getUi() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        title = (TextView) findViewById(R.id.vote_detail_title);
        content = (TextView) findViewById(R.id.vote_detail_content);
        beginTime = (TextView) findViewById(R.id.vote_detail_begin_time);
        endTime = (TextView) findViewById(R.id.vote_detail_end_time);
        approveButton = (Button) findViewById(R.id.vote_detail_approve);
        rejectButton = (Button) findViewById(R.id.vote_detail_reject);
        voteButton = (Button) findViewById(R.id.vote_detail_vote);
        voteStateImage = (ImageView) findViewById(R.id.vote_detail_state_image);
        optionListView = (RecyclerView) findViewById(R.id.vote_detail_option_list);
        optionListAdapter = new VoteDetialOptionAdapter(new Decision());
        optionListView.setAdapter(optionListAdapter);
        optionListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        approveButton.setOnClickListener(this);
        rejectButton.setOnClickListener(this);
        voteButton.setOnClickListener(this);

        Intent intent = getIntent();
        if ("vote".equals(intent.getStringExtra("action"))) {
            approveButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
            voteButton.setVisibility(View.VISIBLE);
        } else {
            title.setText(intent.getStringExtra("title"));
            content.setText(intent.getStringExtra("content"));
            formId = intent.getIntExtra("form_id", -1);
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

    public void onLoadData(Decision decision) {
        Date endDate = decision.getEndTime();
        if (endDate.getMinutes() < 10) {
            endTime.setText((endDate.getYear() + 1900) + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate() + "  " + endDate.getHours() + ":0" + endDate.getMinutes());
        } else {
            endTime.setText((endDate.getYear() + 1900) + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate() + "  " + endDate.getHours() + ":" + endDate.getMinutes());
        }
        optionListAdapter.addItems(decision);
    }

    public int getFormId() {
        return formId;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vote_detail_approve:
                getPresenter().verifyVote(formId, Constant.APPROVE);
                break;
            case R.id.vote_detail_reject:
                getPresenter().verifyVote(formId, Constant.REJECT);
                break;
            default:
                break;
        }
    }


    class VoteDetialOptionAdapter extends RecyclerView.Adapter {

        private Decision item;

        public void addItems(Decision decision) {
            item = decision;
            notifyDataSetChanged();
        }

        public VoteDetialOptionAdapter(Decision decision) {
            item = decision;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new VoteDetialOptionViewHolder(LayoutInflater.from(VoteDetailActivity.this).inflate(R.layout.vote_detail_option_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Log.i("sysout", "onBindViewHolder");
            final VoteDetialOptionViewHolder voteDetialOptionViewHolder = (VoteDetialOptionViewHolder) holder;
            if (item.getOptionPicture() != null) {
                Glide.with(VoteDetailActivity.this).load(item.getOptionPicture().get(Integer.toString(position))).
                        placeholder(R.mipmap.default_photo).error(R.mipmap.ic_launcher).into(voteDetialOptionViewHolder.optionImageView);
            }
            if (item.getOptionText() != null) {
                voteDetialOptionViewHolder.optionText.setText(item.getOptionText().get(position));
            }
            voteDetialOptionViewHolder.optionCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        voteDetialOptionViewHolder.optionLayout.setBackgroundColor(Color.LTGRAY);
                    } else {
                        voteDetialOptionViewHolder.optionLayout.setBackgroundColor(Color.WHITE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (item.getOptionText() != null)
                return item.getOptionText().size();
            else return 0;
        }
    }

    class VoteDetialOptionViewHolder extends RecyclerView.ViewHolder {
        TextView optionText = null;
        ImageView optionImageView = null;
        CheckBox optionCheckbox = null;
        LinearLayout optionLayout = null;

        public VoteDetialOptionViewHolder(View itemView) {
            super(itemView);
            optionText = (TextView) itemView.findViewById(R.id.vote_detail_option_text);
            optionImageView = (ImageView) itemView.findViewById(R.id.vote_detail_option_picture);
            optionCheckbox = (CheckBox) itemView.findViewById(R.id.vote_detail_option_checkbox);
            optionLayout = (LinearLayout) itemView.findViewById(R.id.vote_detail_option_layout);
        }
    }
}

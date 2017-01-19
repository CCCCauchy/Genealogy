package com.oraro.genealogy.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.oraro.genealogy.R;
import com.oraro.genealogy.constant.Constant;
import com.oraro.genealogy.data.entity.Decision;
import com.oraro.genealogy.mvp.presenter.InitiateVotePresenter;
import com.oraro.genealogy.ui.view.dialog.TimePickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/10/24.
 */
public class InitiateVoteActivity extends BasicActivity<InitiateVotePresenter> implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private EditText typeTitle = null;
    private EditText typeContent = null;
    private LinearLayout optionLayout = null;
    private TextView endTimePicker = null;
    private RecyclerView optionListView = null;
    private Spinner voteType = null;
    private Switch isSecret = null;
    private Switch canViewResult = null;
    private RelativeLayout relativeLayout = null;
    private TextView voteGroupText = null;
    private OptionListAdapter optionListAdapter = null;
    private Map<String, Bitmap> optionMap = new HashMap<>();
    private Map<String, String> optionPicture = new HashMap<>();
    private List<String> optionTextList = new ArrayList<>();
    private Date endDate = new Date(System.currentTimeMillis() + 3600 * 24 * 1000);//默认结束时间为一天后
    private Decision decision = new Decision();

    @Override
    public InitiateVotePresenter createPresenter() {
        return new InitiateVotePresenter();
    }

    @Override
    public BasicActivity getUi() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_vote);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        typeTitle = (EditText) findViewById(R.id.initiate_vote_title);
        typeContent = (EditText) findViewById(R.id.initiate_vote_content);
        optionLayout = (LinearLayout) findViewById(R.id.initiate_vote_option_layout);
        endTimePicker = (TextView) findViewById(R.id.end_time_picker);
        voteType = (Spinner) findViewById(R.id.vote_types_spinner);
        isSecret = (Switch) findViewById(R.id.secret_ballot_switch);
        voteGroupText = (TextView) findViewById(R.id.vote_group_text);
        relativeLayout = (RelativeLayout) findViewById(R.id.vote_group_layout);
        canViewResult = (Switch) findViewById(R.id.view_vote_results_switch);
        relativeLayout.setOnClickListener(this);
        endTimePicker.setOnClickListener(this);
        if (endDate.getMinutes() < 10) {
            endTimePicker.setText((endDate.getYear() + 1900) + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate() + "  " + endDate.getHours() + ":0" + endDate.getMinutes());
        } else {
            endTimePicker.setText((endDate.getYear() + 1900) + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate() + "  " + endDate.getHours() + ":" + endDate.getMinutes());
        }
        optionListView = (RecyclerView) findViewById(R.id.option_list_view);
        optionListAdapter = new OptionListAdapter(optionMap, 1);
        optionListView.setAdapter(optionListAdapter);
        optionListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        optionTextList.add("Default");
        optionTextList.add("Default");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.initiate_vote_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.vote_submit:
                if (typeTitle == null || typeTitle.getText() == null) {
                    Toast.makeText(InitiateVoteActivity.this, "title is null", Toast.LENGTH_SHORT).show();
                    break;
                } else if (typeContent == null || typeContent.getText() == null) {
                    Toast.makeText(InitiateVoteActivity.this, "content is null", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    decision.setTitle(typeTitle.getText().toString());
                    decision.setContent(typeContent.getText().toString());
                    decision.setOptionText(optionTextList);
                    decision.setType(voteType.getSelectedItemPosition());
                    decision.setEndTime(endDate);
                    decision.setSecret(isSecret.isChecked());
                    decision.setCanViewResult(canViewResult.isChecked());
//                    decision.setState(Constant.NEED_APPROVE);
                    showProgress();
                    getPresenter().submitVote(decision, optionPicture);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.end_time_picker:
                TimePickerDialog timePickerDialog = new TimePickerDialog(InitiateVoteActivity.this, true, new TimePickerDialog.OnTimePickedListener() {
                    @Override
                    public void onConfirmPick(Integer... times) {
                        if (times[4] < 10) {
                            endTimePicker.setText(times[0] + "-" + (times[1] + 1) + "-" + times[2] + "  " + times[3] + ":0" + times[4]);
                        } else {
                            endTimePicker.setText(times[0] + "-" + (times[1] + 1) + "-" + times[2] + "  " + times[3] + ":" + times[4]);
                        }
                        endDate = new Date(times[0], times[1], times[2], times[3], times[4]);
                    }
                });
                timePickerDialog.show();
                timePickerDialog.setMinDate(System.currentTimeMillis());
                break;
            case R.id.vote_group_layout:
                startActivityForResult(new Intent(InitiateVoteActivity.this, VoteGroupActivity.class), 1001);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                int voteGroup = data.getIntExtra("group_type", 0);
                decision.setVoteGroup(voteGroup);
                voteGroupText.setText(getRoleName(voteGroup));
                return;
            }
            for (int i = 0; i <= requestCode; i++) {
                if (i != requestCode) continue;
                Uri uri = data.getData();
                Log.i(TAG, "picture uri=" + uri);
                Bitmap b = null;
                try {
                    b = (Bitmap) MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                optionListAdapter.setItem(i, b);

                Cursor cursor = this.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                optionPicture.put(Integer.toString(i), cursor.getString(column_index));
                cursor.close();
            }
        }
    }

    private String getRoleName(int roleGroup) {
        String roleName = null;
        if (roleGroup == 0) {
            roleName = "全体成员";
        } else if (roleGroup == 1) {
            roleName = "决策层";
        } else if (roleGroup == 2) {
            roleName = "长老层";
        } else if (roleGroup == 3) {
            roleName = "族长";
        }
        return roleName;
    }

    class OptionListAdapter extends RecyclerView.Adapter {
        private Map<String, Bitmap> items;
        private int BEHAVIOR_MODE;

        public OptionListAdapter(Map<String, Bitmap> bitmaps, int mode) {
            items = bitmaps;
            BEHAVIOR_MODE = mode;
        }

        public void setItem(int position, Bitmap bitmap) {
            items.put(Integer.toString(position), bitmap);
            notifyItemChanged(position);
        }

        public void addItem() {
            String defaultText = "Default";
            optionTextList.add(defaultText);
            int position = optionTextList.size() - 1;
            notifyItemInserted(position);
        }

        public void removeItem() {
            int position = optionTextList.size() - 1;
            optionTextList.remove(position);
            optionMap.remove(Integer.toString(position));
            notifyItemRemoved(position);
        }

        @Override
        public int getItemViewType(int position) {
            if (position + 1 == getItemCount() && (BEHAVIOR_MODE == 1))
                return -1;
            else return 0;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(TAG, "onCreateViewHolder.." + viewType);
            switch (viewType) {
                case -1:
                    return new OptionFooterViewHolder(LayoutInflater.from(InitiateVoteActivity.this).inflate(R.layout.option_footer_layout, parent, false));
                default:
                    return new OptionViewHolder(LayoutInflater.from(InitiateVoteActivity.this).inflate(R.layout.option_item_layout, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            Log.i(TAG, "onBindViewHolder.." + position + ",,,,itemSize=" + items.size() + ",type=" + holder.getItemViewType());
            switch (holder.getItemViewType()) {
                case -1:
                    final OptionFooterViewHolder optionFooterViewHolder = (OptionFooterViewHolder) holder;
                    if (optionTextList.size() < 3) {
                        optionFooterViewHolder.removeOption.setVisibility(View.GONE);
                    } else if (optionTextList.size() > 5) {
                        optionFooterViewHolder.addOption.setVisibility(View.GONE);
                    } else {
                        optionFooterViewHolder.removeOption.setVisibility(View.VISIBLE);
                        optionFooterViewHolder.addOption.setVisibility(View.VISIBLE);
                    }
                    optionFooterViewHolder.removeOption.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeItem();
                            if (optionTextList.size() < 3) {
                                optionFooterViewHolder.removeOption.setVisibility(View.GONE);
                            } else if (optionTextList.size() > 5) {
                                optionFooterViewHolder.addOption.setVisibility(View.GONE);
                            } else {
                                optionFooterViewHolder.removeOption.setVisibility(View.VISIBLE);
                                optionFooterViewHolder.addOption.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    optionFooterViewHolder.addOption.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addItem();
                            if (optionTextList.size() < 3) {
                                optionFooterViewHolder.removeOption.setVisibility(View.GONE);
                            } else if (optionTextList.size() > 5) {
                                optionFooterViewHolder.addOption.setVisibility(View.GONE);
                            } else {
                                optionFooterViewHolder.removeOption.setVisibility(View.VISIBLE);
                                optionFooterViewHolder.addOption.setVisibility(View.VISIBLE);
                            }
                            optionFooterViewHolder.removeOption.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                default:
                    OptionViewHolder optionViewHolder = (OptionViewHolder) holder;
                    if (position < optionTextList.size() && !"Default".equals(optionTextList.get(position))) {
                        optionViewHolder.optionContentWord.setText(optionTextList.get(position));
                    } else {
                        optionViewHolder.optionContentWord.setHint("选项" + (position + 1));
                    }
                    optionViewHolder.optionContentWord.addTextChangedListener(new OptionTextWatcher(position));
                    optionViewHolder.optionAddPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            startActivityForResult(intent, position);
                        }
                    });
                    Bitmap itemImage = items.get(Integer.toString(position));
                    if (itemImage == null) {
                        optionViewHolder.optionPhotoGridView.setVisibility(View.GONE);
                    } else {
                        optionViewHolder.optionPhotoGridView.setVisibility(View.VISIBLE);
                        optionViewHolder.optionPhotoGridView.setAdapter(new GridAdapter(itemImage, position));
                    }
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return optionTextList.size() + 1;
        }

    }

    class OptionViewHolder extends RecyclerView.ViewHolder {

        EditText optionContentWord;
        ImageButton optionAddPhoto;
        GridView optionPhotoGridView;

        public OptionViewHolder(View itemView) {
            super(itemView);
            optionContentWord = (EditText) itemView.findViewById(R.id.option_content_word);
            optionAddPhoto = (ImageButton) itemView.findViewById(R.id.option_add_photo);
            optionPhotoGridView = (GridView) itemView.findViewById(R.id.option_photo_gridView);
        }
    }

    class OptionFooterViewHolder extends RecyclerView.ViewHolder {

        ImageButton addOption;
        ImageButton removeOption;

        public OptionFooterViewHolder(View itemView) {
            super(itemView);
            addOption = (ImageButton) itemView.findViewById(R.id.add_option);
            removeOption = (ImageButton) itemView.findViewById(R.id.remove_option);
        }
    }

    class GridAdapter extends BaseAdapter {
        protected Bitmap item;
        int itemPostion;

        public GridAdapter() {
        }

        public GridAdapter(Bitmap bitmap, int postion) {
            item = bitmap;
            itemPostion = postion;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ImageView imageView = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(InitiateVoteActivity.this).inflate(R.layout.item_image, parent, false);
                imageView = (ImageView) convertView.findViewById(R.id.imageView);
            }
            if (imageView != null) {
                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        optionMap.remove(Integer.toString(itemPostion));
                        optionListAdapter.notifyItemChanged(itemPostion);
                        Log.i(TAG, "LongClick..postion=" + position + "," + optionMap.containsValue(item));
                        return false;
                    }
                });
                if (item != null) {
                    imageView.setImageBitmap(item);
                } else {
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }
            }
            return convertView;
        }
    }

    class OptionTextWatcher implements TextWatcher {
        private int postion;

        public OptionTextWatcher(int p) {
            postion = p;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && s.length() > 0) {
                optionTextList.set(postion, s.toString());
                Log.i(TAG, postion + ", text:" + optionTextList.get(postion));
            }
        }
    }
}

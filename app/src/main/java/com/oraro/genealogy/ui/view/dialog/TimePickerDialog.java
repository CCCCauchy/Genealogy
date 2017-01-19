package com.oraro.genealogy.ui.view.dialog;

import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.oraro.genealogy.R;


/**
 * 时间选择Dialog
 */
public class TimePickerDialog extends Dialog {
    /**
     * 日期
     */
    private DatePicker mDatePicker;

    /**
     * 时间
     */
    private TimePicker mTimePicker;

    /**
     * 确认按钮
     */
    private Button mConfirmButton;

    /**
     * 取消按钮
     */
    private Button mCancelButton;

    /**
     * 当前时间
     */
    private long currentTimeMillis;

    /**
     * 当前选择的年
     */
    private int mCurrentYear = -1;

    /**
     * 当前选择的月
     */
    private int mCurrentMonth = -1;

    /**
     * 当前选择的日
     */
    private int mCurrentDay = -1;

    /**
     * 当前选择的小时
     */
    private int mCurrentHour = -1;

    /**
     * 当前选择的分钟
     */
    private int mCurrentMinute = -1;

    /**
     * title资源id
     */
    private int mTitleRes;

    /**
     * 是否要选择日期
     */
    private boolean mNeedPickDate = false;

    /**
     * 时间选择结果监听器
     */
    private OnTimePickedListener mPickedListener;

    /**
     * [时间选择结果监听接口]<BR>
     */
    public interface OnTimePickedListener {
        /**
         * [确定事件选择结果]<BR>
         * [功能详细描述]
         *
         * @param times 时间选择结果数组，从数组第一个元素开始，依次为年、月、日、时、分。
         */
        void onConfirmPick(Integer... times);
    }

    /**
     * 构造函数
     * [构造简要说明]
     *
     * @param context  上下文
     * @param pickDate 是否选择日期
     */
    public TimePickerDialog(Context context, boolean pickDate) {
        super(context, R.style.TimePickDialog);
        mNeedPickDate = pickDate;
        if (mNeedPickDate) {
            setTitleRes(R.string.select_date);
        } else {
            setTitleRes(R.string.select_time);
        }
    }

    /**
     * 构造函数
     * [构造简要说明]
     *
     * @param context  上下文
     * @param pickDate 是否选择日期
     * @param listener 选择结果监听
     */
    public TimePickerDialog(Context context, boolean pickDate,
                            OnTimePickedListener listener) {
        this(context, pickDate);
        mPickedListener = listener;
    }

    /**
     * [设置title]<BR>
     * [功能详细描述]
     *
     * @param resId 资源id
     */
    public void setTitleRes(int resId) {
        mTitleRes = resId;
    }

    /**
     * [设置显示的时间]<BR>
     * [功能详细描述]
     *
     * @param times 时间数组，只能依次传入：年、月、日、时、分，或者时、分
     */
    public void setTimes(Integer... times) {
        if (times == null) {
            return;
        }
        if (times.length == 5) {
            mCurrentYear = times[0];
            mCurrentMonth = times[1];
            mCurrentDay = times[2];
            mCurrentHour = times[3];
            mCurrentMinute = times[4];
        } else {
            mCurrentHour = times[0];
            mCurrentMinute = times[1];
        }
    }

    /**
     * [设置时间选择结果监听器]<BR>
     * [功能详细描述]
     *
     * @param listener 监听器对象
     */
    public void setOnTimePickedListener(OnTimePickedListener listener) {
        mPickedListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timepicker_dialog);

        initTime();
        initView();
    }

    /**
     * [初始化时间]<BR>
     * [功能详细描述]
     */
    private void initTime() {
        currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        if (mCurrentYear < 0) {
            mCurrentYear = date.getYear();
        }
        if (mCurrentMonth < 0) {
            mCurrentMonth = date.getMonth();
        }
        if (mCurrentDay < 0) {
            mCurrentDay = date.getDay();
        }
        if (mCurrentHour < 0) {
            mCurrentHour = date.getHours();
        }
        if (mCurrentMinute < 0) {
            mCurrentMinute = date.getMinutes();
        }
    }

    /**
     * 初始化布局
     * [一句话功能简述]<BR>
     * [功能详细描述]
     */
    private void initView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(mTitleRes);
        mDatePicker = (DatePicker) findViewById(R.id.date_picker);
        mTimePicker = (TimePicker) this.findViewById(R.id.time_picker);

        // 初始化日期
        if (mNeedPickDate) {
            mDatePicker.setVisibility(View.VISIBLE);
            mTimePicker.setVisibility(View.VISIBLE);
            mTimePicker.setIs24HourView(true);
            mTimePicker.setCurrentHour(mCurrentHour);
            mTimePicker.setCurrentMinute(mCurrentMinute);
        } else {
            mDatePicker.setVisibility(View.GONE);
            mTimePicker.setVisibility(View.VISIBLE);
            // 初始化时间
            mTimePicker.setIs24HourView(true);
            mTimePicker.setCurrentHour(mCurrentHour);
            mTimePicker.setCurrentMinute(mCurrentMinute);
        }

        // 初始化按钮
        mConfirmButton = (Button) this.findViewById(R.id.dialog_button_ok);
        mCancelButton = (Button) this.findViewById(R.id.dialog_button_cancel);
        //设置监听事件
        bindListener();
    }

    /**
     * 设置Button监听事件
     * [一句话功能简述]<BR>
     * [功能详细描述]
     */
    private void bindListener() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mNeedPickDate) {
                    mCurrentYear = mDatePicker.getYear();
                    mCurrentMonth = mDatePicker.getMonth();
                    mCurrentDay = mDatePicker.getDayOfMonth();
                    mCurrentHour = mTimePicker.getCurrentHour();
                    mCurrentMinute = mTimePicker.getCurrentMinute();
                } else {
                    mCurrentHour = mTimePicker.getCurrentHour();
                    mCurrentMinute = mTimePicker.getCurrentMinute();
                }

                mPickedListener.onConfirmPick(mCurrentYear,
                        mCurrentMonth,
                        mCurrentDay,
                        mCurrentHour,
                        mCurrentMinute);
                dismiss();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
    }

    public void setMinDate(Long minDate) {
        if (mDatePicker != null) {
            if (minDate > (currentTimeMillis - 1000)) {
                mDatePicker.setMinDate(currentTimeMillis - 1000);
            } else {
                mDatePicker.setMinDate(minDate);
            }
        }
    }
}

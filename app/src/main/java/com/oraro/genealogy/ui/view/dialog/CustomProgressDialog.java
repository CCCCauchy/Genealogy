package com.oraro.genealogy.ui.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.oraro.genealogy.R;

/**
 * Created by Administrator on 2016/10/14.
 */
public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgess(String message) {
        setCancelable(true);   //设置点击框外消失
        show();
        Window window = getWindow();
        window.setContentView(R.layout.custom_progress_layout);   //自定义的layout样式
        if(message != null){
            TextView progress_text = (TextView) window.findViewById(R.id.progress_text);
            progress_text.setText(message);
        }
    }

}

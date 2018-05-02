package com.cbt.main.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.ViewGroup;

import com.cbt.main.R;

/**
 * Created by vigorous on 17/12/28.
 *
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
        initUI();
    }

    public LoadingDialog(@NonNull Context context, int theme) {
        super(context, theme);
        initUI();
    }


    public void initUI() {
        setContentView(R.layout.dialog_loading);
        getWindow().getAttributes().gravity = Gravity.CENTER;
        getWindow().getAttributes().width = ViewGroup.LayoutParams.WRAP_CONTENT;


    }

//    private void closeDialog(){
//        overridePendingTransition(R.anim.slide_out_down, 0);
//        finish();
//    }

}

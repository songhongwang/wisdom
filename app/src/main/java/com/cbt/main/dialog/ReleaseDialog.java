package com.cbt.main.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.activity.BaseActivity;
import com.cbt.main.activity.ReleaseActivity;

import butterknife.BindView;

/**
 * Created by vigorous on 17/12/28.
 *
 */

public class ReleaseDialog extends BaseActivity {

    @BindView(R.id.iv_back_area)
    ImageView mIvBackArea;
    @BindView(R.id.btn_item2)
    Button mBtnItem2;
    @BindView(R.id.btn_item3)
    Button mBtnItem3;
    @BindView(R.id.btn_item4)
    Button mBtnItem4;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.dialog_release);
    }

    @Override
    public void initUI() {

        getWindow().getAttributes().gravity = Gravity.BOTTOM;
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        overridePendingTransition(R.anim.slide_in_up, 0);

        mBtnItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReleaseDialog.this, ReleaseActivity.class);
                intent.putExtra("title", "灾情上报");
                startActivity(intent);
                finish();
            }
        });
        mBtnItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReleaseDialog.this, ReleaseActivity.class);
                intent.putExtra("title", "农情上报");
                startActivity(intent);
                finish();
            }
        });

        mBtnItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
            }
        });
        mIvBackArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
            }
        });
    }

    private void closeDialog(){
        overridePendingTransition(R.anim.slide_out_down, 0);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeDialog();
    }
}

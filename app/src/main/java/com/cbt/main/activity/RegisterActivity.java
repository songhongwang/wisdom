package com.cbt.main.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cbt.main.R;

import butterknife.BindView;

/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
@SuppressWarnings("deprecation")
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.et_pwd2)
    EditText mEtPwd2;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initUI() {
        final CountDownTimer countDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvGetCode.setText(String.valueOf(millisUntilFinished / 1000) + "s");
                mTvGetCode.setClickable(false);
                mTvGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_gray));
            }

            @Override
            public void onFinish() {
                mTvGetCode.setText("获取验证码");
                mTvGetCode.setClickable(true);
                mTvGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rs_select_btn_blue));
            }
        };

        mTvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countDownTimer.start();
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });


    }

    private void register() {
        String phone = mEtPhone.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String pwd2 = mEtPwd2.getText().toString().trim();

//        ApiClient.getInstance().getBasicService()
    }


}

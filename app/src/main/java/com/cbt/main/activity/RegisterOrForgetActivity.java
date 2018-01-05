package com.cbt.main.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.BaseModel;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.CommonCallBack;

import butterknife.BindView;
import retrofit2.Call;

/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
@SuppressWarnings("deprecation")
public class RegisterOrForgetActivity extends BaseActivity {
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
        String title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);

        mIvFinish.setVisibility(View.GONE);
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
                mTvGetCode.setBackgroundDrawable(getResources().getDrawable(R.drawable.rect_corner2_blue));
            }
        };

        mTvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = mEtPhone.getText().toString().trim();
                if(phone.length() < 11){
                    ToastUtils.show(RegisterOrForgetActivity.this, "手机号错误");
                    return;
                }

                ApiClient.getInstance().getBasicService().getCode(phone).enqueue(new CommonCallBack<Object>() {
                    @Override
                    public void onCResponse(Call<BaseModel<Object>> call, BaseModel<Object> response) {
                        ToastUtils.show(RegisterOrForgetActivity.this, "验证码已发送");

                        countDownTimer.start();
                    }

                    @Override
                    public void onCFailure(Call<BaseModel<Object>> call, Throwable t) {
                        ToastUtils.show(RegisterOrForgetActivity.this, "验证码发送失败");
                    }

                    @Override
                    public void onErrorMessage(String errorMessage) {
                        super.onErrorMessage(errorMessage);
                        ToastUtils.show(RegisterOrForgetActivity.this, errorMessage);
                    }
                });
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
            }
        });


    }

    private void checkInput() {
        String phone = mEtPhone.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String pwd2 = mEtPwd2.getText().toString().trim();

        if(phone.length() < 11){
            ToastUtils.show(this, "手机号不正确");
            return;
        }

        if(code.length() < 3){
            ToastUtils.show(this, "验证码不正确");
            return;
        }
        if(pwd.length() < 6 || pwd2.length() < 6){
            ToastUtils.show(this, "密码不正确");
            return;
        }
        if(!pwd.equals(pwd2)){
            ToastUtils.show(this, "两次输入密码不一致");
            return;
        }

        if(mTvTitle.getText().toString().trim().equals("注册")){
            register(phone, pwd, code);
        }else {
            forgetPwd(phone, pwd, code);
        }
    }

    private void register(String phone, String pwd, String code){

        ApiClient.getInstance().getBasicService().regist(phone, pwd, code).enqueue(new CommonCallBack<Object>() {
            @Override
            public void onCResponse(Call<BaseModel<Object>> call, BaseModel<Object> response) {
                ToastUtils.show(RegisterOrForgetActivity.this, "注册成功");
                finish();
            }

            @Override
            public void onCFailure(Call<BaseModel<Object>> call, Throwable t) {
                ToastUtils.show(RegisterOrForgetActivity.this, "注册失败");
            }
            @Override
            public void onErrorMessage(String errorMessage) {
                super.onErrorMessage(errorMessage);
                ToastUtils.show(RegisterOrForgetActivity.this, errorMessage);
            }
        });
    }

    private void forgetPwd(String phone, String pwd, String code){
        ApiClient.getInstance().getBasicService().forgotPwd(phone, pwd, code).enqueue(new CommonCallBack<Object>() {
            @Override
            public void onCResponse(Call<BaseModel<Object>> call, BaseModel<Object> response) {
                ToastUtils.show(RegisterOrForgetActivity.this, "密码修改成功");
                finish();
            }

            @Override
            public void onCFailure(Call<BaseModel<Object>> call, Throwable t) {
                ToastUtils.show(RegisterOrForgetActivity.this, "密码修改失败");
            }
            @Override
            public void onErrorMessage(String errorMessage) {
                super.onErrorMessage(errorMessage);
                ToastUtils.show(RegisterOrForgetActivity.this, errorMessage);
            }
        });
    }

}

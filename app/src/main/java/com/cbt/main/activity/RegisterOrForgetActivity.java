package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.BaseModel;
import com.cbt.main.utils.PhoneNumberUtil;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.CommonCallBack;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import me.nereo.imagechoose.MultiImageSelectorActivity;
import retrofit2.Call;

/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
@SuppressWarnings("deprecation")
public class RegisterOrForgetActivity extends BaseActivity {
    private int REQUEST_IMAGE = 3;
    private CropCircleTransformation mCropCircleTransformation;

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
    @BindView(R.id.iv_crops)
    ImageView mIvAdatar;
    @BindView(R.id.et_nick_name)
    EditText mEtNiCheng;

    private String imgfile;

    boolean hadGettingCode = false;

    String mTitleStr;
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



    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initUI() {
        mCropCircleTransformation = new CropCircleTransformation();
        mTitleStr = getIntent().getStringExtra("title");
        mTvTitle.setText(mTitleStr);

        mIvFinish.setVisibility(View.GONE);

        if("注册".equals(mTitleStr)){
            mBtnRegister.setText("注册");
        }else{
            mBtnRegister.setText("提交");
        }



        mTvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hadGettingCode = true;
                String phone = mEtPhone.getText().toString().trim();
                if(phone.length() < 11){
                    ToastUtils.show(RegisterOrForgetActivity.this, "手机号错误");
                    return;
                }

                if("注册".equals(mTitleStr)){
                    netRegisterAuthCode(phone);
                }else{
                    netForgetAuthCode(phone);
                }

            }
        });
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInput();
            }
        });
        mIvAdatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterOrForgetActivity.this, MultiImageSelectorActivity.class);
                // whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
    }

    private void netForgetAuthCode(String phone){
        ApiClient.getInstance().getBasicService(this).forgotPwd(phone).enqueue(new CommonCallBack<Object>() {
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
    private void netRegisterAuthCode(String phone){
        ApiClient.getInstance().getBasicService(this).getCode(phone).enqueue(new CommonCallBack<Object>() {
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


    private void checkInput() {
        String nickName = mEtNiCheng.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String pwd2 = mEtPwd2.getText().toString().trim();

        if(TextUtils.isEmpty(nickName)){
            ToastUtils.show(this, "请输入昵称");
            return;
        }

        if(!PhoneNumberUtil.isChinaPhoneLegal(phone)){
            ToastUtils.show(this, "手机号不正确");
            return;
        }

        if(!hadGettingCode){
            ToastUtils.show(this, "获取验证码");
            return;
        }

        if(code.length() < 3){
            ToastUtils.show(this, "验证码不正确");
            return;
        }
        if(pwd.length() == 0 || pwd2.length() == 0){
            ToastUtils.show(this, "输入密码");
            return;
        }
        if(!pwd.equals(pwd2)){
            ToastUtils.show(this, "两次输入密码不一致");
            return;
        }

        if(mTvTitle.getText().toString().trim().equals("注册")){
            register(phone, pwd, code,nickName);
        }else {
            forgetPwd(phone, pwd, code, nickName);
        }
    }

    private void register(String phone, String pwd, String code, String nickName){

        ApiClient.getInstance().getBasicService(this).regist(phone, pwd, code).enqueue(new CommonCallBack<Object>() {
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

    private void forgetPwd(String phone, String pwd, String code, String nickName){
        ApiClient.getInstance().getBasicService(this).forgotPwd(phone, pwd, code).enqueue(new CommonCallBack<Object>() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                List<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

                updateUI(pathList);
            }
        }
    }

    private void updateUI(List<String> pathList) {
        if(pathList != null && pathList.size()>0){
            imgfile = pathList.get(0);
            File imageFile = new File(pathList.get(0));

            Picasso.with(this).load(imageFile).placeholder(R.drawable.login_default_icon)
                    .transform(mCropCircleTransformation)
                    .into(mIvAdatar);
        }else{
            mIvAdatar.setImageResource(R.drawable.login_default_icon);
        }
    }

}

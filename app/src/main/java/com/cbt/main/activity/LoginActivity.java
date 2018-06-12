package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.model.BaseModel;
import com.cbt.main.model.User;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.ClearWriteEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AMing on 16/1/15.
 * Company RongCloud
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = "LoginActivity";
    private static final int LOGIN = 5;
    private static final int GET_TOKEN = 6;
    private static final int SYNC_USER_INFO = 9;

    private ImageView mImg_Background;
    private ClearWriteEditText mPhoneEdit, mPasswordEdit;
    private String phoneString;
    private String passwordString;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);

        User login = SharedPreferencUtil.getLogin(this);
        if(login != null && !TextUtils.isEmpty(login.getState())){
            if (login.getState().equals("0"))
            {
                goToWanshan();
            }
            else
            {
                goToMain();
            }
        }

    }

    @Override
    public void initUI() {
        mPhoneEdit = (ClearWriteEditText) findViewById(R.id.de_login_phone);
        mPasswordEdit = (ClearWriteEditText) findViewById(R.id.de_login_password);
        Button mConfirm = (Button) findViewById(R.id.de_login_sign);
        TextView mRegister = (TextView) findViewById(R.id.de_login_register);
        TextView forgetPassword = (TextView) findViewById(R.id.de_login_forgot);
        forgetPassword.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mImg_Background = (ImageView) findViewById(R.id.de_img_backgroud);

        mPhoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 11) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.de_login_sign:
                phoneString = mPhoneEdit.getText().toString().trim();
                passwordString = mPasswordEdit.getText().toString().trim();

                if (TextUtils.isEmpty(phoneString)) {
                    mPhoneEdit.setShakeAnimation();
                    return;
                }

                if (TextUtils.isEmpty(passwordString)) {
                    mPasswordEdit.setShakeAnimation();
                    return;
                }
                if (passwordString.contains(" ")) {
                    mPasswordEdit.setShakeAnimation();
                    return;
                }
               loginServer(phoneString, passwordString);
                break;
            case R.id.de_login_register:
                Intent intent = new Intent(this, RegisterOrForgetActivity.class);
                intent.putExtra("title", "注册");
                startActivityForResult(intent, 1);
                break;
            case R.id.de_login_forgot:
                Intent intent2 = new Intent(this, RegisterOrForgetActivity.class);
                intent2.putExtra("title", "忘记密码");
                startActivityForResult(intent2, 2);
                break;
        }
    }

    private void loginServer(String phone, String pwd){
//        User user = new User();
//        user.setUid("cd_18600211553");
//        user.setUsname("中国人");
//        user.setIcon("/baidu.png");
//        user.setTelphone(phoneString);
//        user.setPassword(passwordString);
//        SharedPreferencUtil.saveLogin(LoginActivity.this, user);
//        goToMain();
        ApiClient.getInstance().getBasicService(this).login(phone, pwd).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

//                User user = new User();
//                user.setTelphone(phoneString);
//                user.setPassword(passwordString);
                try
                {
                    User user = response.body();
                    SharedPreferencUtil.saveLogin(LoginActivity.this, user);
                    if (user.getState().equals("0"))
                    {
                        goToWanshan();
                    }
                    else
                    {
                        goToMain();
                    }
                }
                catch (Exception e)
                {
                    ToastUtils.show(LoginActivity.this, "用户名或密码错误");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                ToastUtils.show(LoginActivity.this, "登录失败");

//                Intent intent = new Intent(LoginActivity.this, PerfactAccountActivity.class);
//                startActivity(intent);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && data != null) {
            String phone = data.getStringExtra("phone");
            String password = data.getStringExtra("password");
            mPhoneEdit.setText(phone);
            mPasswordEdit.setText(password);
        } else if (data != null && requestCode == 1) {
            String phone = data.getStringExtra("phone");
            String password = data.getStringExtra("password");
            String id = data.getStringExtra("id");
            String nickname = data.getStringExtra("nickname");
//            if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(nickname)) {
//                mPhoneEdit.setText(phone);
//                mPasswordEdit.setText(password);
//                editor.putString(SealConst.SEALTALK_LOGING_PHONE, phone);
//                editor.putString(SealConst.SEALTALK_LOGING_PASSWORD, password);
//                editor.putString(SealConst.SEALTALK_LOGIN_ID, id);
//                editor.putString(SealConst.SEALTALK_LOGIN_NAME, nickname);
//                editor.apply();
//            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void goToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void goToWanshan() {
        startActivity(new Intent(LoginActivity.this, PerfactAccountActivity.class));
        finish();
    }
}

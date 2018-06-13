package com.cbt.main.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.cbt.main.R;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.User;
import com.cbt.main.model.event.EventPublishSuccess;
import com.cbt.main.model.event.EventUpdateUser;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.rong.eventbus.EventBus;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import me.nereo.imagechoose.MultiImageSelectorActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 18/4/12.
 */

public class MyProfileActivity extends BaseActivity {
    private int REQUEST_IMAGE = 3;
    private CropCircleTransformation mCropCircleTransformation;
    User mUser;


    @BindView(R.id.iv_crops)
    ImageView mIvAdatar;
    @BindView(R.id.et_nicheng)
    EditText mEtNiCheng;
    @BindView(R.id.tv_nan)
    TextView mTvNan;
    @BindView(R.id.tv_nv)
    TextView mTvNv;
    @BindView(R.id.tv_shengri)
    TextView mTvShengri;
    @BindView(R.id.rl_shengri)
    View mVBirthday;
    private String imgfile;
    TimePickerView pvTime;
    private boolean mChangeAvatar;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void initUI() {
        mUser = (User) getIntent().getSerializableExtra("user");
        mCropCircleTransformation = new CropCircleTransformation();

        mTvTitle.setText("修改用户信息");
        mIvFinish.setImageResource(R.drawable.icon_complete);

        Picasso.with(this).load(Constants.getBaseUrl() + mUser.getIcon()).placeholder(R.drawable.login_default_icon)
                .transform(mCropCircleTransformation)
                .into(mIvAdatar);

        mIvAdatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyProfileActivity.this, MultiImageSelectorActivity.class);
                // whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 1);
                // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
        if(!TextUtils.isEmpty(mUser.getIcon())){
            Glide.with(MyProfileActivity.this).load(Constants.getBaseUrl() + mUser.getIcon()).into(mIvAdatar);
        }
        if(!TextUtils.isEmpty(mUser.getBirthday())){
            mTvShengri.setText(mUser.getBirthday());
        }

        mTvNan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser.setSex("男");
                refreshUI();
            }
        });
        mTvNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUser.setSex("女");
                refreshUI();
            }
        });

        TimePickerView.Builder builder = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                String birthday = simpleDateFormat.format(date);
                mTvShengri.setText(birthday);
                mUser.setBirthday(birthday);
            }
        });
        boolean[] type = new boolean[]{true, true, true, false, false, false};//显示类型 默认全部显示
        builder.setType(type);
        builder.setLabel("年","月","日","","","");//默认设置为年月日时分秒
        pvTime = new TimePickerView(builder);

        mVBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show();
            }
        });

        mIvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });

        refreshUI();
    }

    private void refreshUI(){
        if(mUser == null){
            return;
        }
        if(!TextUtils.isEmpty(mUser.getUname())){
            mEtNiCheng.setText(mUser.getUname());
            Editable etext = mEtNiCheng.getText();
            Selection.setSelection(etext, etext.length());
        }

        if(!TextUtils.isEmpty(mUser.getSex())){
            Drawable checked =getResources().getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_015);
            Drawable unchecked  =getResources().getDrawable(R.drawable.abc_btn_radio_to_on_mtrl_000);
            checked.setBounds(0,0,checked.getIntrinsicWidth(),checked.getIntrinsicHeight());
            unchecked.setBounds(0,0,checked.getIntrinsicWidth(),checked.getIntrinsicHeight());
            if("男".equals(mUser.getSex())){
                mTvNan.setCompoundDrawables(checked, null ,null, null);
                mTvNv.setCompoundDrawables(unchecked, null ,null, null);
            }else{
                mTvNan.setCompoundDrawables(unchecked, null ,null, null);
                mTvNv.setCompoundDrawables(checked, null ,null, null);
            }
        }



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
            mChangeAvatar = true;
            File imageFile = new File(pathList.get(0));

            Picasso.with(this).load(imageFile).placeholder(R.drawable.login_default_icon)
                    .transform(mCropCircleTransformation)
                    .into(mIvAdatar);
            mUser.setIcon(imageFile.getAbsolutePath());
        }else{
            mIvAdatar.setImageResource(R.drawable.login_default_icon);
        }
    }

    private void commit() {
        String city = GlobalApplication.mLocationData.city;
        String province = GlobalApplication.mLocationData.province;
        String country = GlobalApplication.mLocationData.addr;
        if (mEtNiCheng.getText().equals(""))
        {
            ToastUtils.show(MyProfileActivity.this, "昵称不能为空！");
        }
        else
        {
            mUser.setUsname(mEtNiCheng.getText().toString());
            mUser.setUname(mEtNiCheng.getText().toString());
        }
        MultipartBody requestBody = null;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if(mChangeAvatar) {
            builder.setType(MultipartBody.FORM)
                    .addFormDataPart("icon", "userAvatar", RequestBody.create(MediaType.parse("image/*"), new File(mUser.getIcon())));
        }
        else {
            builder.setType(MultipartBody.FORM).addFormDataPart("icon", "");
        }
        requestBody = builder.build();


            ApiClient.getInstance().getBasicService(this).adduserdetail(province, city,country, mUser.getSex(), mUser.getBirthday(), mUser.getUname(),requestBody).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User usenew = response.body();
                    ToastUtils.show(MyProfileActivity.this, "用户信息更新成功");
                    SharedPreferencUtil.saveLogin(MyProfileActivity.this, usenew);
                    EventBus.getDefault().post(new EventUpdateUser());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    ToastUtils.show(MyProfileActivity.this, "更新失败");

                }
            });
//        }else{
//            ApiClient.getInstance().getBasicService(this).adduserdetail(province, city,country, mUser.getSex(), mUser.getBirthday(), mUser.getUname()).enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//                    ToastUtils.show(MyProfileActivity.this, "用户信息更新成功");
//
//                }
//
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//                    ToastUtils.show(MyProfileActivity.this, "更新失败");
//
//                }
//            });
//        }


    }
}

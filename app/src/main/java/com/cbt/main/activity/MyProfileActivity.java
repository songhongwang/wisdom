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
import com.cbt.main.R;
import com.cbt.main.model.User;
import com.cbt.main.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import me.nereo.imagechoose.MultiImageSelectorActivity;

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

    TimePickerView pvTime;
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
            mEtNiCheng.setText(mUser.getUsname());
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
            File imageFile = new File(pathList.get(0));

            Picasso.with(this).load(imageFile).placeholder(R.drawable.login_default_icon)
                    .transform(mCropCircleTransformation)
                    .into(mIvAdatar);
        }else{
            mIvAdatar.setImageResource(R.drawable.login_default_icon);
        }
    }

    private void commit(){
//        ApiClient.getInstance().getBasicService(this).replyFeed()
        ToastUtils.show(this, "调用修改用户信息接口");
    }
}

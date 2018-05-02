package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.cbt.main.R;
import com.cbt.main.utils.net.Constants;
import com.squareup.picasso.Picasso;

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

    @BindView(R.id.iv_crops)
    ImageView mIvAdatar;

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void initUI() {
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

        mIvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            Picasso.with(this).load(pathList.get(0)).placeholder(R.drawable.login_default_icon)
                    .transform(mCropCircleTransformation)
                    .into(mIvAdatar);
        }else{
            mIvAdatar.setImageResource(R.drawable.login_default_icon);
        }
    }
}

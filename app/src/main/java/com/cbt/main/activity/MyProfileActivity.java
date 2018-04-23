package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cbt.main.R;

import java.util.List;

import me.nereo.imagechoose.MultiImageSelectorActivity;

/**
 * Created by vigorous on 18/4/12.
 */

public class MyProfileActivity extends BaseActivity {
    private int REQUEST_IMAGE = 3;
    ImageView mIvAvatar;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_profile);
    }

    @Override
    public void initUI() {
        mIvFinish.setImageResource(R.drawable.icon_complete);

        mIvAvatar = (ImageView) findViewById(R.id.iv_crops);

        mIvAvatar.setOnClickListener(new View.OnClickListener() {
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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                List<String> pathList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

                String s = (String) pathList.get(0);
                if(!s.equals("holder")){
                    Glide.with(MyProfileActivity.this).load(s).into(mIvAvatar);
                }
            }
        }
    }
}

package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.adapter.ReleaseActAdapter;
import com.cbt.main.app.GlobalApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.nereo.imagechoose.MultiImageSelectorActivity;

/**
 * Created by vigorous on 17/12/27.
 * 发布页面
 */

public class ReleaseActivity extends BaseActivity{

    @BindView(R.id.mGridView)
    GridView mGridView;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    private ReleaseActAdapter mReleaseActAdapter;
    private int REQUEST_IMAGE = 3;

    private List<String> mDatas = new ArrayList<>();

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_release);
    }

    @Override
    public void initUI() {
        mTvTitle.setText("灾情上报");
        mReleaseActAdapter = new ReleaseActAdapter(this, mDatas);
        mGridView.setAdapter(mReleaseActAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = mDatas.get(i);
                if(s.equals("holder")){
                    Intent intent = new Intent(ReleaseActivity.this, MultiImageSelectorActivity.class);
                    // whether show camera
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                    // max select image amount
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                    // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                    intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
                    startActivityForResult(intent, REQUEST_IMAGE);
                }else{
                    // 查看图片
                }
            }
        });
        updateUI(null);

        GlobalApplication.mApp.updateLocation();
        mTvLocation.setText(GlobalApplication.mLocationData.addr);
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

    private void updateUI(List<String> pathList ){
        mDatas.clear();
        if(pathList == null){
            mDatas.add("holder");
        }else{
            for(String path : pathList){
                mDatas.add(path);
            }
            if(pathList.size() < 9){
                mDatas.add("holder");
            }
        }
        mReleaseActAdapter.resetData(mDatas);
        mReleaseActAdapter.notifyDataSetChanged();

    }
}

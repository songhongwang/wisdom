package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cbt.main.R;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.Weather7DaysForcast;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 18/4/1.
 * 完善个人信息
 */

public class PerfactAccountActivity extends BaseActivity{

    @BindView(R.id.et_farm_name) EditText mEtFramName;
    @BindView(R.id.et_farm_zuowu) EditText mEtFramZuowu;
    @BindView(R.id.btn_commit) Button mBtnCommit;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_perfact_account);
        ButterKnife.bind(this);
    }

    @Override
    public void initUI() {

        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               commit();
            }
        });

    }

    private void commit(){

        String city = GlobalApplication.mLocationData.city;
        String province = GlobalApplication.mLocationData.province;
        String country = GlobalApplication.mLocationData.addr;


        String fName = "旺旺Farm";
        String points = "43.25,116.22";
        String zuowulist = "1,3;2,8";
        ApiClient.getInstance().getBasicService(this).perfactAccount(fName, points, zuowulist, city, province, country).enqueue(new Callback<Weather7DaysForcast>() {
            @Override
            public void onResponse(Call<Weather7DaysForcast> call, Response<Weather7DaysForcast> response) {
                ToastUtils.show(PerfactAccountActivity.this, "aa");
            }

            @Override
            public void onFailure(Call<Weather7DaysForcast> call, Throwable t) {

            }
        });

    }
}

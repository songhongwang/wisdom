package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.cbt.main.R;
import com.cbt.main.adapter.PerfactAccountAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.Weather7DaysForcast;
import com.cbt.main.model.ZuoWuModel;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;

import java.util.ArrayList;
import java.util.List;

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

    EditText mEtFramName;
    EditText mEtFramAear;
    @BindView(R.id.listview) ListView mLv;
    private PerfactAccountAdapter<ZuoWuModel> mPerfactAccountAdapter;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_perfact_account);
        ButterKnife.bind(this);
    }

    @Override
    public void initUI() {

        mIvFinish.setImageResource(R.drawable.more_icon_release);

        View headerView = View.inflate(this, R.layout.header_perfact_account, null);
        View footerView = View.inflate(this, R.layout.footer_perfact_account, null);
        mEtFramName = (EditText) headerView.findViewById(R.id.et_farm_name);
        mEtFramAear = (EditText) headerView.findViewById(R.id.et_farm_area);
        headerView.findViewById(R.id.tv_get_farm_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfactAccountActivity.this, SelectMapLocationActivity.class);
                startActivity(intent);
            }
        });
        footerView.findViewById(R.id.tv_add_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZuoWuModel zuoWuModel = new ZuoWuModel("", "");
                mPerfactAccountAdapter.addItem(zuoWuModel);
            }
        });

        mLv.addHeaderView(headerView);
        mLv.addFooterView(footerView);


        List<ZuoWuModel> dataList = new ArrayList<>();
        dataList.add(new ZuoWuModel("", ""));

        mPerfactAccountAdapter = new PerfactAccountAdapter<>(dataList, this);
        mLv.setAdapter(mPerfactAccountAdapter);
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

package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.fragment.WeatherLineFragment;
import com.cbt.main.model.IndexModel;
import com.cbt.main.model.Weather7DaysForcast;
import com.cbt.main.model.WeatherModel;
import com.cbt.main.model.WeatherModel2;
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
 * Created by vigorous on 18/3/31.
 */

public class WeatherForcastActivity extends BaseActivity {
    WeatherLineFragment mWeatherLineFragment;
    IndexModel mIndexModel;
    @BindView(R.id.tv_qiya)
    TextView mTvQiYa;
    @BindView(R.id.tv_fengsu)
    TextView mTvFengSu;
    @BindView(R.id.tv_fabu)
    TextView mTvFaBu;
    @BindView(R.id.tv_tian)
    TextView mTvTian;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_weather_forcast);
        ButterKnife.bind(this);
    }

    @Override
    public void initUI() {
        mIvFinish.setVisibility(View.GONE);

        mIndexModel = (IndexModel) getIntent().getSerializableExtra("weather");
        if(mIndexModel == null){
            ToastUtils.show(this, "天气预报数据未准备好");
            finish();
            return;
        }
        mTvQiYa.setText(mIndexModel.qiya);
        mTvFengSu.setText(mIndexModel.fengxiang);
        mTvFaBu.setText(mIndexModel.ybshijian);
        mTvTian.setText("今天 " + mIndexModel.ybtianqi);

        mWeatherLineFragment = new WeatherLineFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_container,mWeatherLineFragment);
        transaction.commitAllowingStateLoss();

        getWeather();
    }

    private void getWeather() {
        String city = GlobalApplication.mLocationData.city;
        String province = GlobalApplication.mLocationData.province;
        String country = GlobalApplication.mLocationData.addr;


        ApiClient.getInstance().getBasicService(this).getWeatherForcast(province, city, country).enqueue(new Callback<Weather7DaysForcast>() {
            @Override
            public void onResponse(Call<Weather7DaysForcast> call, Response<Weather7DaysForcast> response) {
                //ToastUtils.show(WeatherForcastActivity.this, response.body().getTodaytianqi());

                List<WeatherModel> goodList = new ArrayList<>();
                List<WeatherModel2> d7list = response.body().getD7list();
                for(int i = 0; i< d7list.size();i ++){

                    WeatherModel good = WeatherModel2.convert(d7list.get(i), i);
                    goodList.add(good);
                }

                mWeatherLineFragment.updateUI(goodList);


            }

            @Override
            public void onFailure(Call<Weather7DaysForcast> call, Throwable t) {

            }
        });
    }
}

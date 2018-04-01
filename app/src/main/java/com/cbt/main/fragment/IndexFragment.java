package com.cbt.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.activity.WeatherForcastActivity;
import com.cbt.main.adapter.IndexProductAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.engin.SceneSurfaceView;
import com.cbt.main.model.BaseModel;
import com.cbt.main.model.IndexModel;
import com.cbt.main.model.Weather7DaysForcast;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.utils.net.CommonCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 16/1/4.
 * 首页
 */
public class IndexFragment extends BaseFragment {

    @Nullable@BindView(R.id.listView)
    ListView mLv;
    private IndexProductAdapter mIndexProductAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initUI() {
        mLv = (ListView) mRootView.findViewById(R.id.listView) ;
        mRootView.findViewById(R.id.tv_product_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mRootView.findViewById(R.id.tv_weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WeatherForcastActivity.class);
                startActivity(intent);
            }
        });


        mTvTitle.setText("北京市海淀区");
        List<String> dataList = new ArrayList<>();
        dataList.add("aaa");
        dataList.add("aaa");
        dataList.add("aaa");
        dataList.add("aaa");
        dataList.add("aaa");
        mIndexProductAdapter = new IndexProductAdapter(dataList, getActivity());
        mLv.setAdapter(mIndexProductAdapter);

        getData();
        getWeather();
    }

    @Override
    protected void lazyLoad() {

    }

    private void getData() {
        String city = GlobalApplication.mLocationData.city;
        String province = GlobalApplication.mLocationData.province;
        String country = GlobalApplication.mLocationData.addr;


        ApiClient.getInstance().getBasicService(getContext()).getIndex(province, city, country).enqueue(new Callback<IndexModel>() {
            @Override
            public void onResponse(Call<IndexModel> call, Response<IndexModel> response) {
                ToastUtils.show(getContext(), response.body().nongli);

            }

            @Override
            public void onFailure(Call<IndexModel> call, Throwable t) {

            }
        });
    }
    private void getWeather() {
        String city = GlobalApplication.mLocationData.city;
        String province = GlobalApplication.mLocationData.province;
        String country = GlobalApplication.mLocationData.addr;


        ApiClient.getInstance().getBasicService(getContext()).getWeatherForcast(province, city, country).enqueue(new Callback<Weather7DaysForcast>() {
            @Override
            public void onResponse(Call<Weather7DaysForcast> call, Response<Weather7DaysForcast> response) {
                ToastUtils.show(getContext(), response.body().getTodaytianqi());

            }

            @Override
            public void onFailure(Call<Weather7DaysForcast> call, Throwable t) {

            }
        });
    }
}

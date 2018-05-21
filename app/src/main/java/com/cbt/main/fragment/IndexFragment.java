package com.cbt.main.fragment;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cbt.main.R;
import com.cbt.main.activity.MainActivity;
import com.cbt.main.activity.MoreProductActivity;
import com.cbt.main.activity.ReleaseActivity;
import com.cbt.main.activity.WeatherForcastActivity;
import com.cbt.main.adapter.IndexProductAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.IndexModel;
import com.cbt.main.model.IndexProductModel;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.picker.JsonBean;
import com.cbt.main.view.picker.JsonFileReader;
import com.google.gson.Gson;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vigorous on 16/1/4.
 * 首页
 */
public class IndexFragment extends BaseFragment {

    // test commit
    private ListView mLv;
    private TextView mTvBigWendu;
    private TextView mTvShiDu;
    private TextView mTvQiYa;
    private TextView mTvFengSu;
    private TextView mTvFaBu;
    private TextView mTvTodayWeather;
    private TextView mTvTempr;
    private TextView mTvFeng;
    private IndexProductAdapter mIndexProductAdapter;
    private IndexModel mIndexModel;
    private TwinklingRefreshLayout mTwinklingRefreshLayout;

    // 当前地区
    String city;
    String province;
    String country;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexModel = null;
    }

    @Override
    public void initUI() {
        city = GlobalApplication.mLocationData.city;
        province = GlobalApplication.mLocationData.province;
        country = GlobalApplication.mLocationData.addr;

        mLv = (ListView) mRootView.findViewById(R.id.listView) ;
        mRootView.findViewById(R.id.tv_product_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MoreProductActivity.class);
                startActivity(intent);
            }
        });
        mRootView.findViewById(R.id.tv_weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WeatherForcastActivity.class);
                intent.putExtra("weather", mIndexModel);
                startActivity(intent);
            }
        });

        mRootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateTitleLocation();
            }
        }, 500);

        mTvBigWendu = (TextView) mRootView.findViewById(R.id.tv_big_wendu);
        mTvShiDu = (TextView) mRootView.findViewById(R.id.tv_shidu);
        mTvQiYa = (TextView) mRootView.findViewById(R.id.tv_qiya);
        mTvFengSu = (TextView) mRootView.findViewById(R.id.tv_fengsu);
        mTvFaBu = (TextView) mRootView.findViewById(R.id.tv_fabu);
        mTvTodayWeather = (TextView) mRootView.findViewById(R.id.tv_today_weather);
        mTvTempr= (TextView) mRootView.findViewById(R.id.tv_temper);
        mTvFeng= (TextView) mRootView.findViewById(R.id.tv_wind);

//        mTvTitle.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getData();
//            }
//        },200);
//        mTvTitle.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                getProductList();
//            }
//        }, 400);

        mTwinklingRefreshLayout = (TwinklingRefreshLayout) mRootView.findViewById(R.id.refreshLayout);
        ProgressLayout headerView = new ProgressLayout(getActivity());
        mTwinklingRefreshLayout.setHeaderView(headerView);
        mTwinklingRefreshLayout.setEnableLoadmore(false);
        mTwinklingRefreshLayout.setOverScrollBottomShow(false);
        mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                refreshLayout.finishRefreshing();
                getData();
            }
        });
        mTvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //条件选择器
                OptionsPickerView pvOptions = new  OptionsPickerView.Builder(getActivity(), new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        province = options1Items.get(options1).getPickerViewText();
                        city = options2Items.get(options1).get(option2);
                        country = options3Items.get(options1).get(option2).get(options3);
                        String tx = options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2).get(options3);
                        mTvTitle.setText(tx);
                        getData();
                    }
                }).build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                initJsonData();
            }
        }).start();
    }

    @Override
    protected void lazyLoad() {
        if(mIndexModel == null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData();
                }
            }, 200);

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getProductList();
            }
        }, 600);
    }

    private void updateTitleLocation(){
        city = GlobalApplication.mLocationData.city;
        province = GlobalApplication.mLocationData.province;
        country = GlobalApplication.mLocationData.addr;

        if(!TextUtils.isEmpty(city) && !TextUtils.isEmpty(country) && mTvTitle != null){
            mTvTitle.setText(city + country);
        }
    }

    private void getData() {
        city = GlobalApplication.mLocationData.city;
        province = GlobalApplication.mLocationData.province;
        country = GlobalApplication.mLocationData.addr;

        if(TextUtils.isEmpty(province)){
            GlobalApplication.mApp.updateLocation();
            ToastUtils.show(getContext(), "请在设置中添加位置权限");
            return;
        }

        updateTitleLocation();

        ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getIndex(province, city, country).enqueue(new Callback<IndexModel>() {
            @Override
            public void onResponse(Call<IndexModel> call, Response<IndexModel> response) {
                mIndexModel = response.body();
                if(TextUtils.isEmpty(mIndexModel.wendu) || mIndexModel == null){
                    ToastUtils.show(getActivity(), "数据异常，请稍后重试");
                    return;
                }

                mTvBigWendu.setText(mIndexModel.wendu);
                mTvShiDu.setText("相对湿度" + mIndexModel.shidu);
                mTvQiYa.setText("气压" + mIndexModel.qiya);
                mTvFengSu.setText("风向/风力\n" + mIndexModel.fengxiang + "/" + mIndexModel.fengsu);

                mTvFaBu.setText(mIndexModel.nongli);

                mTvTodayWeather.setText(mIndexModel.ybtianqi);
                // 判断天气 变化背景动态
                updateBg(mIndexModel.ybtianqi);
                if (mIndexModel.ybzuigaowendu != null)
                {
                    mTvTempr.setText(mIndexModel.ybzuidiwendu + " - " + mIndexModel.ybzuigaowendu);
                }
                else
                {
                    mTvTempr.setText(mIndexModel.ybzuidiwendu);
                }
                mTvFeng.setText(mIndexModel.ybfengxiang + mIndexModel.ybfengsu);
                mTwinklingRefreshLayout.finishRefreshing();
            }

            @Override
            public void onFailure(Call<IndexModel> call, Throwable t) {
                mTwinklingRefreshLayout.finishRefreshing();

            }
        });
    }

    private void getProductList(){
        ApiClient.getInstance().getBasicService(GlobalApplication.mApp).getIndexProduct(0).enqueue(new Callback<List<IndexProductModel>>() {
            @Override
            public void onResponse(Call<List<IndexProductModel>> call, Response<List<IndexProductModel>> response) {

                List<IndexProductModel> dataList = response.body();
                mIndexProductAdapter = new IndexProductAdapter(dataList, getActivity());
                mLv.setAdapter(mIndexProductAdapter);

            }

            @Override
            public void onFailure(Call<List<IndexProductModel>> call, Throwable t) {

            }
        });
    }

    private void updateBg(final String weather){
        if(getActivity() instanceof MainActivity && (getActivity() != null)){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((MainActivity)getActivity()).sunshineOrRain(weather);
                }
            });
        }
    }

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(GlobalApplication.mApp, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
}

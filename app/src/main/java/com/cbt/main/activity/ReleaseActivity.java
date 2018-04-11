package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cbt.main.R;
import com.cbt.main.adapter.ReleaseActAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.picker.JsonBean;
import com.cbt.main.view.picker.JsonFileReader;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.nereo.imagechoose.MultiImageSelectorActivity;
import me.nereo.imagechoose.ShowActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

/**
 * Created by vigorous on 17/12/27.
 * 发布页面
 */

public class ReleaseActivity extends BaseActivity{

    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.mGridView)
    GridView mGridView;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_str_leixing)
    TextView mTvStrLeixing;
    @BindView(R.id.rl_location)
    RelativeLayout mRlLocation;
    @BindView(R.id.rl_leixing)
    RelativeLayout mRlLeixing;
    private ReleaseActAdapter mReleaseActAdapter;
    private int REQUEST_IMAGE = 3;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    List<String> mOptionLeixingList = new ArrayList<>();


    private List<String> mDatas = new ArrayList<>();

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_release);
    }

    @Override
    public void initUI() {
        String title = getIntent().getStringExtra("title");
        mTvTitle.setText(title);
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
                    Intent showIntent =new Intent(ReleaseActivity.this, ShowActivity.class);
                    showIntent.putExtra("path",s);
                    startActivity(showIntent);
                }
            }
        });
        updateUI(null);

        GlobalApplication.mApp.updateLocation();
        mTvLocation.setText(GlobalApplication.mLocationData.province + GlobalApplication.mLocationData.city + GlobalApplication.mLocationData.addr);
        initJsonData();
        mRlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //条件选择器
                OptionsPickerView pvOptions = new  OptionsPickerView.Builder(ReleaseActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText()
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2).get(options3);
                        Log.d("hahah", tx);
                        ToastUtils.show(ReleaseActivity.this, tx);
                    }
                }).build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();
            }
        });

        mIvFinish.setImageResource(R.drawable.icon_complete);
        mIvFinish.setVisibility(View.VISIBLE);
        mIvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
            }
        });
        mRlLeixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLeixingDialog();
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

    private void initJsonData() {   //解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        //  获取json数据
        String JsonData = JsonFileReader.getJson(this, "province_data.json");
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

    private void onLeixingDialog(){
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getPickerViewText()
//                                + options2Items.get(options1).get(option2)
//                                + options3Items.get(options1).get(option2).get(options3);
                Log.d("hahah", " " + options1);
                mTvStrLeixing.setText(mOptionLeixingList.get(options1));
            }
        }).build();
        mOptionLeixingList.add("水果类");
        mOptionLeixingList.add("粮食类");
        mOptionLeixingList.add("油料作物");
        mOptionLeixingList.add("蔬菜类");
        mOptionLeixingList.add("经济作物");

        pvOptions.setPicker(mOptionLeixingList);
        pvOptions.show();
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

    private void commit() {
        String content = mEtContent.getText().toString();

        final File img1 = new File(mDatas.get(0));

        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM)
                .addFormDataPart("content", content);

        for(int i = 0; i< mDatas.size(); i++){
            File file = new File(mDatas.get(i));
            builder.addFormDataPart("img" + (i+1), img1.getName(), RequestBody.create(MediaType.parse("image/*"), file));
        }
        MultipartBody requestBody = builder.build();


        String city = GlobalApplication.mLocationData.city;
        String province = GlobalApplication.mLocationData.province;
        String country = GlobalApplication.mLocationData.addr;

        ApiClient.getInstance().getBasicService(this).uploadFarmState(
                province,
                city,
                country,
                content,
                requestBody).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                ToastUtils.show(ReleaseActivity.this, "发布成功");
                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                ToastUtils.show(ReleaseActivity.this, "失败");
            }
        });

    }



}

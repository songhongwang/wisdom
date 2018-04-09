package com.cbt.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cbt.main.R;
import com.cbt.main.adapter.PerfactAccountAdapter;
import com.cbt.main.app.GlobalApplication;
import com.cbt.main.model.User;
import com.cbt.main.model.Weather7DaysForcast;
import com.cbt.main.model.ZuoWuModel;
import com.cbt.main.utils.SharedPreferencUtil;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private String mLocation = "";
    private Set<ZuoWuModel> mZuowuSet = new HashSet<>();
    List<String> mOptionZuowuList = new ArrayList<>();
    List<String> mOptionLeixingList = new ArrayList<>();

    private ZuoWuModel mCurrentZuowu;
    List<ZuoWuModel> dataList = new ArrayList<>();

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_perfact_account);
        ButterKnife.bind(this);
    }

    @Override
    public void initUI() {

        mIvFinish.setImageResource(R.drawable.icon_complete);
        mIvFinish.setVisibility(View.VISIBLE);
        mIvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
                ToastUtils.show(PerfactAccountActivity.this, "完善信息");
            }
        });

        View headerView = View.inflate(this, R.layout.header_perfact_account, null);
        View footerView = View.inflate(this, R.layout.footer_perfact_account, null);
        mEtFramName = (EditText) headerView.findViewById(R.id.et_farm_name);
        mEtFramAear = (EditText) headerView.findViewById(R.id.et_farm_area);
        headerView.findViewById(R.id.tv_get_farm_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfactAccountActivity.this, SelectMapLocationActivity.class);
                startActivityForResult(intent, 10);
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

        ZuoWuModel firstZuowu = new ZuoWuModel("", "");
        dataList.add(firstZuowu);
        mZuowuSet.add(firstZuowu);

        mPerfactAccountAdapter = new PerfactAccountAdapter<>(dataList, this);
        mLv.setAdapter(mPerfactAccountAdapter);
        mPerfactAccountAdapter.setOperateListener(new PerfactAccountAdapter.OperateListener() {
            @Override
            public void onAdd(ZuoWuModel zuoWuModel) {
                mZuowuSet.add(zuoWuModel);
            }

            @Override
            public void onDel(ZuoWuModel zuoWuModel) {
                mZuowuSet.remove(zuoWuModel);
            }

            @Override
            public void onLeixin(ZuoWuModel zuoWuModel) {
                mCurrentZuowu = zuoWuModel;
                onLeixingDialog();
            }

            @Override
            public void onPinzhong(ZuoWuModel zuoWuModel) {
                mCurrentZuowu = zuoWuModel;
                onPinzhongDialog();
            }
        });
    }

    private void onPinzhongDialog(){
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(PerfactAccountActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getPickerViewText()
//                                + options2Items.get(options1).get(option2)
//                                + options3Items.get(options1).get(option2).get(options3);
                Log.d("hahah", option2 + " " + options1);
                mCurrentZuowu.pinzhong = options1 + "";
                mPerfactAccountAdapter.notifyItem(mCurrentZuowu);
                mCurrentZuowu.strPinzhong = mOptionZuowuList.get(options1);
            }
        }).build();
        mOptionZuowuList.add("菠菜");
        mOptionZuowuList.add("青菜");
        mOptionZuowuList.add("油菜花");
        mOptionZuowuList.add("萝卜");
        mOptionZuowuList.add("白菜");

        pvOptions.setPicker(mOptionZuowuList);
        pvOptions.show();
    }

    private void onLeixingDialog(){
        OptionsPickerView pvOptions = new  OptionsPickerView.Builder(PerfactAccountActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getPickerViewText()
//                                + options2Items.get(options1).get(option2)
//                                + options3Items.get(options1).get(option2).get(options3);
                Log.d("hahah", option2 + " " + options1);
                mCurrentZuowu.leixin = options1 + "";
                mCurrentZuowu.strLeixin = mOptionLeixingList.get(options1);
                mPerfactAccountAdapter.notifyItem(mCurrentZuowu);
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


    // 完善用户信息 （农场位置 作物类型等）
    private void commit(){
        String farmName = mEtFramName.getText().toString();
        String farmAear = mEtFramAear.getText().toString();


        String city = GlobalApplication.mLocationData.city;
        String province = GlobalApplication.mLocationData.province;
        String country = GlobalApplication.mLocationData.addr;

        User login = SharedPreferencUtil.getLogin(this);
        if(login == null){
            ToastUtils.show(PerfactAccountActivity.this, "请登录");
            return;
        }

        String fName = login.getUname();
        if(TextUtils.isEmpty(mLocation)){
            ToastUtils.show(PerfactAccountActivity.this, "请选择位置");
        }
        String points = mLocation;
        String zuowulist = "";
        for(ZuoWuModel zuoWuModel : mZuowuSet){
            zuowulist += zuoWuModel.leixin + "," + zuoWuModel.strPinzhong + ";";
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == 11){
                mLocation = data.getStringExtra("location");
                ToastUtils.show(PerfactAccountActivity.this, mLocation);
            }
        }
    }
}

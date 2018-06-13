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
import com.cbt.main.model.ClientFarm;
import com.cbt.main.model.Dictionaries;
import com.cbt.main.model.TypeModel;
import com.cbt.main.model.User;
import com.cbt.main.model.Usercrop;
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
    private PerfactAccountAdapter<Usercrop> mPerfactAccountAdapter;
    private String mLocation = "";
    private Set<Usercrop> mZuowuSet = new HashSet<>();
    List<TypeModel> mOptionZuowuList = new ArrayList<>();
    List<TypeModel> mOptionLeixingList = new ArrayList<>();

    private Usercrop mCurrentZuowu;
    List<Usercrop> dataList = new ArrayList<>();

    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_perfact_account);
        ButterKnife.bind(this);
    }

    @Override
    public void initUI() {
        mTvTitle.setText("农庄信息");
        mIvFinish.setImageResource(R.drawable.icon_complete);
        mIvFinish.setVisibility(View.VISIBLE);
        mIvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commit();
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
                Usercrop zuoWuModel = new Usercrop("", "","","");
                mPerfactAccountAdapter.addItem(zuoWuModel);
            }
        });

        mLv.addHeaderView(headerView);
        mLv.addFooterView(footerView);



        mPerfactAccountAdapter = new PerfactAccountAdapter<>(dataList, this);
        mLv.setAdapter(mPerfactAccountAdapter);
        mPerfactAccountAdapter.setOperateListener(new PerfactAccountAdapter.OperateListener() {
            @Override
            public void onAdd(Usercrop zuoWuModel) {
                mZuowuSet.add(zuoWuModel);
            }

            @Override
            public void onDel(Usercrop zuoWuModel) {
                mZuowuSet.remove(zuoWuModel);
            }

            @Override
            public void onLeixin(Usercrop zuoWuModel) {
                mCurrentZuowu = zuoWuModel;
                onLeixingDialog();
            }

            @Override
            public void onPinzhong(Usercrop zuoWuModel) {
                mCurrentZuowu = zuoWuModel;
                onPinzhongDialog();
            }
        });
        initselectData();
    }

    private void onPinzhongDialog(){
        final OptionsPickerView pvOptions = new  OptionsPickerView.Builder(PerfactAccountActivity.this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置
//                        String tx = options1Items.get(options1).getPickerViewText()
//                                + options2Items.get(options1).get(option2)
//                                + options3Items.get(options1).get(option2).get(options3);
                Log.d("hahah", option2 + " " + options1);
                mCurrentZuowu.setCid(mOptionZuowuList.get(options1).getId());
                mCurrentZuowu.setCname(mOptionZuowuList.get(options1).getName());
                mPerfactAccountAdapter.notifyItem(mCurrentZuowu);
            }
        }).build();
        ApiClient.getInstance().getBasicService(this).findType(mCurrentZuowu.getCtid(),1).enqueue(new Callback<List<Dictionaries>>() {
            @Override
            public void onResponse(Call<List<Dictionaries>> call, Response<List<Dictionaries>> response) {
                mOptionZuowuList.clear();
                List<Dictionaries> dataList = response.body();
                for(int i = 0; i< dataList.size(); i ++){
                    mOptionZuowuList.add(new TypeModel(dataList.get(i).getId(),dataList.get(i).getName()));
                }
                pvOptions.setPicker(mOptionZuowuList);
                pvOptions.show();
            }

            @Override
            public void onFailure(Call<List<Dictionaries>> call, Throwable t) {

            }
        });

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

                mCurrentZuowu.setCtid(mOptionLeixingList.get(options1).getId());
                mCurrentZuowu.setCtname(mOptionLeixingList.get(options1).getName());
                mPerfactAccountAdapter.notifyItem(mCurrentZuowu);
            }
        }).build();

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
//
//        User login = SharedPreferencUtil.getLogin(this);
//        if(login == null){
//            ToastUtils.show(PerfactAccountActivity.this, "请登录");
//            return;
//        }
//
//        String fName = login.getUname();
        if(TextUtils.isEmpty(mLocation)){
            ToastUtils.show(PerfactAccountActivity.this, "请选择位置");
        }
        String points = mLocation;
        String zuowulist = "";
        for(Usercrop zuoWuModel : mZuowuSet){
            if (!zuoWuModel.getCtid().equals(""))
            {
                zuowulist += zuoWuModel.getCtid() + "," + zuoWuModel.getCid() + ";";
            }
        }

        ApiClient.getInstance().getBasicService(this).perfactAccount(farmName, points, zuowulist, province,city , country,farmAear).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                ToastUtils.show(PerfactAccountActivity.this, "成功");
                User login = SharedPreferencUtil.getLogin(PerfactAccountActivity.this);
                login.setState("1");
                SharedPreferencUtil.saveLogin(PerfactAccountActivity.this, login);
                String from = getIntent().getStringExtra("from");
                if(!"MoreFragment".equals(from)){ // 首次登录或注册成功后在没有打开MainActivity之前调用了此页面需要手动打开MainActivity
                    startActivity(new Intent(PerfactAccountActivity.this, MainActivity.class));
                }
                finish();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                ToastUtils.show(PerfactAccountActivity.this, "失败");
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

    public void initselectData() {

        ApiClient.getInstance().getBasicService(this).findType(null, 2).enqueue(new Callback<List<Dictionaries>>() {
            @Override
            public void onResponse(Call<List<Dictionaries>> call, Response<List<Dictionaries>> response) {
                List<Dictionaries> dataList = response.body();
                for (int i = 0; i < dataList.size(); i++) {
                    mOptionLeixingList.add(new TypeModel(dataList.get(i).getId(), dataList.get(i).getName()));
                }

            }

            @Override
            public void onFailure(Call<List<Dictionaries>> call, Throwable t) {

            }
        });
        ApiClient.getInstance().getBasicService(this).farmlandfarm().enqueue(new Callback<ClientFarm>() {
            @Override
            public void onResponse(Call<ClientFarm> call, Response<ClientFarm> response) {
                ClientFarm cfm = response.body();
                if (cfm != null && cfm.getFname() != null)
                {
                    mLocation = cfm.getPoinw()+","+cfm.getPoinj();
                    mEtFramName.setText(cfm.getFname());
                    mEtFramAear.setText(cfm.getArea());
                    if (cfm.getUsercrop() != null)
                    {
                        for(int i = 0; i< cfm.getUsercrop().size(); i++){
                            Usercrop tempadd =  cfm.getUsercrop().get(i);
                            tempadd.setP1(tempadd.getUid()+"-"+tempadd.getCtid()+"-"+tempadd.getCid());
                            dataList.add(tempadd);
                            mZuowuSet.add(tempadd);
                        }

                    }
                }
                else
                {
                    Usercrop firstZuowu = new Usercrop("", "","","");
                    dataList.add(firstZuowu);
                    mZuowuSet.add(firstZuowu);
                }
//                Usercrop firstZuowu = new Usercrop("", "","","");
//                dataList.add(firstZuowu);
//                mZuowuSet.add(firstZuowu);
                mPerfactAccountAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ClientFarm> call, Throwable t) {
//                ToastUtils.show(PerfactAccountActivity.this, "123456");
            }
        });
    }
}

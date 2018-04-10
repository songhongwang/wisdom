package com.cbt.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.R;
import com.cbt.main.adapter.DisasterActAdapter;
import com.cbt.main.utils.Utils;
import com.cbt.main.view.piaoquan.MessagePicturesLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vigorous on 18/4/10.
 */

public class DisasterDetailActivity extends BaseActivity {
    private DisasterActAdapter mDisasterActAdapter;

    MessagePicturesLayout lPictures;
    TextView mTvContent;
    ListView mListView;
    EditText mEtInput;
    @Override
    public void onCCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_disaster);

    }

    @Override
    public void initUI() {

        mIvFinish.setVisibility(View.GONE);

        mEtInput = (EditText) findViewById(R.id.rc_edit_text);

        mListView = (ListView) findViewById(R.id.lv_disaster);
        View headerView = View.inflate(this, R.layout.header_disaster_list, null);
        mListView.addHeaderView(headerView);

        List datas  = new ArrayList();

        mDisasterActAdapter = new DisasterActAdapter(this,datas);
        mListView.setAdapter(mDisasterActAdapter);


        lPictures = (MessagePicturesLayout) headerView.findViewById(R.id.l_pictures);
        mTvContent = (TextView) headerView.findViewById(R.id.t_content);



        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");
        datas.add("https://avatar.csdn.net/D/A/2/3_bigdog_1027.jpg");

        lPictures.set(datas, datas);
    }
}

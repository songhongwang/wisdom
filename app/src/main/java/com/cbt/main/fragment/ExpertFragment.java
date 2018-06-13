package com.cbt.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.cbt.main.activity.ReleaseZixunActivity;
import com.cbt.main.adapter.ExpertSearchAdapter;
import com.cbt.main.adapter.ZhuanjiaAdapter;
import com.cbt.main.dialog.ReleaseDialog;
import com.cbt.main.model.Data;
import com.cbt.main.model.WentiModel;
import com.cbt.main.model.event.OnBackPressedEvent;
import com.cbt.main.utils.ToastUtils;
import com.cbt.main.utils.net.ApiClient;
import com.cbt.main.view.pagertab.PagerSlidingTabStrip;
import com.cbt.main.R;
import com.cbt.main.adapter.ExpertFragmentAdapter;
import com.cbt.main.utils.Utils;
import com.cbt.main.view.piaoquan.SpaceItemDecoration;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.rong.eventbus.EventBus;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by caobotao on 16/1/4.
 */
public class ExpertFragment extends BaseFragment {

    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;

    private RecyclerView mRecyclerViewSearch;
    private ExpertSearchAdapter mExpertSearchAdapter;

    private View mVTip1, mVTip2, mVTip3;

    private boolean mIsLoading;
    private int mPage;
    private boolean mHasMore;
    private List<Data> goodList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initUI() {
        mTvTitle.setText("专家咨询");
        EventBus.getDefault().register(this);
        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setImageResource(R.drawable.nav_icon_message);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RongIM.getInstance().startSubConversationList(getActivity(), Conversation.ConversationType.PRIVATE);

            }
        });

        mVTip1 = mRootView.findViewById(R.id.msg_unread_count_tab1);
        mVTip2=  mRootView.findViewById(R.id.msg_unread_count_tab2);
//        mVTip3=  mRootView.findViewById(R.id.msg_unread_count_tab3);

        mIvComplete.setVisibility(View.VISIBLE);
        mIvComplete.setImageResource(R.drawable.nav_icon_release);
        mIvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ReleaseZixunActivity.class);
                startActivity(intent);
            }
        });
        mViewPager = (ViewPager) mRootView.findViewById(R.id.id_viewpager_moments_t3);
        mViewPager.setOffscreenPageLimit(3);
        ExpertFragmentAdapter adapter = new ExpertFragmentAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) mRootView.findViewById(R.id.tabs_t3);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        setTabsValue();



        ((EditText)mRootView.findViewById(R.id.et_search)).setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    mRecyclerViewSearch.setVisibility(View.VISIBLE);
                    getSearchData();
                    return true;
                }
                return false;

            }

        });
        ((EditText)mRootView.findViewById(R.id.et_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(TextUtils.isEmpty(charSequence.toString())){
                    if(mRecyclerViewSearch.getVisibility() == View.VISIBLE){
                        mRecyclerViewSearch.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRecyclerViewSearch = (RecyclerView) mRootView.findViewById(R.id.v_recycler);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewSearch.addItemDecoration(new SpaceItemDecoration(getActivity()).setSpace(5).setSpaceColor(0xFFECECEC));

        mExpertSearchAdapter = new ExpertSearchAdapter(getActivity());
        mRecyclerViewSearch.setAdapter(mExpertSearchAdapter);


    }

    private void setTabsValue() {
        if (isAdded()) {
            DisplayMetrics dm = getResources().getDisplayMetrics();
            // 设置Tab是自动填充满屏幕的
            mPagerSlidingTabStrip.setShouldExpand(true);
            // 设置Tab的分割线是透明的
            mPagerSlidingTabStrip.setDividerColor(getResources().getColor(R.color.transparent));
            // 设置Tab底部线的高度
            mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, dm));
            // 设置Tab Indicator的高度
            mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm));
            // 设置Tab Indicator的宽度
//            mPagerSlidingTabStrip.setindicatorLinePadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, dm));
            // 设置Tab标题文字的大小
            mPagerSlidingTabStrip.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, dm));
            //选中tab是否加粗
//            mPagerSlidingTabStrip.setTextSize(12);
            // 设置Tab Indicator的颜色
            mPagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.green));
            // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
//            mPagerSlidingTabStrip.setSelectedTextColor(getResources().getColor(R.color.c_333333));
            // 取消点击Tab时的背景色
            mPagerSlidingTabStrip.setTabBackground(0);

            mPagerSlidingTabStrip.setSelectedTabColor(R.color.green);

            mPagerSlidingTabStrip.setScrollOffset((int) (Utils.getScreenWidth(getActivity()) * 0.5f));
        }
    }
    @Override
    protected void lazyLoad() {
        // 接口控制显示小红点

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(OnBackPressedEvent onBackPressedEvent) {
        if(mRecyclerViewSearch.getVisibility() == View.VISIBLE){
            mRecyclerViewSearch.setVisibility(View.GONE);
        }else{
            getActivity().finish();
        }
    }


    private void getSearchData(){
        // TODO: 18/6/13 此处url需要改为搜索
        ApiClient.getInstance().getBasicService(getContext()).getExperthot(0).enqueue(new Callback<List<WentiModel>>() {
            @Override
            public void onResponse(Call<List<WentiModel>> call, Response<List<WentiModel>> response) {
                List<WentiModel> dataList = response.body();

                mIsLoading = false;

                if(dataList.size() > 0){
                    if(mPage == 0){
                        goodList.clear();
                    }
                    for(int i = 0; i< dataList.size(); i ++){
                        goodList.add(WentiModel.convert(dataList.get(i)));
                    }

                    mExpertSearchAdapter.set(goodList);
                    mExpertSearchAdapter.notifyDataSetChanged();

                    mPage ++;
                    mHasMore =true;
                }else{
                    mHasMore = false;
                }
            }

            @Override
            public void onFailure(Call<List<WentiModel>> call, Throwable t) {

                mIsLoading = false;
            }
        });

    }
}

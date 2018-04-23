package com.cbt.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cbt.main.R;
import com.cbt.main.adapter.MineFragmentAdapter;
import com.cbt.main.adapter.MyShoucangAdapter;
import com.cbt.main.model.MomentMode;
import com.cbt.main.utils.Utils;
import com.cbt.main.view.pagertab.PagerSlidingTabStrip;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by caobotao on 16/1/4.
 */
public class MyShoucangFragment extends BaseFragment {


    private ViewPager mViewPager;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;

    public static MyShoucangFragment getInstance(MomentMode mode){
        MyShoucangFragment fragment = new MyShoucangFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("mode",mode);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void refresh(){
        getData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fabu, container, false);
        mRootView = view;
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void initUI() {
        mViewPager = (ViewPager) mRootView.findViewById(R.id.id_viewpager_moments_t2);
        mViewPager.setOffscreenPageLimit(1);
        MyShoucangAdapter adapter = new MyShoucangAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        mPagerSlidingTabStrip = (PagerSlidingTabStrip) mRootView.findViewById(R.id.tabs_t2);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        setTabsValue();

        getData();
    }

    private void startConversationList() {

        RongIM.getInstance().startSubConversationList(getActivity(), Conversation.ConversationType.PRIVATE);

//        Map<String, Boolean> map = new HashMap<>();
//        map.put(Conversation.ConversationType.PRIVATE.getName(), true); // 会话列表需要显示私聊会话, 第二个参数 true 代表私聊会话需要聚合显示
//        map.put(Conversation.ConversationType.GROUP.getName(), false);  // 会话列表需要显示群组会话, 第二个参数 false 代表群组会话不需要聚合显示

//        RongIM.getInstance().startConversationList(getActivity()); // 所有聊天 包括系统服务，公众号，群组
//        RongIM.getInstance().startConversationList(getActivity(), map); // 同上
//        RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE, "18600211553", "hahah"); // 打开指定聊天
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

    }

    private void getData() {
//        java.lang.IllegalStateException: Expected a string but was BEGIN_OBJECT at line 1 column 797 path $[1].interactivelist[0]
//        ApiClient.getInstance().getBasicService(getContext()).getIndexFeed(0).enqueue(new Callback<List<IndexFeedModel>>() {
//            @Override
//            public void onResponse(Call<List<IndexFeedModel>> call, Response<List<IndexFeedModel>> response) {
//                ToastUtils.show(getContext(), response.body().size() + " ");
//            }
//
//            @Override
//            public void onFailure(Call<List<IndexFeedModel>> call, Throwable t) {
//
//            }
//        });
    }
}

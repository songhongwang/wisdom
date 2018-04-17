package com.cbt.main.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;


/**
 * Created by vigorous on 17/3/21.
 *
 */

public class OnRcvScrollListener extends RecyclerView.OnScrollListener implements OnBottomListener {

    private String TAG = getClass().getSimpleName();

    public enum LAYOUT_MANAGER_TYPE {
        LINEAR,
        GRID,
        STAGGERED_GRID
    }

    /**
     * layoutManager的类型（枚举）
     */
    protected LAYOUT_MANAGER_TYPE layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;
/*    *//**
     * 是否正在加载
     *//*
    private boolean isLoadingMore = false;*/

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //  int lastVisibleItemPosition = -1;
        if (layoutManagerType == null) {
            if (layoutManager instanceof LinearLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
            } else if (layoutManager instanceof GridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.GRID;
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
            } else {
                throw new RuntimeException(
                        "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
            }
        }

        switch (layoutManagerType) {
            case LINEAR:
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case GRID:
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                break;
            case STAGGERED_GRID:
                StaggeredGridLayoutManager staggeredGridLayoutManager
                        = (StaggeredGridLayoutManager) layoutManager;
                if (lastPositions == null) {
                    lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                lastVisibleItemPosition = findMax(lastPositions);
                break;
        }

    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        currentScrollState = newState;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        if (
                visibleItemCount > 0 &&
                        (currentScrollState == RecyclerView.SCROLL_STATE_SETTLING && lastVisibleItemPosition >= totalItemCount - 3)
                        ||
                        (currentScrollState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition >= totalItemCount - 1)

                ) {
            Log.d("rcv_log", "is loading more");
            onBottom();
        }
        // 滚动回调
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            onCScrollStateChanged(recyclerView);
        }
    }


    private void onCScrollStateChanged(RecyclerView recyclerView) {
        int start = 0;
        int last = 0;
        if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            int[] firstVisibleItems = null;
            firstVisibleItems = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPositions(firstVisibleItems);

            int[] lastVisibleItems = null;

            lastVisibleItems = ((StaggeredGridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPositions(lastVisibleItems);


            if (firstVisibleItems.length > 1) {
                if (firstVisibleItems[0] > firstVisibleItems[1]) {
                    start = firstVisibleItems[1];
                } else {
                    start = firstVisibleItems[0];

                }
            }
            if (lastVisibleItems.length > 1) {
                if (lastVisibleItems[0] > lastVisibleItems[1]) {
                    last = lastVisibleItems[0] + 1;
                } else {
                    last = lastVisibleItems[1] + 1;
                }
            }
        } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            start = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            start = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            last = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
        }

        onCScrollStateChanged(start, last);
    }

    public void onCScrollStateChanged(int startPos, int endPos) {
        //Log.d(TAG, "scroll start pos end pos")
    }

    @Override
    public void onBottom() {
        //Log.d(TAG, "is onBottom");
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}

package com.modulizedatasource.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.modulizedatasource.datamodule.ModuleDataAdapter;
import com.modulizedatasource.datamodule.ModuleDataGroupNode;
import com.modulizedatasource.datamodule.ModuleDataNode;
import com.modulizedatasource.datamodule.coverview.FetchCoveredViewResult;

import java.util.ArrayList;


/**
 * Created by tony on 2018/5/11.
 */

public class ListViewWithCoveredView extends FrameLayout {
    private DrawEventListenedListView mListView;
    private RelativeLayout mCoverViewGroup;
    private int mCoverViewTopMargin;
    private AbsListView.OnScrollListener mOnScrollListenerProxy;

    private final ArrayList<ModuleDataNode> mExitNodeList = new ArrayList<>();
    private final ArrayList<View> mToDrawViewList = new ArrayList<>();
    private final ArrayList<Integer> mToDrawViewTopList = new ArrayList<>();


    public ListViewWithCoveredView(Context context) {
        super(context);
        init();
    }

    public ListViewWithCoveredView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ListViewWithCoveredView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.mListView == null) {
            return;
        }

        this.mListView.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.mListView.setOnItemClickListener(listener);
    }

    public void setDivider(Drawable divider) {
        this.mListView.setDivider(divider);
    }

    public void setDividerHeight(int height) {
        this.mListView.setDividerHeight(height);
    }

    public void setCoverViewTopMargin(final int value) {
        this.mCoverViewTopMargin = value;
    }

    public void setOnScrollListener(AbsListView.OnScrollListener l) {
        this.mOnScrollListenerProxy = l;
    }

    private void init() {
        mListView = new DrawEventListenedListView(this.getContext());
        mCoverViewGroup = new RelativeLayout(this.getContext());
        mListView.setOnDrawListener(mListViewOnDrawListener);
        mListView.setOnScrollListener(mInnerOnScrollListener);

        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mListView, lp1);

        FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        addView(mCoverViewGroup, lp2);
    }

    private final AbsListView.OnScrollListener mInnerOnScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOnScrollListenerProxy == null) {
                return;
            }

            mOnScrollListenerProxy.onScrollStateChanged(view, scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mOnScrollListenerProxy == null) {
                return;
            }

            mOnScrollListenerProxy.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    };

    private void drawCoverView() {
        if (!(mListView.getAdapter() instanceof ModuleDataAdapter)) {
            mCoverViewGroup.removeAllViews();
            return;
        }

        final ModuleDataAdapter adapter = (ModuleDataAdapter) mListView.getAdapter();
        adapter.getPageContextModel().mCoveredViewCacheManage.recycleViewGroup(mCoverViewGroup);

        final ModuleDataNode topModuleDataNode = adapter.getTopModuleDataNode();
        if (!(topModuleDataNode instanceof ModuleDataGroupNode)) {
            return;
        }

        ModuleDataGroupNode topModule = (ModuleDataGroupNode) topModuleDataNode;

        int lastViewBottom = this.mCoverViewTopMargin;
        int currentViewTop;
        FetchCoveredViewResult result;
        mExitNodeList.clear();
        mToDrawViewList.clear();
        mToDrawViewTopList.clear();

        do {
            final int index = getCoverViewIndexByY(lastViewBottom);
            if (index < 0) {
                break;
            }

            final int realIndex = index + mListView.getFirstVisiblePosition();
            result = topModule.getCoveredView(realIndex, mExitNodeList);
            if (result == null ||
                    result.coveredView == null) {
                break;
            }

            currentViewTop = lastViewBottom;

            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.getWidth(), MeasureSpec.EXACTLY);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.getHeight(), MeasureSpec.AT_MOST);
            result.coveredView.measure(widthMeasureSpec, heightMeasureSpec);

            final int firstVisiblePosition = mListView.getFirstVisiblePosition();
            final int lastVisiblePosition = mListView.getLastVisiblePosition();
            final int fetchedModuleLastIndex = result.lastReturnNode.getEndIndex();

            if (fetchedModuleLastIndex >= firstVisiblePosition &&
                    fetchedModuleLastIndex <= lastVisiblePosition) {
                int viewIndex = fetchedModuleLastIndex - firstVisiblePosition;
                int bottom = mListView.getChildAt(viewIndex).getBottom();

                if (bottom < lastViewBottom + result.coveredView.getMeasuredHeight()) {
                    int offset = lastViewBottom + result.coveredView.getMeasuredHeight() - bottom;
                    currentViewTop -= offset;
                    lastViewBottom = bottom + mListView.getDividerHeight();
                } else {
                    lastViewBottom += result.coveredView.getMeasuredHeight() + mListView.getDividerHeight();
                }
            } else {
                lastViewBottom += result.coveredView.getMeasuredHeight() + mListView.getDividerHeight();
            }

            result.coveredView.layout(0,
                    currentViewTop,
                    result.coveredView.getMeasuredWidth(),
                    currentViewTop + result.coveredView.getMeasuredHeight());

            mToDrawViewList.add(result.coveredView);
            mToDrawViewTopList.add(currentViewTop);

            if (result.lastReturnNode != null) {
                mExitNodeList.add(result.lastReturnNode);
            }
        } while (true);

        for (int i = mToDrawViewList.size() - 1; i >= 0; i--) {
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.topMargin = mToDrawViewTopList.get(i);
            mCoverViewGroup.addView(mToDrawViewList.get(i), lp);
        }
    }

    private int getCoverViewIndexByY(final int y) {
        int childViewIndex = -1;

        if (y < 0) {
            return childViewIndex;
        }

        /**
         * 找出ListView中最后一个被CoveredView遮挡的Item
         * 1,y==0时,childViewIndex 为0
         * 2,y>0时,找第一个bottom>=y且top<y的item
         */
        for (int i = 0; i < mListView.getChildCount(); i++) {
            if (mListView.getChildAt(i).getBottom() >= y &&
                    (y == 0 || y > mListView.getChildAt(i).getTop())) {
                childViewIndex = i;
                break;
            }
        }

        return childViewIndex;
    }

    private final DrawEventListener mListViewOnDrawListener = new DrawEventListener() {

        @Override
        public void onDraw() {
            drawCoverView();
        }
    };
}

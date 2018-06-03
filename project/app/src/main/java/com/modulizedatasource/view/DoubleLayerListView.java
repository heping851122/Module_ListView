package com.modulizedatasource.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.modulizedatasource.datamodule.ModuleDataAdapter;
import com.modulizedatasource.datamodule.ModuleDataGroupNode;
import com.modulizedatasource.datamodule.ModuleDataNode;
import com.modulizedatasource.datamodule.coverview.FetchCoveredViewResult;

import java.util.ArrayList;


/**
 * Created by tony on 2018/5/11.
 */

public class DoubleLayerListView extends ListView {
    private int mCoverViewTopMargin;
    private OnScrollListener mOnScrollListenerProxy;


    public DoubleLayerListView(Context context) {
        super(context);
        init();
    }

    public DoubleLayerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DoubleLayerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCoverViewTopMargin(final int value) {
        this.mCoverViewTopMargin = value;
    }

    @Override
    public void setOnScrollListener(OnScrollListener l) {
        this.mOnScrollListenerProxy = l;
    }

    private void init() {
        setOnScrollListener(mInnerOnScrollListener);
    }

    private final OnScrollListener mInnerOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOnScrollListenerProxy == null) {
                return;
            }

            mOnScrollListenerProxy.onScrollStateChanged(view, scrollState);
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            DoubleLayerListView.this.onScrollLocally(view, firstVisibleItem, visibleItemCount, totalItemCount);

            if (mOnScrollListenerProxy == null) {
                return;
            }

            mOnScrollListenerProxy.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    };

    private void onScrollLocally(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawCoverView(canvas);
    }

    final ArrayList<ModuleDataNode> exitNodeList = new ArrayList<>();
    final ArrayList<View> toDrawViewList = new ArrayList<>();
    final ArrayList<Integer> toDrawViewTopList = new ArrayList<>();

    private void drawCoverView(Canvas canvas) {
        if (!(this.getAdapter() instanceof ModuleDataAdapter)) {
            return;
        }

        final ModuleDataAdapter adapter = (ModuleDataAdapter) this.getAdapter();
        final ModuleDataNode topModuleDataNode = adapter.getTopModuleDataNode();
        if (!(topModuleDataNode instanceof ModuleDataGroupNode)) {
            return;
        }

        ModuleDataGroupNode topModule = (ModuleDataGroupNode) topModuleDataNode;

        int lastViewBottom = this.mCoverViewTopMargin;
        int currentViewTop;
        FetchCoveredViewResult result;
        exitNodeList.clear();
        toDrawViewList.clear();
        toDrawViewTopList.clear();

        do {
            int index = getCoverViewIndexByY(lastViewBottom);
            int realIndex = index + this.getFirstVisiblePosition();
            result = topModule.getCoveredView(realIndex, exitNodeList);
            if (result == null ||
                    result.coveredView == null) {
                break;
            }

            currentViewTop = lastViewBottom;

            int widthMeasureSpec = MeasureSpec.makeMeasureSpec(this.getWidth(), MeasureSpec.AT_MOST);
            int heightMeasureSpec = MeasureSpec.makeMeasureSpec(this.getHeight(), MeasureSpec.AT_MOST);
            result.coveredView.measure(widthMeasureSpec, heightMeasureSpec);

            final int firstVisiblePosition = this.getFirstVisiblePosition();
            final int lastVisiblePosition = this.getLastVisiblePosition();
            final int fetchedModuleLastIndex = result.lastReturnNode.getEndIndex();

            if (fetchedModuleLastIndex >= firstVisiblePosition &&
                    fetchedModuleLastIndex <= lastVisiblePosition) {
                int viewIndex = fetchedModuleLastIndex - firstVisiblePosition;
                int bottom = this.getChildAt(viewIndex).getBottom();

                if (bottom < lastViewBottom + result.coveredView.getMeasuredHeight()) {
                    int offset = lastViewBottom + result.coveredView.getMeasuredHeight() - bottom;
                    currentViewTop -= offset;
                    lastViewBottom = bottom + this.getDividerHeight();
                } else {
                    lastViewBottom += result.coveredView.getMeasuredHeight() + this.getDividerHeight();
                }
            } else {
                lastViewBottom += result.coveredView.getMeasuredHeight() + this.getDividerHeight();
            }

            result.coveredView.layout(0, currentViewTop, result.coveredView.getMeasuredWidth(),
                    currentViewTop + result.coveredView.getMeasuredHeight());

            toDrawViewList.add(result.coveredView);
            toDrawViewTopList.add(currentViewTop);

            if (result.lastReturnNode != null) {
                exitNodeList.add(result.lastReturnNode);
            }

        } while (true);

        for (int i = toDrawViewList.size() - 1; i >= 0; i--) {
            canvas.save();
            canvas.translate(0, toDrawViewTopList.get(i));
            toDrawViewList.get(i).draw(canvas);
            canvas.restore();
        }
    }

    private int getCoverViewIndexByY(int y) {
        int childViewIndex = -1;

        if (y < 0) {
            return childViewIndex;
        }


        for (int i = 0; i < this.getChildCount(); i++) {

            if (this.getChildAt(i).getBottom() > y) {
                childViewIndex = i;
                break;
            }
        }

        return childViewIndex;
    }
}

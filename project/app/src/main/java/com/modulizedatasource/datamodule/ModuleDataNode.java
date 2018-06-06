package com.modulizedatasource.datamodule;

import android.content.Context;
import android.util.Log;
import android.view.View;


import com.modulizedatasource.model.PageContextModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tony on 2018/5/8.
 */

public abstract class ModuleDataNode<T> {
    public interface onItemClickListener {
        void onItemClickEvent(int index, ModuleDataNode node);
    }

    public final static int sInvalidIndex = -1;

    private final List<T> mDataList = new ArrayList<>();
    private PageContextModel mContext;
    protected int mStartIndex = sInvalidIndex;
    protected int mEndIndex = sInvalidIndex;
    protected int mCount;
    protected ModuleDataGroupNode mParent;
    protected onItemClickListener mOnItemClickListener;


    public void init(PageContextModel context) {
        this.mContext = context;
    }

    public PageContextModel getPageContextModel() {
        try {
            if (mContext != null) {
                return mContext;
            }

            if (mContext == null) {
                mContext = getRootNode().mContext;
            }
        } catch (Exception e) {
            Log.e("tony", "Exception getPageContextModel null");
        }

        return mContext;
    }

    public ModuleDataGroupNode getParent() {
        return this.mParent;
    }

    protected View getView(final View convertView,final int index, final Context context) {
        final int realIndex = getLocalIndex(index);

        return innerGetView(convertView,realIndex, context);
    }

    public abstract View innerGetView(final View convertView,final int localIndex, final Context context);

    protected abstract String getViewType(final int index);

    public int getCount() {
        return mCount;
    }

    public void setData(final List<T> inputList) {
        if (inputList == null ||
                inputList.isEmpty()) {
            mDataList.clear();
        } else {
            mDataList.addAll(inputList);
        }

        requestDataInvalid();
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public final int getLocalIndex(final int index) {
        return index - this.mStartIndex;
    }

    public void onItemClick(final int index) {
        if (mOnItemClickListener == null) {
            return;
        }

        mOnItemClickListener.onItemClickEvent(index, this);
    }

    public T getItemData(final int index) {
        final int innerIndex = getLocalIndex(index);

        return getItemDataLocally(innerIndex);
    }

    public T getItemDataLocally(final int localIndex) {
        return this.mDataList.get(localIndex);
    }

    public int getStartIndex() {
        return this.mStartIndex;
    }

    public int getEndIndex() {
        return this.mEndIndex;
    }

    protected void requestDataInvalid() {
        if (mParent != null) {
            mParent.requestDataInvalid();
        }
    }

    protected void invalidData(final int startIndex) {
        this.mCount = mDataList.size();

        if (this.getCount() > 0) {
            this.mStartIndex = startIndex;
            this.mEndIndex = this.mStartIndex + this.getCount() - 1;
        } else {
            resetIndex();
        }
    }

    protected boolean isIndexInRange(final int index) {
        if (index < 0) {
            return false;
        }

        return index >= mStartIndex && index <= mEndIndex;
    }

    protected void setParent(final ModuleDataGroupNode parent) {
        this.mParent = parent;
    }

    protected void detach() {
        this.mParent = null;
    }

    protected void resetIndex() {
        this.mStartIndex = sInvalidIndex;
        this.mEndIndex = sInvalidIndex;
    }

    protected ModuleDataNode getRootNode() {
        if (mParent == null) {
            return null;
        }

        ModuleDataNode topperNode = mParent;
        while (topperNode.getParent() != null) {
            topperNode = topperNode.getParent();
        }

        return topperNode;
    }

}

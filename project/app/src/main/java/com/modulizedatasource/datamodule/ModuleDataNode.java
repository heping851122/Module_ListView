package com.modulizedatasource.datamodule;

import android.content.Context;
import android.view.View;


import com.modulizedatasource.model.PageContextModel;
import com.modulizedatasource.viewcache.BaseViewHolder;
import com.tools.LogUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by tony on 2018/5/8.
 */

public abstract class ModuleDataNode<T, V extends BaseViewHolder> {
    public interface onItemClickListener {
        void onItemClickEvent(int index, ModuleDataNode node);
    }

    public final static int sInvalidIndex = -1;

    private Class<V> mViewHolderClass = null;
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
            LogUtil.printExceptionLog("getPageContextModel null");
        }

        return mContext;
    }

    public ModuleDataGroupNode getParent() {
        return this.mParent;
    }

    protected View getView(final View convertView, final int index, final Context context) {
        final int realIndex = getLocalIndex(index);

        return innerGetView(convertView, realIndex, context);
    }

    private View innerGetView(final View convertView, final int localIndex, final Context context) {
        Class<V> clazzType = getViewHolderClassType();
        V viewHolder;
        if (convertView != null &&
                clazzType.isInstance(convertView.getTag())) {
            viewHolder = (V) convertView.getTag();
        } else {
            viewHolder = (V) getPageContextModel().mViewCacheManage.getViewHolder(getViewType(localIndex));
        }

        if (viewHolder != null) {
            bindData(viewHolder, localIndex);
            return viewHolder.mContentView;
        } else {
            return null;
        }
    }

    protected abstract void bindData(V viewHolder, final int localIndex);

    protected String getViewType(final int index) {
        Class<V> clazzType = getViewHolderClassType();

        if (clazzType == null) {
            return "";
        } else {
            return clazzType.getName();
        }
    }

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

    private Class<V> getViewHolderClassType() {
        if (mViewHolderClass != null) {
            return mViewHolderClass;
        }

        try {
            Class childClazz = this.getClass();
            Class parentClazz;
            while (true) {
                parentClazz = childClazz.getSuperclass();
                if (parentClazz == ModuleDataNode.class) {
                    break;
                } else {
                    childClazz = parentClazz;
                }
            }

            Type type = childClazz.getGenericSuperclass();
            Type[] generics = ((ParameterizedType) type).getActualTypeArguments();
            mViewHolderClass = (Class<V>) (generics[1]);
        } catch (Exception e) {
            mViewHolderClass = null;
        } finally {
            return mViewHolderClass;
        }
    }
}

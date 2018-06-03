package com.modulizedatasource.viewcache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;


/**
 * Created by tony on 2018/1/19.
 */

public abstract class BaseViewHolder {
    public interface onViewDetachListener {
        void onDetach(final BaseViewHolder viewHolder);
    }

    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected int mMaxViewCacheCount = 20;
    protected onViewDetachListener mViewDetachListener;

    public View mContentView;

    public void init(final Context context, final LayoutInflater layoutInflater) {
        this.mContext = context;
        this.mLayoutInflater = layoutInflater;
    }

    public void build() {
        if (mContext == null ||
                mLayoutInflater == null) {
            return;
        }

        final View view = createView();
        if (view == null) {
            return;
        }
        view.setTag(this);
        this.mContentView = view;
        bindView(view);
    }

    public boolean isUsable() {
        return this.mContentView != null &&
                this.mContentView.getParent() == null;
    }

    protected abstract View createView();

    protected abstract void bindView(final View contentView);

    public void recycleView(final List<BaseViewHolder> viewHolderList) {
        if (viewHolderList == null) {
            return;
        }

        if (viewHolderList.size() > mMaxViewCacheCount) {
            return;
        }

        viewHolderList.add(this);
    }

    public void setViewDetachListener(final onViewDetachListener viewDetachListener) {
        mViewDetachListener = viewDetachListener;
    }

    void notifyDetach() {
        if (mViewDetachListener == null) {
            return;
        }

        mViewDetachListener.onDetach(this);
        mViewDetachListener = null;
    }
}

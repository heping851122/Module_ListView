package com.modulizedatasource.viewcache;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tools.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tony on 2018/1/25.
 */

public abstract class ViewCacheManagerBaseClass {
    protected final HashMap<String, Class<? extends BaseViewHolder>> mViewCacheTypeMap = new LinkedHashMap<>(20);
    protected final HashMap<String, ArrayList<BaseViewHolder>> mViewHolderCacheMap = new HashMap<>(20);
    protected final HashMap<String, Integer> mViewTypeMap = new HashMap<>(20);

    protected String tag = this.getClass().getName();
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public void init(final Context context, final LayoutInflater layoutInflater) {
        this.mContext = context;
        this.mLayoutInflater = layoutInflater;
        refreshViewType();
    }

    public int getViewTypeCount() {
        return mViewTypeMap.size();
    }

    public int getViewType(String key) {
        return mViewTypeMap.get(key);
    }

    public BaseViewHolder getViewHolder(final String key) {
        BaseViewHolder viewHolder = null;
        if (mViewCacheTypeMap.get(key) == null) {
            LogUtil.printExceptionLog("ViewCache get TypeMap not find key: " + key);
            return viewHolder;
        }

        final List<BaseViewHolder> viewHolderList = mViewHolderCacheMap.get(key);
        viewHolder = findViewHolderInCacheList(viewHolderList);

        if (viewHolder == null) {
            viewHolder = createNewViewHolder(mViewCacheTypeMap.get(key));
        }

        return viewHolder;
    }

    public void recycleViewGroup(final ViewGroup viewGroup) {
        if (viewGroup == null ||
                viewGroup.getChildCount() <= 0) {
            return;
        }

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View itemView = viewGroup.getChildAt(i);
            recycleView(itemView);
        }

        viewGroup.removeAllViews();
    }

    public void recycleView(final View view) {
        if (view == null ||
                !(view.getTag() instanceof BaseViewHolder)) {
            return;
        }

        final BaseViewHolder viewHolder = (BaseViewHolder) view.getTag();
        final String key = viewHolder.getClass().getName();
        if (TextUtils.isEmpty(key) ||
                mViewCacheTypeMap.get(key) == null) {
            LogUtil.printExceptionLog("ViewCache put TypeMap not find key: " + key);
            return;
        }

        viewHolder.notifyDetach();
        ArrayList<BaseViewHolder> cacheList;
        if (mViewHolderCacheMap.get(key) == null) {
            cacheList = new ArrayList<>();
            mViewHolderCacheMap.put(key, cacheList);
        } else {
            cacheList = mViewHolderCacheMap.get(key);
        }

        viewHolder.recycleView(cacheList);
    }

    private boolean isInited() {
        return mContext != null &&
                mLayoutInflater != null;
    }

    private BaseViewHolder createNewViewHolder(Class<? extends BaseViewHolder> clazz) {
        BaseViewHolder viewHolder = null;

        try {
            viewHolder = clazz.newInstance();
            viewHolder.init(mContext, mLayoutInflater);
            viewHolder.build();
        } catch (Exception e) {
            LogUtil.printExceptionLog("createNewViewHolder exception");
            viewHolder = null;
        } finally {
            return viewHolder;
        }
    }

    private BaseViewHolder findViewHolderInCacheList(final List<BaseViewHolder> viewHolderList) {
        if (viewHolderList == null ||
                viewHolderList.isEmpty()) {
            return null;
        }

        final Iterator iterator = viewHolderList.iterator();
        if (iterator == null) {
            return null;
        }

        while (iterator.hasNext()) {
            BaseViewHolder viewHolder = (BaseViewHolder) iterator.next();
            iterator.remove();
            if (viewHolder == null ||
                    !viewHolder.isUsable()) {
                continue;
            }

            return viewHolder;
        }

        return null;
    }

    private void refreshViewType() {
        mViewTypeMap.clear();

        int i = 0;
        for (Map.Entry<String, Class<? extends BaseViewHolder>> entry : mViewCacheTypeMap.entrySet()) {
            mViewTypeMap.put(entry.getKey(), i);
            i++;
        }
    }

}

package com.modulizedatasource.datamodule;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.modulizedatasource.model.PageContextModel;
import com.tools.LogUtil;


/**
 * Created by tony on 2018/5/9.
 */

public class ModuleDataAdapter extends BaseAdapter {
    private ModuleDataNode mTopModuleDataNode;
    private PageContextModel mContext;

    public void setModuleDataNode(ModuleDataNode node) {
        this.mTopModuleDataNode = node;
    }

    public ModuleDataNode getTopModuleDataNode() {
        return this.mTopModuleDataNode;
    }

    public void setContext(PageContextModel context) {
        this.mContext = context;
    }

    public PageContextModel getPageContextModel() {
        return this.mContext;
    }

    @Override
    public int getCount() {
        if (mTopModuleDataNode == null) {
            return 0;
        }

        return mTopModuleDataNode.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return mContext.mViewCacheManage.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        final String viewType = mTopModuleDataNode.getViewType(position);

        return mContext.mViewCacheManage.getViewType(viewType);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mTopModuleDataNode == null) {
            return null;
        }

        View view = mTopModuleDataNode.getView(convertView, position, parent.getContext());

        if (view == null) {
            LogUtil.printExceptionLog("ModuleDataAdapter get null view");
            view = new View(mContext.mContext);
        }

        if (convertView != null &&
                convertView == view) {
            LogUtil.printLog("ModuleDataAdapter get view reused");
        } else {
            final String viewType = mTopModuleDataNode.getViewType(position);
            if (convertView == null) {
                LogUtil.printLog("ModuleDataAdapter get view no reused 001 " + viewType);
            } else if (convertView != view) {
                LogUtil.printExceptionLog("ModuleDataAdapter get view no reused 002 " + viewType);
            }
        }

        return view;
    }
}

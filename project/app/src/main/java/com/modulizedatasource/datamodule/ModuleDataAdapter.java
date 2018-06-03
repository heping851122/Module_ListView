package com.modulizedatasource.datamodule;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.modulizedatasource.model.PageContextModel;


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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mTopModuleDataNode == null) {
            return null;
        }

        if (mContext != null) {
            mContext.mViewCacheManage.recycleView(convertView);
        }

        View view = mTopModuleDataNode.getView(position, parent.getContext());

        if (view == null) {
            Log.e("tony", "ModuleDataAdapter get null view");
            view = new View(mContext.mContext);
        }

        return view;
    }
}

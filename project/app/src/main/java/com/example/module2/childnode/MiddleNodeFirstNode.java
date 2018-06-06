package com.example.module2.childnode;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.modulizedatasource.datamodule.ModuleDataNode;


/**
 * Created by tony on 2018/5/14.
 */

public class MiddleNodeFirstNode extends ModuleDataNode<String> {

    @Override
    public View innerGetView(final View convertView, int localIndex, Context context) {
        ViewHolderStyle1 viewHolder;

        if (convertView != null &&
                convertView.getTag() instanceof ViewHolderStyle1) {
            viewHolder = (ViewHolderStyle1) convertView.getTag();
        } else {
            viewHolder = (ViewHolderStyle1) getPageContextModel().mViewCacheManage.getViewHolder(ViewHolderStyle1.class.getName());
        }

        viewHolder.titleTextView.setText("MiddleNodeFirstNode: " + localIndex);
        viewHolder.mContentView.setBackgroundColor(Color.WHITE);

        return viewHolder.mContentView;
    }

    @Override
    protected String getViewType(int index) {
        return ViewHolderStyle1.class.getName();
    }
}

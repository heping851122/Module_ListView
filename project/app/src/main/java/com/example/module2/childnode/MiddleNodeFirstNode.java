package com.example.module2.childnode;

import android.graphics.Color;

import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.modulizedatasource.datamodule.ModuleDataNode;


/**
 * Created by tony on 2018/5/14.
 */

public class MiddleNodeFirstNode extends ModuleDataNode<String,ViewHolderStyle1> {

    @Override
    protected void bindData(ViewHolderStyle1 viewHolder, int localIndex) {
        viewHolder.titleTextView.setText("MiddleNodeFirstNode: " + localIndex);
        viewHolder.mContentView.setBackgroundColor(Color.WHITE);
    }
}

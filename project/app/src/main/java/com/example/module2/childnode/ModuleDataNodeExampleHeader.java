package com.example.module2.childnode;

import android.graphics.Color;

import com.example.module2.Model.HeaderModel;
import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.modulizedatasource.datamodule.ModuleDataNode;


/**
 * Created by tony on 2018/5/10.
 */

public class ModuleDataNodeExampleHeader extends ModuleDataNode<HeaderModel, ViewHolderStyle1> {

    @Override
    protected void bindData(ViewHolderStyle1 viewHolder, int localIndex) {
        HeaderModel data = getItemDataLocally(localIndex);
        viewHolder.titleTextView.setText(data.title + "\nnow opening state is: " + data.isOpen);
        viewHolder.mContentView.setBackgroundColor(Color.RED);
    }
}

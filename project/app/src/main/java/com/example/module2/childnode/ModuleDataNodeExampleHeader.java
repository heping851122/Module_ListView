package com.example.module2.childnode;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.example.module2.Model.HeaderModel;
import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.modulizedatasource.datamodule.ModuleDataNode;


/**
 * Created by tony on 2018/5/10.
 */

public class ModuleDataNodeExampleHeader extends ModuleDataNode<HeaderModel> {

    @Override
    public View innerGetView(int index, Context context) {
        ViewHolderStyle1 viewHolder = (ViewHolderStyle1) getPageContextModel().mViewCacheManage.getViewHolder(ViewHolderStyle1.class.getName());

        HeaderModel data = getItemDataLocally(index);
        viewHolder.titleTextView.setText(data.title + "\nnow opening state is: " + data.isOpen);
        viewHolder.mContentView.setBackgroundColor(Color.RED);

        return viewHolder.mContentView;
    }
}

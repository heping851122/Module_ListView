package com.example.module2.childnode;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.application.BaseApplication;
import com.example.tony.modulelistview.R;
import com.example.viewcache.viewholderItem.ViewHolderStyle1;
import com.modulizedatasource.datamodule.ModuleDataGroupNode;
import com.modulizedatasource.datamodule.ModuleDataNode;
import com.modulizedatasource.datamodule.coverview.FetchCoveredViewResult;

import java.util.List;


/**
 * Created by tony on 2018/5/13.
 */

public class GroupNode extends ModuleDataGroupNode {
    private int mGroupNodeIndex;

    public void setGroupNodeIndex(final int index) {
        this.mGroupNodeIndex = index;
    }

    @Override
    public boolean isHasCoveredView() {
        return true;
    }

    @Override
    public View getCoveredViewInner() {
        ViewHolderStyle1 viewHolder = (ViewHolderStyle1) getPageContextModel().mCoveredViewCacheManage.getViewHolder(ViewHolderStyle1.class.getName());
        viewHolder.titleTextView.setText("GroupNode covered view: " + this.mGroupNodeIndex);

        viewHolder.mContentView.setBackgroundColor(Color.RED);
        viewHolder.mContentView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getPageContextModel().mContext, "GroupNode headview click", Toast.LENGTH_LONG).show();
            }
        });

        return viewHolder.mContentView;
    }
}
